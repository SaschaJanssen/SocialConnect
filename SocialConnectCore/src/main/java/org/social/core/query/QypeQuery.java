package org.social.core.query;

import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.util.UtilProperties;
import org.social.core.util.UtilValidate;

public class QypeQuery extends Query {

    private final String searchUrl;
    private final String consumerKey;

    private final String prefix = "/reviews";
    private final String format = ".json";

    private String language;
    private String page;
    private String since;

    public QypeQuery(CustomerNetworkKeywords networkKeywords) {
        super(networkKeywords);

        consumerKey = UtilProperties.getPropertyValue("conf/qype.properties", "consumer_key");
        searchUrl = UtilProperties.getPropertyValue("conf/qype.properties", "search_url");

        setPage(networkKeywords.getPage());
    }

    private void setPage(String page) {
        this.page = page;
    }

    @Override
    public String getSearchUrl() {
        return searchUrl;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String constructQuery() {
        StringBuilder queryBuilder = new StringBuilder(searchUrl);

        queryBuilder.append(page);
        queryBuilder.append(prefix);
        if (UtilValidate.isNotEmpty(language)) {
            queryBuilder.append("/");
            queryBuilder.append(language);
        }
        queryBuilder.append(format);

        queryBuilder.append("?");
        queryBuilder.append("order=");
        queryBuilder.append("date_created");
        queryBuilder.append("&");
        queryBuilder.append("consumer_key=");
        queryBuilder.append(consumerKey);

        return queryBuilder.toString();
    }

    @Override
    public void setSince(String since) {
        this.since = since;
    }

    @Override
    public String getSince() {
        return since;
    }
}
