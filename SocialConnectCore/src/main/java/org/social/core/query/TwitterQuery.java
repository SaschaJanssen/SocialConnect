package org.social.core.query;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.util.UtilValidate;

public class TwitterQuery extends Query {

	private final String searchUrl = "https://search.twitter.com/search.json";

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

	public void setSince(String since) {
		this.since = since;
	}

	@Override
	public String constructQuery() {
		String encodedQuery = createEncodedSearchQuery();

		StringBuilder queryBuilder = new StringBuilder(this.searchUrl);
		queryBuilder.append("?");
		queryBuilder.append("q=");
		queryBuilder.append(encodedQuery);
		queryBuilder.append("&lang=");
		queryBuilder.append(this.language);

		if (UtilValidate.isNotEmpty(this.since)) {
			queryBuilder.append("&since=");
			queryBuilder.append(this.since);
		}

		queryBuilder.append("&rpp=");
		queryBuilder.append(this.recordsPerPage);
		String constructedQuery = queryBuilder.toString();
		return constructedQuery;
	}

	@Override
	public String getSearchUrl() {
		return this.searchUrl;
	}

	private String createEncodedSearchQuery() {
		StringBuilder query = new StringBuilder();

		query.append(this.direct);

		if (UtilValidate.isNotEmpty(this.hash)) {
			query.append(" OR ");
			query.append(this.hash);
		}

		if (UtilValidate.isNotEmpty(this.mentioned)) {
			query.append(" OR ");
			query.append(this.mentioned);
		}

		if (UtilValidate.isNotEmpty(this.minus)) {
			query.append(" -");
			query.append(this.minus);
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
}
