package org.social.core.query;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.util.UtilProperties;
import org.social.core.util.UtilValidate;

public class TwitterQuery extends Query {

    private final String searchUrl;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String direct;
    private String mentioned;
    private String hash;
    private String minus;
    private String language;
    private String since;

    private String recordsPerPage = "100";

    public TwitterQuery(CustomerNetworkKeywords cnk) {
        super(cnk);

        setQuery(cnk.getQueryForNetwork());
        setHash(cnk.getHashForNetwork());
        setMentioned(cnk.getMentionedForNetwork());
        searchUrl = UtilProperties.getPropertyValue("conf/twitter.properties", "searchUrl");
    }

    private void setQuery(String direct) {
        this.direct = direct;
    }

    private void setMentioned(String mentioned) {
        this.mentioned = mentioned;
    }

    private void setHash(String hash) {
        this.hash = hash;
    }

    public void setMinus(String minus) {
        this.minus = minus;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void setSince(String since) {
        this.since = since;
    }

    @Override
    public String constructQuery() {
        String encodedQuery = createEncodedSearchQuery();

        StringBuilder queryBuilder = new StringBuilder(searchUrl);
        queryBuilder.append("?");
        queryBuilder.append("q=");
        queryBuilder.append(encodedQuery);
        if (UtilValidate.isNotEmpty(language)) {
            queryBuilder.append("&lang=");
            queryBuilder.append(language);
        }
        if (UtilValidate.isNotEmpty(since)) {
            queryBuilder.append("&since=");
            queryBuilder.append(since);
        }

        queryBuilder.append("&rpp=");
        queryBuilder.append(recordsPerPage);
        String constructedQuery = queryBuilder.toString();
        return constructedQuery;
    }

    @Override
    public String getSearchUrl() {
        return searchUrl;
    }

    private String createEncodedSearchQuery() {
        StringBuilder query = new StringBuilder();

        query.append(direct);

        if (UtilValidate.isNotEmpty(hash)) {
            query.append(" OR ");
            query.append(hash);
        }

        if (UtilValidate.isNotEmpty(mentioned)) {
            query.append(" OR ");
            query.append(mentioned);
        }

        if (UtilValidate.isNotEmpty(minus)) {
            query.append(" -");
            query.append(minus);
        }

        String encodedQuery = "";
        try {
            encodedQuery = URLEncoder.encode(query.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Using not encoded query.", e);
            encodedQuery = query.toString();
        }

        return encodedQuery;
    }

    @Override
    public String getSince() {
        return since;
    }
}
