/*
 * File: KMeansClustering.java
 */

import java.io.*;
import java.util.*;

public class KMeansClustering {
  
  private int k;
  private ArrayList<Data> data;
  
  public KMeansClustering(ArrayList<Data> data, int k) {
    this.k = k;
    this.data = data;
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
  
  public static void main(String[] args) throws IOException {
    ArrayList<Data> initialData = readARFF(args[0]);
  }
}