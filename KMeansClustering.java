/*
 * File: KMeansClustering.java
 */

import java.io.*;
import java.util.*;
import java.math.*;

public class KMeansClustering {
  
  private int k;
  private ArrayList<Data> data;
  private ArrayList<Cluster> clusters = new ArrayList<Cluster>();
  private ArrayList<String> classLabels;
  private double manWSS = 0;
  private double eucWSS = 0;
  private double infoGain;
  private double manBSS = 0;
  private double eucBSS = 0;
  private boolean euclidean;
  private double weightedEntropy;
  
  
  //Getters
  public double getEucWSS() {return this.eucWSS;}
  public double getManWSS() {return this.manWSS;}
  public double getEucBSS() {return this.eucBSS;}
  public double getManBSS() {return this.manBSS;}
  public double getEntropy() {return this.infoGain;}
  public ArrayList<Cluster> getClusters() {return this.clusters;}
  public boolean getEuclidean() {return this.euclidean;}
  public double getWeightedEntropy() {return this.weightedEntropy;}
  public int getK() {return k;}
  public ArrayList<Data> getData() {return data;}
  
  public void calculateWSS() {
    for (int i = 0; i < clusters.size(); i++) {
      clusters.get(i).calculateWSS();
      eucWSS += clusters.get(i).getEucWSS();
      manWSS += clusters.get(i).getManWSS();
    }  
  }
  
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
  public void calculateEntropy() {
    for (int i = 0; i < clusters.size(); i++) {
      clusters.get(i).calculateEntropy(classLabels);
      infoGain += 1 - ((clusters.get(i).classCount(clusters.get(i).getCluster().get(i).getClassLabel())/clusters.get(i).getCluster().size()) * clusters.get(i).getEntropy());
    }
  }           
  
  public KMeansClustering(ArrayList<Data> data, int k, ArrayList<String> classLabels, boolean euclidean) {
    this.k = k;
    this.data = data;
    this.classLabels = classLabels;
    this.euclidean = euclidean;
    kMeans();
  }
  
  public KMeansClustering(String filename, int k) throws IOException {
    this.k = k;
    this.data = readARFF(filename);
    kMeans();
  }
  
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
  public void kMeans() {
    ArrayList<Data> centroids = new ArrayList<Data>();
    //select k points as initial centroids
    for (int i = 0; i < k; i++) {
      boolean valid = false;
      Data centroid = new Data();
      while (!valid) {
        int randomValue = (int) Math.round(Math.random()*(this.data.size() - 1));
        centroid = this.data.get(randomValue);
        valid = !centroids.contains(centroid);
      }
      this.clusters.add(new Cluster(centroid));
      centroids.add(centroid);
    }
    ArrayList<Data> oldCentroids = centroids;
    int iterations = 0;
    boolean complete = false;
    while (!complete && iterations<100) {
      
      System.out.println("Iteration: " + iterations);
//      for (int i = 0; i < centroids.size(); i++) {
//        String line = "Cluster: " + i + " =";
//        int count = 0;
//        for (Iterator<String> attribute = centroids.get(i).getAttributes().keySet().iterator(); attribute.hasNext();) {
//          String current = attribute.next();
//          if (count > 3) break;
//          else line += " " + current + ": " + round(centroids.get(i).getAttributes().get(current), 5);
//          count++;
//        }
//        System.out.println(line);
//      }
      cluster();
//      System.out.println("Iteration: " + iterations);
//      for (int i = 0; i < this.clusters.size(); i++) {
//        System.out.println("\tCluster " + i + ": " + this.clusters.get(i).getCluster().size());
//      }
      centroids = computeCentroid();
      iterations++;
      complete = converge(centroids, oldCentroids);
      oldCentroids = centroids;
    }
  }
  
  public boolean converge(ArrayList<Data> centroids, ArrayList<Data> oldCentroids) {
    boolean equal = true;
    for (int i = 0; i < centroids.size(); i++) {
      for (Iterator<String> attribute = centroids.get(i).getAttributes().keySet().iterator(); attribute.hasNext();) {
        String current = attribute.next();
        if (round(centroids.get(i).getAttribute(current), 5) != round(oldCentroids.get(i).getAttribute(current), 5)) {
          equal = false; 
        }
      }//endForEach
    }//EndFor
    return equal;
  }
  
  public void cluster() {
    //Associates points with nearest centroid
    for (int i = 0; i < this.data.size(); i++) {
      int closestCentroid = 0;
      double smallest = Double.POSITIVE_INFINITY;
      for (int j = 0; j < this.clusters.size(); j++) {
        double distance = distance(this.data.get(i), this.clusters.get(j).getCentroid(), euclidean);
        if (distance < smallest) {closestCentroid = j; smallest = distance;}
      }
      this.data.get(i).setClosestCentroid(closestCentroid);
    }
    //Adds all points to their nearest cluster
    for (int i = 0; i < this.clusters.size(); i++) {
      ArrayList<Data> newStuff = new ArrayList<Data>();
      for (int j = 0; j < this.data.size(); j++) {
        if (this.data.get(j).getClosestCentroid() == i) newStuff.add(this.data.get(j));
      }
      this.clusters.get(i).setCluster(newStuff);
    }
  }
  
