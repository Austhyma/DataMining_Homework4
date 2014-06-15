DataMining_Homework4
====================
This project implements the K-Means Clustering algorithm using supervised clustering. It uses the number of class labels, twice the number of class labels, and thrice the number of class labels for values of k. The initial centroids are randomly placed amongst the samples. The algorithm ends once the centroids have converged, or if the number of iterations has reached 100.
Once the algorithm is complete, the clusters are analyzed based off of entropy/Information Gain.
====================

In order to run the program, compile using javac as such:

	javac KMeansClustering.java
	
and then: 

	java KMeansClustering <fileToBeClustered>
	
Returns file: <filename>_results.csv into the same directory of your file. 
