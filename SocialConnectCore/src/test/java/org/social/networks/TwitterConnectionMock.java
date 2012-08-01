package org.social.networks;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.Query;

public class TwitterConnectionMock implements SocialNetworkConnection {

	String s = "{\"results\":[{\"created_at\":\"Tue, 31 Jul 2012 21:09:31 +0000\",\"from_user\":\"MockingEn\",\"from_user_id\" : 396072387,\"from_user_id_str\" : \"396072387\",\"from_user_name\" : \"Rennesme. \",\"geo\" : null,\"id\" : 230409883093200896,\"id_str\" : \"230409883093200896\",\"iso_language_code\" : \"en\",\"metadata\" : { \"result_type\" : \"recent\" },\"profile_image_url\" : \"http://a0.twimg.com/profile_images/2439886993/image_normal.jpg\",\"profile_image_url_https\" : \"https://si0.twimg.com/profile_images/2439886993/image_normal.jpg\",\"source\" : \"Twitter for iPhone\",\"text\" : \"Craving vapiano's alfredo pasta with chicken.\",\"to_user\" : null,\"to_user_id\" : 0,\"to_user_id_str\" : \"0\",\"to_user_name\" : null}]}";

	@Override
	public List<JSONObject> getRemoteData(Query query) {
		JSONObject jo = new JSONObject();

		jo = (JSONObject) JSONSerializer.toJSON(s);

		List<JSONObject> result = new ArrayList<JSONObject>();
		result.add(jo);
		return result;
	}

}
