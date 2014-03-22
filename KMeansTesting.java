/*
 * File: Testing File
 */

import junit.framework.*;
import java.io.*;

public class KMeansTesting extends TestCase {
  
  private KMeansClustering k1, k2;
  
  public void setUp() throws IOException {
    k1 = new KMeansClustering("Files/AllGenes.arff", 3);
    k2 = new KMeansClustering("Files/iris.arff", 5);
  }
  
  public void testConstructors() {
    assertEquals(k1.getK(), 3);
    assertEquals(k2.getK(), 5);
    assertFalse(k1.getData().equals(null));
    assertEquals(k1.getData().size(), 72);
    assertEquals(k2.getData().size(), 151);
  }
}
