import java.util.*;

public class Cluster {
 private Data centroid;
 private ArrayList<Data> cluster;
 private double eucWSSmeasure= 0;
 private double manWSSmeasure = 0;
 private double entropy = 0;
 
 public Cluster(Data centroid, ArrayList<Data> cluster) {
  this.centroid = centroid;
  this.cluster = cluster;
 }
 
 //Getters
 public double getEucWSS() {return this.eucWSSmeasure;}
 public Data getCentroid() {return this.centroid;}
 public ArrayList<Data> getCluster() {return this.cluster;}
 public double getManWSS() {return this.manWSSmeasure;}
 
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
 
 public int classCount(String classLabel) {
   int counter = 0;
   for (int i = 0; i < cluster.size(); i++) {
     if (classLabel.equals(cluster.get(i).getClassLabel())) { counter++;}
   }
   return counter;
 }
     
 
 //TODO
 public void calculateEntropy() {
   for (int i = 0; i < cluster.size(); i++) {
        entropy -= -((classCount(cluster.get(i).getClassLabel()))/cluster.size()*Math.log(classCount(cluster.get(i).getClassLabel())))/Math.log(2);
      }
   }
 }
        
  


 
