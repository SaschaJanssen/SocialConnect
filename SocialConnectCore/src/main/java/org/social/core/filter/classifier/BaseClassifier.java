package org.social.core.filter.classifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.social.core.entity.domain.LearningData;
import org.social.core.util.UtilLucene;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.tokenizers.NGramTokenizer;
import weka.core.tokenizers.Tokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public abstract class BaseClassifier {

    protected Instances train = null;
    protected Classifier classifier = null;

    private StringToWordVector filter = null;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static BaseClassifier trainedInstance = null;

    public void init(List<LearningData> trainingDataSet) {
        this.trainClassifier(trainingDataSet);

        trainedInstance = this;
        isInitialized.set(true);
    }

    public static BaseClassifier getTrainedInstance() throws IllegalStateException {
        checkIfIsInitialized();
        return trainedInstance;
    }

    private static void checkIfIsInitialized() {
        if (!isInitialized.get()) {
            throw new IllegalStateException(
                    "The classifier instance is not trained please initialize the classifier first before using it. Use Constructor.");
        }
    }

    protected abstract void buildClassifier(Instances fillteredTrainData) throws Exception;

    public void trainClassifier(List<LearningData> trainData) {
        try {
            train = createInstancesFromDataList(trainData);
            Tokenizer tokenizer = createTokenizer();
            filter = createStringToWordVectorFilter(train, tokenizer);

            Instances fillteredTrainData = filterInstances(train);
            buildClassifier(fillteredTrainData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Instances createInstancesFromDataList(List<LearningData> trainData) {
        Attribute stringAttribute = new Attribute("text", (FastVector) null);

        FastVector classVal = new FastVector(3);
        classVal.addElement("POSITIVE");
        classVal.addElement("NEGATIVE");
        classVal.addElement("NEUTRAL");
        Attribute classAttribute = new Attribute("class", classVal);

        FastVector wekaAttributes = new FastVector(2);
        wekaAttributes.addElement(stringAttribute);
        wekaAttributes.addElement(classAttribute);

        Instances trainingSet = new Instances("Rel", wekaAttributes, 10);
        trainingSet.setClassIndex(1);

        for (LearningData trainWrapper : trainData) {
            Instance instance = new Instance(2);
            instance.setValue((Attribute) wekaAttributes.elementAt(0), trainWrapper.getLearningData());
            instance.setValue((Attribute) wekaAttributes.elementAt(1), trainWrapper.getClassificationId());

            trainingSet.add(instance);
        }

        return trainingSet;
    }

    /**
     * Classifies a set of testing data strings with the initialized Classifier.
     */
    public List<String> classify(List<String> dataToClassify) {
        checkIfIsInitialized();

        List<String> result = new ArrayList<String>();

        Instances dataset = createInstacesForClassification(dataToClassify);

        Attribute classAttributes = train.classAttribute();
        try {
            result = classifyDataSet(dataset, classAttributes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String classify(String phraseToClassify) {
        checkIfIsInitialized();

        Instance instance = createInstanceForClassification(phraseToClassify);
        instance.setDataset(train);
        Attribute classAttributes = train.classAttribute();

        String predictedValue = null;
        try {
            predictedValue = classifyData(instance, classAttributes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return predictedValue;
    }

    private Tokenizer createTokenizer() {
        NGramTokenizer tokenizer = new NGramTokenizer();
        tokenizer.setNGramMaxSize(3);
        return tokenizer;
    }

    private StringToWordVector createStringToWordVectorFilter(Instances instancesForInputFormat, Tokenizer tokenizer)
            throws Exception {
        StringToWordVector filter = new StringToWordVector();
        // filter.setStopwords(new File("/home/u179995/english"));

        filter.setTokenizer(tokenizer);
        filter.setIDFTransform(true);
        filter.setLowerCaseTokens(true);
        filter.setTFTransform(true);
        filter.setInputFormat(instancesForInputFormat);

        return filter;
    }

    private List<String> classifyDataSet(Instances datasetForClassification, Attribute classAttributes)
            throws Exception {
        List<String> result = new ArrayList<String>();

        for (int i = 0; i < datasetForClassification.numInstances(); i++) {
            String predictedValue = classifyData(datasetForClassification.instance(i), classAttributes);
            result.add(predictedValue);
        }
        return result;
    }

    private String classifyData(Instance dataForClassification, Attribute classAttributes) throws Exception {
        Instance instanceUnderTest = filterInstance(dataForClassification);
        double predictedValue = classifier.classifyInstance(instanceUnderTest);
        return classAttributes.value((int) predictedValue);
    }

    private Instances createInstacesForClassification(List<String> testData) {
        Instances dataset = train.stringFreeStructure();
        Attribute attrText = dataset.attribute("text");

        for (String dataForTest : testData) {
            Instance inst = createInstanceForClassification(dataForTest, attrText, dataset.numAttributes());
            dataset.add(inst);
        }
        return dataset;
    }

    private Instance createInstanceForClassification(String dataForTest) {
        dataForTest = UtilLucene.standardsAnalyzer(dataForTest);

        Attribute string = new Attribute("text", (FastVector) null);
        Attribute classification = train.classAttribute();

        FastVector attributes = new FastVector();
        attributes.addElement(classification);
        attributes.addElement(string);

        return createInstanceForClassification(dataForTest, string, attributes.size());
    }

    private Instance createInstanceForClassification(String dataForTest, Attribute attrText, int numAttr) {
        dataForTest = UtilLucene.standardsAnalyzer(dataForTest);

        double[] values = new double[numAttr];
        values[0] = attrText.addStringValue(dataForTest);

        Instance instance = new Instance(1.0, values);
        instance.setDataset(train);

        return instance;
    }

    protected void evaluateClassification(Instances train, Classifier bayes) throws Exception {
        Evaluation evaluation = new Evaluation(train);
        evaluation.evaluateModel(bayes, train);
    }

    private Instance filterInstance(Instance unfilteredData) throws Exception {
        filter.input(unfilteredData);
        return filter.output();
    }

    private Instances filterInstances(Instances unfilteredTrainingData) throws Exception {
        return Filter.useFilter(unfilteredTrainingData, filter);
    }
}
