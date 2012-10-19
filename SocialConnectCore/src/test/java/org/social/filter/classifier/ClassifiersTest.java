package org.social.filter.classifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.social.core.entity.domain.LearningData;
import org.social.core.filter.classifier.BaseClassifier;
import org.social.core.filter.classifier.BayesClassifier;
import org.social.core.filter.classifier.J48Classifier;

public class ClassifiersTest {

    private String bayesClassificationString = "NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEGATIVE,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,POSITIVE,POSITIVE,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,POSITIVE,POSITIVE,POSITIVE,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,POSITIVE,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEGATIVE,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEGATIVE,NEUTRAL,POSITIVE,NEUTRAL";
    private String j48ClassificationString = "NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEGATIVE,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,POSITIVE,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEGATIVE,NEGATIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEGATIVE,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,POSITIVE,POSITIVE,POSITIVE,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEGATIVE,NEUTRAL,POSITIVE,NEUTRAL";
    private String[] masterBayes;
    private String[] masterJ48;
    private List<String> testDataSet;
    private List<LearningData> trainingDataSet;

    @Before
    public void setUp() throws Exception {
        masterJ48 = j48ClassificationString.split(",");
        masterBayes = bayesClassificationString.split(",");

        trainingDataSet = readLearningData();
        testDataSet = readTestDataFile();
    }

    @Test(expected = IllegalStateException.class)
    public void testFailGetTrainedInstance() throws Exception {
        BaseClassifier.getTrainedInstance();
    }

    @Test(expected = IllegalStateException.class)
    public void testInitFail() throws Exception {
        BaseClassifier classifier = new BayesClassifier();
        classifier.trainClassifier(trainingDataSet);
        classifier.classify(testDataSet);
    }

    @Test
    public void testWekaClassifierBayes() throws Exception {
        BaseClassifier classifier = new BayesClassifier();
        classifier.init(trainingDataSet);

        List<String> result = BaseClassifier.getTrainedInstance().classify(testDataSet);

        assertEquals(masterBayes.length, result.size());

        for (int i = 0; i < masterBayes.length; i++) {
            assertEquals(masterBayes[i], result.get(i));
        }
    }

    @Test
    public void testWekaClassifierWithSingleStringBayes() throws Exception {
        BaseClassifier classifier = new BayesClassifier();
        classifier.init(trainingDataSet);
        String result = BaseClassifier.getTrainedInstance().classify(testDataSet.get(0));
        assertEquals("NEUTRAL", result);

        result = BaseClassifier.getTrainedInstance().classify(testDataSet.get(1));
        assertEquals("POSITIVE", result);

        result = BaseClassifier.getTrainedInstance().classify(testDataSet.get(4));
        assertEquals("NEGATIVE", result);
    }

    @Test
    public void testWekaClassifierWithSingleStringJ48() throws Exception {
        BaseClassifier classifier = new J48Classifier();
        classifier.init(trainingDataSet);
        String result = BaseClassifier.getTrainedInstance().classify(testDataSet.get(0));
        assertEquals("NEUTRAL", result);

        result = BaseClassifier.getTrainedInstance().classify(testDataSet.get(6));
        assertEquals("POSITIVE", result);

        result = BaseClassifier.getTrainedInstance().classify(testDataSet.get(39));
        assertEquals("NEGATIVE", result);

        result = BaseClassifier.getTrainedInstance().classify(testDataSet.get(41));
        assertEquals("POSITIVE", result);
    }

    @Test
    public void testWekaClassifierJ48() {
        BaseClassifier classifier = new J48Classifier();
        classifier.init(trainingDataSet);
        List<String> result = BaseClassifier.getTrainedInstance().classify(testDataSet);

        assertEquals(masterJ48.length, result.size());

        for (int i = 0; i < masterJ48.length; i++) {
            assertEquals(masterJ48[i], result.get(i));
        }

    }

    @Test
    public void testGetTrainedInstance() throws Exception {
        new J48Classifier();
        BaseClassifier classifier = BaseClassifier.getTrainedInstance();
        assertNotNull(classifier);
        assertTrue(classifier instanceof J48Classifier);
    }

    private List<LearningData> readLearningData() {
        List<LearningData> result = new ArrayList<LearningData>();

        BufferedReader br = getFileReader("src/test/resources/sentimentLearningTestData");

        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split("§");

                LearningData wrapper = new LearningData();
                wrapper.setLearningData(splitted[0]);
                wrapper.setClassificationId(splitted[1]);

                result.add(wrapper);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private BufferedReader getFileReader(String filePath) {
        Reader reader = null;
        try {
            reader = new FileReader(new File(filePath));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);
        return br;
    }

    private List<String> readTestDataFile() {
        List<String> testData = new ArrayList<String>();

        BufferedReader br = getFileReader("src/test/resources/sentimentTestData");
        // BufferedReader br = getFileReader("src/test/resources/sentTest_2");

        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                testData.add(line);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return testData;
    }

}
