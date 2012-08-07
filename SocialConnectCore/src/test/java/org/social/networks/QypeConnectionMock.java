package org.social.networks;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.Query;

public class QypeConnectionMock implements SocialNetworkConnection {

	String s = "{\"rating\":5,\"locale_code\":\"en_GB\",\"content_xhtml\":\"<p>thumbs up!<br /><\\/p>\",\"updated\":\"2012-07-27T22:13:03+02:00\",\"language\":\"en\",\"links\":[{\"href\":\"http://api.qype.com/v1/reviews/3126848\",\"rel\":\"self\"},{\"href\":\"http://www.qype.co.uk/review/3126848?utm_source=api&utm_medium=referal&utm_campaign=2242&utm_term=review\",\"rel\":\"alternate\",\"hreflang\":\"en\"},{\"title\":\"Wolfgangs\",\"href\":\"http://api.qype.com/v1/places/139975\",\"rel\":\"http://schemas.qype.com/place\"},{\"title\":\"Rebecca Hilton\",\"href\":\"http://api.qype.com/v1/users/RebeccaHilton1994\",\"rel\":\"http://schemas.qype.com/user\"},{\"title\":\"New York\",\"href\":\"http://api.qype.com/v1/locators/usa2-new-york\",\"rel\":\"http://schemas.qype.com/locator\"},{\"href\":\"http://api.qype.com/v1/reviews/3126848/likes\",\"count\":0,\"rel\":\"http://schemas.qype.com/likes\"},{\"href\":\"http://api.qype.com/v1/reviews/3126848/comments\",\"count\":0,\"rel\":\"http://schemas.qype.com/comments\"},{\"href\":\"http://api.qype.com/v1/reviews/3126848/compliments\",\"count\":0,\"rel\":\"http://schemas.qype.com/compliments\"}],\"content_text\":\"thumbs up!\",\"summary\":\"thumbs up!\",\"id\":\"tag:api.qype.com,2012-07-27:/reviews/3126848\",\"created\":\"2012-07-27T22:13:03+02:00\"}";

	@Override
	public List<JSONObject> getRemoteData(Query query) {
		JSONObject jo = new JSONObject();

		jo = (JSONObject) JSONSerializer.toJSON(s);

		List<JSONObject> result = new ArrayList<JSONObject>();
		result.add(jo);
		return result;
	}

}
