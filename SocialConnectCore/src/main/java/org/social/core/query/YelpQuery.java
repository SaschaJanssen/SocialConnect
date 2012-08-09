package org.social.core.query;

import org.social.core.data.CustomerNetworkKeywords;

public class YelpQuery extends Query {

    private final String searchUrl = "https://www.yelp.com";

    private String endpoint;
    private String since;

    public YelpQuery(CustomerNetworkKeywords customerNetworkKeywords) {
        super(customerNetworkKeywords);

        setEndpoint(customerNetworkKeywords.getPage());
    }

    @Override
    public String constructQuery() {
        return endpoint + "?sort_by=date_desc";
    }

    private void setEndpoint(String id) {
        endpoint = id;
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

    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public String getLanguage() {
        return null;
    }

}
