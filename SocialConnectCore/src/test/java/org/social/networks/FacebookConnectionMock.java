package org.social.networks;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.Query;

public class FacebookConnectionMock implements SocialNetworkConnection {

    String s = "{\"id\":\"100001735862605_374761675925025\",\"from\":{\"name\":\"AdilNazir\",\"id\":\"100001735862605\"},\"message\":\"Having awesome dinner at VAPIANO...;)))\",\"type\":\"status\",\"application\":{\"name\":\"Mobile\",\"id\":\"2915120374\"},\"created_time\":\"2012-07-18T19:47:38+0000\",\"updated_time\":\"2012-07-20T19:35:22+0000\",\"likes\":{\"data\":[{\"name\":\"RebalQureshi\",\"id\":\"1442797846\"},{\"name\":\"SairaNazir\",\"id\":\"100001845429991\"},{\"name\":\"BilalArif\",\"id\":\"100002035868753\"},{\"name\":\"SarahFazal\",\"id\":\"100000795381708\"}],\"count\":10}}";

    @Override
    public List<JSONObject> getRemoteData(Query query) {
        JSONObject jo = new JSONObject();

        jo = (JSONObject) JSONSerializer.toJSON(s);

        List<JSONObject> result = new ArrayList<JSONObject>();
        result.add(jo);
        return result;
    }

}
