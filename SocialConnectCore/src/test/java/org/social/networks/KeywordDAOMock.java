package org.social.networks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.social.core.constants.KeywordType;
import org.social.core.entity.domain.Keywords;
import org.social.core.entity.helper.KeywordDAO;

public class KeywordDAOMock implements KeywordDAO {

    @Override
    public List<Keywords> getMappedKeywordByCustomerAndNetwork(Long customerId, String networkId) {
        List<Keywords> keywordListForNetwork = new ArrayList<Keywords>();
        Keywords keywords = new Keywords();
        keywords.setCustomerId(1L);
        keywords.setKeywordTypeId(KeywordType.QUERY.getName());
        keywords.setKeyword("Vapiano");
        keywordListForNetwork.add(keywords);
        return keywordListForNetwork;
    }

    @Override
    public Set<String> getUserNetworks(Long customerId) {
        // TODO Auto-generated method stub
        return null;
    }

}
