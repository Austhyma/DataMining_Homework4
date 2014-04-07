import java.util.*;

public class Cluster {
  private Data centroid;
  private ArrayList<Data> cluster;
  private double eucWSSmeasure= 0;
  private double manWSSmeasure = 0;
  private double entropy = 0;
  private double weightedEntropy = 0;
  
  public Cluster(Data centroid) {
    this.centroid = centroid;
    this.cluster = new ArrayList<Data>();
  }
  
  //Getters
  public double getEucWSS() {return this.eucWSSmeasure;}
  public Data getCentroid() {return this.centroid;}
  public ArrayList<Data> getCluster() {return this.cluster;}
  public double getManWSS() {return this.manWSSmeasure;}
  public double getWeightedEntropy() {return this.weightedEntropy;}
  //Setters
  public void setCentroid(Data centroid) {this.centroid = centroid;}
  public void setCluster(ArrayList<Data> cluster) {this.cluster = cluster;}
  public void setWeightedEntropy(double value) {this.weightedEntropy = value;}
  
  public double getEntropy() {return this.entropy;}
  
  public void calculateWSS() {
    for (int i = 0; i < cluster.size(); i++) {
      for (Iterator<String> stuff = cluster.get(i).getAttributes().keySet().iterator() ; stuff.hasNext();) {
        String current = stuff.next();
        double manValue = Math.pow(centroid.getAttribute(current) - cluster.get(i).getAttribute(current), 2);
        eucWSSmeasure += Math.pow(manValue, 2);
        manWSSmeasure += manValue;
      }
    }
  }
  
  public double classCount(String classLabel) {
    double counter = 0;
    for (int i = 0; i < cluster.size(); i++) {
      if (classLabel.equals(cluster.get(i).getClassLabel())) { counter++;}
    }
    return counter;
  }
  
  public double classCount2(String classLabel, ArrayList<Data> data) {
    double counter = 0; 
    for (int i = 0; i < data.size(); i++) {
      counter += (classLabel.equals(data.get(i).getClassLabel())) ? 1 : 0;
    }
    return counter;
  }
    
  public void addPoint(Data point) {
    this.cluster.add(point);
  }
  
  public double log(double value, double base) {
    return Math.log(value)/Math.log(base);
  }
  
  public void calculateEntropy(ArrayList<String> classLabels) {
    for (int i = 0; i < classLabels.size(); i++) {
      double probability = classCount(classLabels.get(i))/(double) cluster.size();
      if (probability == 0) {
        this.entropy += 0;
      }
      else {
        this.entropy -= (probability * log(probability, classLabels.size()));
      }
    }
  }
}





