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
	private double WSS;
	private double BSS;
	private double entropy;
	
	//Getters
	public double getWSS() {return this.WSS;}
	public double getBSS() {return this.BSS;}
	public double getEntropy() {return this.entropy;}
	
	//TODO
	public void calculateWSS() {}
	
	//TODO
	public void calculateBSS() {}
	
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
		
	}
	
	//TODO
	public void output(KMeansClustering current) {
		
	}
	
  
  public static void main(String[] args) throws IOException {
    ArrayList<Data> initData = readARFF(args[0]);
	ArrayList<String> classLabels = new ArrayList<String>();
	classLabels.add(initialData.get(0).getClassLabel());
	for (int i = 1; i < initialData.size(); i++) {
		String current = initialData.get(i).getClassLabel();
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