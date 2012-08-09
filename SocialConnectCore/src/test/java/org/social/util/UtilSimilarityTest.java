package org.social.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.social.core.util.UtilSimilarity;

public class UtilSimilarityTest {

    @Test
    public void testWordSimilarity() {
        double result = UtilSimilarity.jaroWinklerSimilarity("food", "fooood");
        assertEquals(0.9222222222222222, result, 0.0);

        result = UtilSimilarity.jaroWinklerSimilarity("drink", "drunk");
        assertEquals(0.8933333333333333, result, 0.0);

        result = UtilSimilarity.jaroWinklerSimilarity("eat", "eaten");
        assertEquals(0.9066666666666667, result, 0.0);

        result = UtilSimilarity.jaroWinklerSimilarity("eat", "heat");
        assertEquals(0.9166666666666666, result, 0.0);

        result = UtilSimilarity.jaroWinklerSimilarity("beer", "bear");
        assertEquals(0.8666666666666667, result, 0.0);

        result = UtilSimilarity.jaroWinklerSimilarity("beer", "beer");
        assertEquals(1.0, result, 0.0);

        result = UtilSimilarity.jaroWinklerSimilarity("tea", "team");
        assertEquals(0.9416666666666667, result, 0.0);

    }

    @Test
    public void testAreStringSimkilar() throws Exception {
        assertTrue(UtilSimilarity.areStringsSimilar("wine", "wines"));
        assertFalse(UtilSimilarity.areStringsSimilar("beer", "bear"));
        // assertFalse(UtilSimilarity.areStringsSimilar("tea", "team"));
    }

}
