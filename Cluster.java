import java.util.*;

public class Cluster {
	private Data centroid;
	private ArrayList<Data> cluster;
	
	public Cluster(Data centroid, ArrayList<Data> cluster) {
		this.centroid = centroid;
		this.cluster = cluster;
	}
}