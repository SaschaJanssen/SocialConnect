package org.social.core.query;

import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.util.UtilProperties;

public class ZagatQuery extends Query {

    private final String searchUrl;
    private final String postFix;
    private String endpoint;
    private String since;

    public ZagatQuery(CustomerNetworkKeywords customerNetworkKeywords) {
        super(customerNetworkKeywords);
        setEndpoint(customerNetworkKeywords.getPage());
        searchUrl = UtilProperties.getPropertyValue("conf/zagat.properties", "searchUrl");
        postFix = UtilProperties.getPropertyValue("conf/zagat.properties", "postfix");
    }

    @Override
    public String constructQuery() {
        return endpoint + postFix;
    }

    public String getEndpoint() {
        return endpoint;
    }

    private void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String getSearchUrl() {
        return searchUrl;
    }

    @Override
    public String getSince() {
        return since;
    }

    @Override
    public void setSince(String since) {
        this.since = since;
    }

    @Override
    public String getLanguage() {
        return null;
    }

}
