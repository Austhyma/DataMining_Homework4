/*
 * File: Data.java
 */

import java.util.*;

public class Data {
  
  protected int iD;
  protected HashMap<String, Double> attributes;
  protected String classLabel;
  
  public Data(HashMap<String, Double> dimensions, String classLabel, int iD) {
    this.attributes = dimensions;
    this.classLabel = classLabel;
    this.iD = iD;
  }
  
  public String getClassLabel() {return classLabel;}
  public HashMap<String, Double> getAttributes() {return this.attributes;}
  public int getID() {return this.iD;}
}