import java.util.*;

public class Cluster {
	private Data centroid;
	private ArrayList<Data> cluster;
	private double WSSmeasure;
	private double BSSmeasure;
	private double entropy;
	
	public Cluster(Data centroid, ArrayList<Data> cluster) {
		this.centroid = centroid;
		this.cluster = cluster;
	}
	
	//Getters
	public double getWSS() {return this.WSSmeasure;}
	public double getBSS() {return this.BSSmeasure;}
	public double getEntropy() {return this.entropy;}
	
	public void calculateWSS() {}
	
	public void calculateBSS() {}
	
	public void calculateEntropy() {}
}