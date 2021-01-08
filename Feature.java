/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataminingproject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.IntStream;

/**
 *
 * @author user
 */
public class Feature {
    private String name=null;
    private String[][] data=null;
    private HashSet<FeatureValue> featureValues=new HashSet<FeatureValue>();
    private double probability;
    public Feature(String[][] data, int column){
        this.name=data[0][column];
        this.data=data;
        IntStream.range(1, data.length).forEach(row -> featureValues.add(new FeatureValue(data[row][column])));
        featureValues.stream().forEach(featureValue -> {
            int counter =0;
            for(int row=1;row<data.length; row++)
                if(featureValue.getName()==data[row][column] )featureValue.setOccurences(++counter);
        });
    }
    public Feature calcProb(String featureValueName, HashMap<String, String> logMap){
        if(getFeatureValue(featureValueName)!=null){
            probability=(((double)getFeatureValue(featureValueName).getOccurences())/(data.length-1));
            logMap.put(this.name, getFeatureValue(featureValueName).getOccurences()+"/"+(data.length-1));
        }
        else{
            probability=0;
            logMap.put(this.name, "0/"+(data.length-1));
        }
        return this;
    }
    public FeatureValue getFeatureValue(String featureValueName){
        FeatureValue returnValue=null;
        Iterator<FeatureValue> iterator=featureValues.iterator(); 
        while(iterator.hasNext()){
            FeatureValue featureValue=iterator.next();
            if(featureValue.getName().equals(featureValueName)) { returnValue=featureValue; break;}
        }
        return returnValue;
    }
    public double getProbability() {return probability;}
    public String getName() {return name;}
    public HashSet<FeatureValue> getFeatureValues() {return featureValues;}
    public String toString(){return name;}
}
