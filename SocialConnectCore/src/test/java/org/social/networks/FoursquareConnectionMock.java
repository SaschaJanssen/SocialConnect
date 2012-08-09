package org.social.networks;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.social.core.network.connection.SocialNetworkConnection;
import org.social.core.query.Query;

public class FoursquareConnectionMock implements SocialNetworkConnection {

    String s = "{\"id\":\"500f70bde4b0c06369ba0c43\",\"createdAt\":1343189181,\"text\":\"Burgers are great, try the Cajun style fries, the diet coke vanella and pib sodas are good too\",\"likes\":{\"count\":0,\"groups\":[]},\"todo\":{\"count\":0},\"user\":{\"id\":\"33226339\",\"firstName\":\"Mikey\",\"photo\":{\"prefix\":\"https://irs0.4sqi.net/img/user/\",\"suffix\":\"/53Q1EPZQRUYKMKAC.jpg\"},\"tips\":{\"count\":3},\"gender\":\"male\",\"homeCity\":\"New Hartford, NY\",\"bio\":\"\",\"contact\":{\"twitter\":\"toastymmtweeter\",\"facebook\":\"100000905839292\"}}}";

    @Override
    public List<JSONObject> getRemoteData(Query query) {
        JSONObject jo = new JSONObject();

        jo = (JSONObject) JSONSerializer.toJSON(s);

        List<JSONObject> result = new ArrayList<JSONObject>();
        result.add(jo);
        return result;
    }

}
