/*
 * File: KMeansClustering.java
 */

import java.io.*;
import java.util.*;

public class KMeansClustering {
  
 private int k;
 private ArrayList<Data> data;
 private ArrayList<Cluster> clusters;
 private ArrayList<String> classLabels;
 private double manWSS = 0;
 private double eucWSS = 0;
 private double entropy;
 private double manBSS = 0;
 private double eucBSS = 0;
   
 
 //Getters
 public double getEucWSS() {return this.eucWSS;}
 public double getManWSS() {return this.manWSS;}
 public double getEucBSS() {return this.eucBSS;}
 public double getManBSS() {return this.manBSS;}
 public double getEntropy() {return this.entropy;}
 
 //TODO
 //public void calculateWSS() {}
 
 
 
 
 //TODO
 public void calculateBSS() {
  
   HashMap<String, Double> dataAttributes = new HashMap<String, Double>();
   for (int i = 0; i < clusters.size(); i++) {
     for (int j = 0; j < clusters.get(i).getCluster().size(); j++) {
       for (Iterator<String> stuff = clusters.get(i).getCluster().get(j).getAttributes().keySet().iterator(); stuff.hasNext();) {         
         String current = stuff.next();
         if (i == 0) {
           dataAttributes.put(current, (clusters.get(i).getCentroid().getAttributes().get(current))/clusters.size());
         }
         else {
           dataAttributes.put(current, dataAttributes.get(current) + (clusters.get(i).getCentroid().getAttributes().get(current))/clusters.size());
         }
       }
     }
   }
   for (int i = 0; i < clusters.size(); i++) {
     for (int j = 0; j < clusters.get(i).getCluster().size(); j++) {
       for (Iterator<String> stuff = clusters.get(i).getCentroid().getAttributes().keySet().iterator(); stuff.hasNext();) {
         String current = stuff.next();
         double value = Math.pow((clusters.get(i).getCentroid().getAttribute(current) - dataAttributes.get(current) + (clusters.get(i).getCentroid().getAttributes().get(current))/clusters.size()), 2);
         double manValue = value * clusters.get(i).getCluster().size();
         manBSS += manValue;
         eucBSS += Math.pow(manValue, 2);
       }
     }
   }
 }
     
   
 
     
 
 
 //TODO
 public void calculateEntropy() {}
  
  public KMeansClustering(ArrayList<Data> data, int k, ArrayList<String> classLabels) {
    this.k = k;
    this.data = data;
    this.classLabels = classLabels;
  }
  
  public KMeansClustering(String filename, int k) throws IOException {
    this.k = k;
    this.data = readARFF(filename);
  }
  
  public int getK() {return k;}
  public ArrayList<Data> getData() {return data;}
  
  public static ArrayList<Data> readARFF(String fileName) throws IOException{
    BufferedReader file;
    ArrayList<String> attributeNames = new ArrayList<String>();
    //Validate file
    try {
      file = new BufferedReader(new FileReader(fileName));
    }
    catch (FileNotFoundException e) {
      System.out.println("Invalid File");
      return null;
    }
    
    String line = file.readLine();
    while (!line.equals("@data")) {
      if (line.equals("")) {line = file.readLine();continue;}
      String[] lineArgs = line.split("\\s");
      if (lineArgs[0].equals("@relation")) {line = file.readLine();continue;}
      else if (lineArgs[0].equals("@attribute")) {
        attributeNames.add(lineArgs[1]);
        line = file.readLine();
      }
    }
    
    ArrayList<Data> allData = new ArrayList<Data>();
    line = file.readLine();
    int iD = 0;
    while (line != null) {
      HashMap<String, Double> objects = new HashMap<String, Double>();
      String[] dataValues = line.split(",");
      String classLabel = "";
      for (int i = 0; i < dataValues.length; i++) {
        String attribute = attributeNames.get(i);
        if (attribute.equals("Class")) {
          classLabel = dataValues[i];
          continue;
        }
        double value;
        try {
          value = Double.parseDouble(dataValues[i]);
        }
        catch (NumberFormatException e) {
          line = file.readLine();
          continue;
        }
        objects.put(attribute, value);
      }
      allData.add(new Data(objects, classLabel, iD));
      iD++;
      line = file.readLine();
    }
    file.close();
    return allData;
  }
 //TODO
  public KMeansClustering cluster(boolean euclidean) {
    return null;
  }
     
 
 //TODO
 public void output(KMeansClustering current) {
  
 }
 
  
  public static void main(String[] args) throws IOException {
   ArrayList<Data> initData = readARFF(args[0]);
   ArrayList<String> classLabels = new ArrayList<String>();
 classLabels.add(initData.get(0).getClassLabel());
 for (int i = 1; i < initData.size(); i++) {
  String current = initData.get(i).getClassLabel();
  if (!classLabels.contains(current)) {
   classLabels.add(current);
  }
 }
 KMeansClustering initCluster = new KMeansClustering(initData, classLabels.size(), classLabels);
 KMeansClustering initDoubleCluster = new KMeansClustering(initData, classLabels.size() * 2, classLabels);
 KMeansClustering initTripleCluster = new KMeansClustering(initData, classLabels.size() * 3, classLabels);
 
 KMeansClustering clusterEuc = initCluster.cluster(true);
 KMeansClustering doubleClusterEuc = initDoubleCluster.cluster(true);
 KMeansClustering tripleClusterEuc = initTripleCluster.cluster(true);
 KMeansClustering clusterMan = initCluster.cluster(false);
 KMeansClustering doubleClusterMan = initDoubleCluster.cluster(false);
 KMeansClustering tripleClusterMan = initTripleCluster.cluster(false);
  }
}