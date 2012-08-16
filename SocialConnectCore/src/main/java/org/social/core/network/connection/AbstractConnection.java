package org.social.core.network.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.util.UtilValidate;

public abstract class AbstractConnection {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected HttpClient httpClient;

    protected AbstractConnection() {
        httpClient = new DefaultHttpClient();
        setHttpsProxy();
    }

    private void setHttpsProxy() {

        String host = System.getProperty("https.proxyHost");
        String portString = System.getProperty("https.proxyPort");

        if (UtilValidate.isNotEmpty(host) && UtilValidate.isNotEmpty(portString)) {
            int port = Integer.parseInt(portString);

            HttpHost proxy = new HttpHost(host, port);
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
    }

    protected String readDataFromUrl(String queryUrl, String language) {
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader in = null;
        try {
            HttpUriRequest req = new HttpGet(queryUrl);

            if (UtilValidate.isNotEmpty(language)) {
                req.setHeader("Accept-Language", language);
            }

            HttpResponse resp = doRequest(req);

            in = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                resultStringBuilder.append(inputLine);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        return resultStringBuilder.toString();
    }

    private HttpResponse doRequest(HttpUriRequest req) throws IOException, ClientProtocolException {
        int tries = 0;
        int maxNumberOfTries = 3;

        boolean shouldTryAgain;
        HttpResponse resp = null;
        do {
            shouldTryAgain = false;
            try {
                resp = httpClient.execute(req);
            } catch (ConnectionClosedException cce) {
                logger.warn("Unexpected connection close exception after " + tries + " have "
                        + (maxNumberOfTries - tries) + " left before throwing the Exception, Message: "
                        + cce.getMessage());

                tries++;
                if (tries <= maxNumberOfTries) {
                    shouldTryAgain = true;
                } else {
                    throw new ConnectionClosedException(cce.getMessage());
                }
            }
        } while (shouldTryAgain);
        return resp;
    }
}
