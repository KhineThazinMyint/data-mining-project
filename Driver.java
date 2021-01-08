/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataminingproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFrame;

/**
 *
 * @author user
 */
public class Driver extends JFrame {
    static String dataKey=Data.datas.keySet().iterator().next();
     public static void main(String[] args) throws Exception {
        DataSet dataSet=new DataSet(Data.datas.get(dataKey));
     //   System.out.println("[" + dataKey + " DATASEcT]\n"+ dataSet);// display dataset
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));// dataset reader
        boolean flag=true;
        while(flag){
            System.out.println("> What do you want to do (calc probs, change dataset, exit) ? ");
            String command=bufferedReader.readLine();// process reader 
            switch(command){
                case "calc probs" :
                    System.out.println("> Please enter values for:  ");
                    for(int i=0 ; i<dataSet.getData()[0].length-2 ; i++) System.out.print(dataSet.getData()[0][i] + ", ");
                    System.out.println(dataSet.getData()[0][dataSet.getData()[0].length-2]+ " (Separated by commas)");
                    String[] values=(bufferedReader.readLine()).split(",");// data reader
                    HashMap<String, String>instMap= new HashMap<String, String>();
                    for(int i=0; i<dataSet.getData()[0].length-1; i++) instMap.put(dataSet.getData()[0][i], values[i].trim());
                    HashMap<String, Double> condProbs=dataSet.calcCondProbs(instMap);
                    double allProbs=0.0;
                    Iterator<Double> probsIterator=condProbs.values().iterator();
                    while(probsIterator.hasNext()) allProbs +=probsIterator.next();
                    Iterator<String> keyIterator=condProbs.keySet().iterator();
                    while(keyIterator.hasNext()){
                        String next=keyIterator.next();
                        System.out.println("P(" + next +"|"+ DataSet.getInstanceStr(dataSet, instMap)+") = "+
                                String.format("%3f", condProbs.get(next))+ "/" + String.format("%3f", allProbs)+
                                " = "+ String.format("%f", condProbs.get(next)/allProbs));
                    }
                   
                    
                    
                System.out.println();
                   break;
                case "change dataset":
                    System.out.println("> Choose dataset (" +Data.datas.keySet()+"?");
                    String value=bufferedReader.readLine();
                    if(Data.datas.keySet().contains(value)){
                        dataKey=value;
                        dataSet=new DataSet(Data.datas.get(dataKey));
                        System.out.println(dataSet);
                    }
                    else 
                        System.out.println("please enter valid dataset name");
                    break;
                    
                case "exit":
                    flag=false;
                    break;
                    
            }
        }
       System.exit(0);
    }
}
