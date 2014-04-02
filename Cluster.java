import java.util.*;

public class Cluster {
 private Data centroid;
 private ArrayList<Data> cluster;
 private double EucWSSmeasure;
 private double EucBSSmeasure;
 private double ManWSSmeasure;
 private double ManBSSmeasure;
 private double entropy;
 
 public Cluster(Data centroid, ArrayList<Data> cluster) {
  this.centroid = centroid;
  this.cluster = cluster;
 }
 
 //Getters
 public double getEucWSS() {return this.EucWSSmeasure;}
 public double getEucBSS() {return this.EucBSSmeasure;}
 public double getManWSS() {return this.ManWSSmeasure;}
 public double getManBSS() {return this.ManBSSmeasure;}
 public double getEntropy() {return this.entropy;}
 
 public void calculateEucWSS() {
   EucWSSmeasure = Math.abs(this.centroid.getAttributes() - cluster.getAttributes())^2;
 }
 
 
 //TODO
 public void calculateEucBSS(boolean Euclidean) {}
 

 public void calculateManWSS() {
   ManWSSmeasure = Math.abs(this.centroid.getAttributes() - cluster.getAttributes()); 
 }
 
 //TODO
 public void calculateManBSS(boolean Euclidean) {
   for(int i = 0; i<cluster.size(); i++){
     ManBSSmeasure = Math.abs(this.centroid.getAttributes() - cluster.get(i)getAttributes());
   }
 }
 
 //TODO
 public void calculateEntropy(boolean Euclidean) {
  }
}

 
