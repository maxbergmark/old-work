package io.dota.betting;


import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import static java.lang.String.valueOf;

/**
 * Created by ElevPC on 2017-03-24.
 */
public class ML {

    public ML(String trainfile, String testfile) throws Exception {
        ConverterUtils.DataSource trainsource = new ConverterUtils.DataSource(trainfile);
        Instances traindata = trainsource.getDataSet();
        traindata.setClassIndex(traindata.numAttributes() - 1);
        ConverterUtils.DataSource testsource = new ConverterUtils.DataSource(testfile);
        Instances testdata = testsource.getDataSet();
        testdata.setClassIndex(testdata.numAttributes() - 1);

        J48 j48 = new J48();
        j48.setUnpruned(true);        // using an unpruned J48
        // meta-classifier
        FilteredClassifier fc = new FilteredClassifier();
        //fc.setFilter(rm);
        fc.setClassifier(j48);
        // train and make predictions
        fc.buildClassifier(traindata);
        System.out.println(fc.graph());
        double value = 1;
        for (int i = 0; i < testdata.numInstances(); i++) {
            double pred = fc.classifyInstance(testdata.instance(i));
            System.out.print("Actual: " + testdata.classAttribute().value((int) testdata.instance(i).classValue()));
            System.out.print(", predicted: " + testdata.classAttribute().value((int) pred));
            int actual = Integer.valueOf(testdata.classAttribute().value((int) testdata.instance(i).classValue()));
            int predicted = Integer.valueOf(testdata.classAttribute().value((int) pred));
            if(actual == predicted) {
                if(predicted == 1) {
                    value += testdata.instance(i).value(0) - 1;
                } else if(predicted == 2) {
                    value += testdata.instance(i).value(2) - 1;
                }
            } else {
                value--;
            }
            System.out.println("    VALUE: " + value);
        }

    }

}
