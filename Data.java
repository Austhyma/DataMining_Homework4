/*
 * File: Data.java
 */

import java.util.*;
import java.math.*;

public class Data {
  
  protected int iD;
  protected HashMap<String, Double> attributes;
  protected String classLabel;
  
  public Data(HashMap<String, Double> dimensions, String classLabel, int iD) {
    this.attributes = dimensions;
    this.classLabel = classLabel;
    this.iD = iD;
  }
  
  public Data(HashMap<String, Double> dimensions) {
    this.attributes = dimensions;

  }
  
  public String getClassLabel() {return classLabel;}
  public HashMap<String, Double> getAttributes() {return this.attributes;}
  public int getID() {return this.iD;}
  
  public double getAttribute(String key) {
    return attributes.get(key);
  }
  
  public double round(double value, int places) {
    return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
  }

  public boolean equals(Data other) {
    boolean equal = true;
    for (Iterator<String> attribute = this.attributes.keySet().iterator(); attribute.hasNext();) {
      String current = attribute.next();
      if (round(this.attributes.get(current), 5) != round(other.getAttribute(current), 5)) {equal = false; break;}
    }
    return equal;
  }
}