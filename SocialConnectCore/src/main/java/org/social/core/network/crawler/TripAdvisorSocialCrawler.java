package org.social.core.network.crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.social.core.constants.Networks;
import org.social.core.exceptions.ItemNotFoundException;

public class TripAdvisorSocialCrawler extends SocialCrawler {

    private String[] splitedBaseUrls = null;

    public TripAdvisorSocialCrawler(BaseCrawler crawler, String baseUrl, String endpoint) {
        super(crawler, baseUrl, endpoint);
    }

    @Override
    protected String extractNetworkUserRatingData(Element ratingElement) {
        String rating = ratingElement.select("img[alt]").attr("alt");
        return rating.split(" ")[0];
    }

    @Override
    protected String getLanguageFromHeadMetaData(Element headerElements) {
        Element select = headerElements.parent().select("select#filterLang").first();
        Element selectedLanguage = select.select("option[selected]").first();

        return selectedLanguage.attr("value");
    }

    @Override
    public String getNextPageFromPagination(Element body) throws ItemNotFoundException {
        String nextPage = null;


        Elements paginationElements = selectFromElement(body, paginationControlsCssClassName);
        Element pagination = paginationElements.first();

        Element nextPageLink = pagination.select("span.sprite-pageNext").first();

        if (nextPageLink != null) {
            Elements selectedElements = selectFromElement(pagination, selectedPaginationCssClassName);

            String currentPage = selectedElements.first().text();
            int currentPageNo = Integer.parseInt(currentPage);
            int nextPageIndicator = currentPageNo * 10;

            if (splitedBaseUrls == null) {
                splitBaseString(body);
            }

            StringBuilder nextLinkBuilder = new StringBuilder();
            nextLinkBuilder.append(splitedBaseUrls[0]);
            nextLinkBuilder.append("-");
            nextLinkBuilder.append(splitedBaseUrls[1]);
            nextLinkBuilder.append("-");
            nextLinkBuilder.append(splitedBaseUrls[2]);
            nextLinkBuilder.append("-");
            nextLinkBuilder.append("or" + nextPageIndicator);
            nextLinkBuilder.append("-");
            nextLinkBuilder.append(splitedBaseUrls[3]);

            nextPage = nextLinkBuilder.toString();
        }

        return nextPage;
    }

    public void splitBaseString(Element body) {
        splitedBaseUrls = new String[4];

        Element flagLinkElement = body.select("a[href].flag_link").first();
        String flagLinkHref = flagLinkElement.attr("href");
        int startFlag = StringUtils.indexOf(flagLinkHref, "/Restaurant_");
        flagLinkHref = flagLinkHref.substring(startFlag);

        Matcher matcher = Pattern.compile("/Restaurant_Review").matcher(flagLinkHref);
        if (matcher.find()) {
            splitedBaseUrls[0] = matcher.group();
        }

        matcher = Pattern.compile("[a-z]{1}[0-9]+-[a-z]{1}[0-9]+").matcher(flagLinkHref);
        if (matcher.find()) {
            splitedBaseUrls[1] = matcher.group();
        }

        matcher = Pattern.compile("Reviews").matcher(flagLinkHref);
        if (matcher.find()) {
            splitedBaseUrls[2] = matcher.group();
        }

        int qualifierStart = StringUtils.indexOf(flagLinkHref, "Reviews-");
        splitedBaseUrls[3] = flagLinkHref.substring(qualifierStart + 8);
    }

    @Override
    protected String getNetworkName() {
        return Networks.TRIPADVISOR.name();
    }

    @Override
    protected String getUserIdFromUserInfo(Element userInfo) {
        return "n/a";
    }

    @Override
    protected String getUserNameFromUserInfo(Element userInfo) {
        return userInfo.text();
    }

    @Override
    protected String getPropertyFileName() {
        return "conf/tripAdvisor.properties";
    }
}
