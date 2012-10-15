package org.social.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.social.core.util.UtilLucene;

public class UtilLuceneTest {

    @Test
    public void testNGramTokenizer() {
        List<String> result = UtilLucene.ngramString("Service is exceptionally bad");
        assertEquals("[service is, is exceptionally, exceptionally bad]", result.toString());
    }

    @Test
    public void testTokenize() throws Exception {
        List<String> result = UtilLucene.tokenizeString(new StandardAnalyzer(Version.LUCENE_36),
                "Service is exceptionally bad");
        assertEquals("[service, exceptionally, bad]", result.toString());
    }
    
    @Test
    public void testAnalyser() throws Exception {
        String result = UtilLucene.standardsAnalyzer("@CEP_AceTurtle09 I haven't...but I want Five Guys burger and fries tho :-/");
        assertEquals("cep_aceturtle09 i haven't i want five guys burger fries tho ", result);
    }
}