  public double distance(Data current, Data centroid, boolean euclidean) {
    double distance = 0;
    for (Iterator<String> attribute = current.getAttributes().keySet().iterator(); attribute.hasNext();) {
      String currentAttribute = attribute.next();
      double manValue = Math.abs(current.getAttribute(currentAttribute) - centroid.getAttribute(currentAttribute));
      distance += (euclidean) ? Math.pow(manValue, 2) : manValue;
    }
    return distance;
  }
  
  public double round(double value, int places) {
    return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
  }
  
  public ArrayList<Data> computeCentroid() {
    ArrayList<Data> returnValues = new ArrayList<Data>();
    //For each cluster
    for (int i = 0; i < this.clusters.size(); i++) {
      HashMap<String, Double> dataAttributes = new HashMap<String, Double>();
      //For each point in each cluster
      for (int j = 0; j < this.clusters.get(i).getCluster().size(); j++) {
        Data data = this.clusters.get(i).getCluster().get(j);
        for (Iterator<String> attribute = clusters.get(i).getCentroid().getAttributes().keySet().iterator(); attribute.hasNext();) {
          String current = attribute.next();
          double total = (double) this.clusters.get(i).getCluster().size();
          double value = (j == 0) ? (data.getAttribute(current)/total) : (dataAttributes.get(current) + data.getAttribute(current)/total);
          dataAttributes.put(current, value);
        }
      }
      this.clusters.get(i).setCentroid(new Data(dataAttributes)); 
      returnValues.add(new Data(dataAttributes));
    }
    return returnValues;
  }
  
  
  //TODO
  public static void output(ArrayList<KMeansClustering> current, String filename) throws IOException {
    //Display clusters?
    
    
    PrintWriter outFile = new PrintWriter(new FileWriter(filename +  "_results.csv"));
    
    //Report cohesion and separation using WSS and BSS
    for(int i = 0; i < current.size(); i++) {
      String tableColumns = (current.get(i).getEuclidean()) ? "Euclidean," : "Manhattan,";
      tableColumns += "WSS,BSS,InformationGain,WeightedEntropy";
      outFile.println(tableColumns);
      if(current.get(i).getEuclidean()){     
        String line = "k = " + current.get(i).getK() + ", " + current.get(i).getEucWSS() + ", " + current.get(i).getEucBSS() + ", " 
          + current.get(i).getEntropy() + ", " + current.get(i).getWeightedEntropy() + ", ";
        outFile.println(line);
      }else {     
        String line = "k = " + current.get(i).getK() + ", " + current.get(i).getManWSS() + ", " + current.get(i).getManBSS() + ", " 
          + current.get(i).getEntropy() + ", " + current.get(i).getWeightedEntropy() + ", ";
        outFile.println(line);
      } 
    }   
    outFile.println(" ");
    
    //Report Entropy per cluster with total weighted entropy
    for(int i = 0; i < current.size(); i++) {
      String clusterColumns = (current.get(i).getEuclidean()) ? "Euclidean," : "Manhattan,";
      clusterColumns += "Entropy, Weighted Entropy";
      outFile.println(clusterColumns);
      if(current.get(i).getEuclidean()){  
        for(int j = 0; j< current.get(i).clusters.size(); j++) {
          String nLine = j + ", " + current.get(i).clusters.get(j).getEntropy() + ", " 
            + current.get(i).clusters.get(j).getWeightedEntropy();
          outFile.println(nLine);
        }
      }else {
        for(int j = 0; j< current.get(i).clusters.size(); j++) {
          String nLine = j + ", " + current.get(i).clusters.get(j).getEntropy() + ", " 
            + current.get(i).clusters.get(j).getWeightedEntropy();
          outFile.println(nLine);
        }
      }
    }
    outFile.close();
  }
  
  
  
  public static void main(String[] args) throws IOException {
    ArrayList<Data> initData = readARFF(args[0]);
    ArrayList<String> classLabels = new ArrayList<String>();
    classLabels.add(initData.get(0).getClassLabel());
    for (int i = 1; i < initData.size(); i++) {
      String current = initData.get(i).getClassLabel();
      if (!classLabels.contains(current) && !current.equals("")) {
        classLabels.add(current);
      }
    }
    
    KMeansClustering clusterEuc = new KMeansClustering(initData, classLabels.size(), classLabels, true);
    KMeansClustering doubleClusterEuc = new KMeansClustering(initData, classLabels.size() * 2, classLabels, true);
    KMeansClustering tripleClusterEuc = new KMeansClustering(initData, classLabels.size() * 3, classLabels, true);
    KMeansClustering clusterMan = new KMeansClustering(initData, classLabels.size(), classLabels, false);
    KMeansClustering doubleClusterMan = new KMeansClustering(initData, classLabels.size() * 2, classLabels, false);
    KMeansClustering tripleClusterMan = new KMeansClustering(initData, classLabels.size() * 3, classLabels, false);
    ArrayList<KMeansClustering> stuff = new ArrayList<KMeansClustering>(Arrays.asList(clusterEuc, doubleClusterEuc, tripleClusterEuc, clusterMan, doubleClusterMan, tripleClusterMan));
    String filename = args[0].substring(0, args[0].indexOf("."));
    output(stuff, filename);
  }
}