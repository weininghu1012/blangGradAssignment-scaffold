//package matchings;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import static java.util.Collections.shuffle;
//
//import bayonet.distributions.Multinomial;
//import bayonet.distributions.Random;
//import blang.core.LogScaleFactor;
//import blang.distributions.Generators;
//import blang.mcmc.ConnectedFactor;
//import blang.mcmc.SampledVariable;
//import blang.mcmc.Sampler;
//import briefj.collections.UnorderedPair;
//
///**
// * Each time a Permutation is encountered in a Blang model, 
// * this sampler will be instantiated. 
// */
//public class BipartiteMatchingSampler implements Sampler {
//  /**
//   * This field will be populated automatically with the 
//   * permutation being sampled. 
//   */
//  @SampledVariable BipartiteMatching matching;
//  /**
//   * This will contain all the elements of the prior or likelihood 
//   * (collectively, factors), that depend on the permutation being 
//   * resampled. 
//   */
//  @ConnectedFactor List<LogScaleFactor> numericFactors;
//  long startTime = System.nanoTime();
//  List<Integer> currentConnections;
//
//  double currentDensity;
//  double newDensity;
//  double alpha;
//  double u;
//  int firstIndex;
//  int secondIndex;
//  int newSize;
//  double[] chooseSizeProbabilities = new double[5];
//
//  
//  @Override
//  public void execute(Random rand) {
//      currentConnections = matching.getConnections();
//      ArrayList<Integer> deepCurrentConnections = new ArrayList<Integer>(currentConnections);
//      
//
//	  
//
//     
//      currentDensity = logDensity();
//      
//      
//	  Arrays.fill(chooseSizeProbabilities, 0.2);
//	  
//	  newSize = Generators.categorical(rand, chooseSizeProbabilities);
//	  
//	  ArrayList<Integer> occupied = new ArrayList<>(5);
//	  ArrayList<Integer> newConnections = new ArrayList<>(5);
//	  for (int i = 0; i < 5; i++) {
//		  occupied.add(i);
//		  newConnections.add(i);
//		  
//	  }
//	  
//	  Collections.shuffle(occupied);
//	  Collections.shuffle(newConnections);
//	  
//	  
////	  UnorderedPair<Integer,Integer> firstShuffle = Generators.distinctPair(rand, 5);
////	  UnorderedPair<Integer,Integer> secondShuffle = Generators.distinctPair(rand, 5);
////
////	      firstIndex = firstShuffle.getFirst();
////	      secondIndex = firstShuffle.getSecond();
////	      Collections.swap(occupied, firstIndex, secondIndex);
////	      ArrayList<Integer> partOccupied = new ArrayList(occupied.subList(0, newSize));
////	      
////	      int firstIndexP = secondShuffle.getFirst();
////	      int secondIndexP = secondShuffle.getSecond();
////	      Collections.swap(newConnections, firstIndexP, secondIndexP);
////	      ArrayList<Integer> partNewConnections = new ArrayList(newConnections.subList(0, newSize));
//	      
//	      for (int j = 0; j < 5; j++) {
//	    	  matching.getConnections().set(j, -1);
//	      }
//	      
//	      for (int k = 0; k < newSize; k++) {
//	    	  matching.getConnections().set(occupied.get(k), occupied.get(k));
//	      }
//      
//      //permutation.sampleUniform(rand);
//	     newDensity = logDensity();
//	  alpha = Math.min(1, Math.exp(newDensity)/Math.exp(currentDensity));
//	  u = Math.random();
//	  if (alpha < u) {
//		  for (int i = 0; i < 5; i ++ ) {
//			  matching.getConnections().set(i, deepCurrentConnections.get(i));
//			
//		  }	
//	  }
//  }
//  
//  private double logDensity() {
//    double sum = 0.0;
//    for (LogScaleFactor f : numericFactors)
//      sum += f.logDensity();
//    return sum;
//  }
//}
package matchings;

import java.util.ArrayList;
import java.util.List;
import bayonet.math.*; 
import bayonet.distributions.Multinomial;
import bayonet.distributions.Random;
import blang.core.LogScaleFactor;
import blang.distributions.Generators;
import blang.mcmc.ConnectedFactor;
import blang.mcmc.SampledVariable;
import blang.mcmc.Sampler;

import static java.util.Collections.shuffle;


/**
 * Each time a Permutation is encountered in a Blang model, 
 * this sampler will be instantiated. 
 */
public class BipartiteMatchingSampler implements Sampler {
  /**
   * This field will be populated automatically with the 
   * permutation being sampled. 
   */
  @SampledVariable BipartiteMatching matching;
  /**
   * This will contain all the elements of the prior or likelihood 
   * (collectively, factors), that depend on the permutation being 
   * resampled. 
   */
  @ConnectedFactor List<LogScaleFactor> numericFactors;
  
  @Override
  public void execute(Random rand) {
        List<Integer> currentConnections = matching.getConnections();
        ArrayList<Integer> deepCurrentConnections = new ArrayList<Integer>(currentConnections);
        double currentDensity = logDensity();
        
        
        int changeIndex = rand.nextInt(matching.getConnections().size());
        ArrayList<Integer> leftOver = new ArrayList<Integer>();
        for (int i = 0; i < matching.getConnections().size(); i ++) {
        	if (!currentConnections.contains(i)) {
        		leftOver.add(i);

        	}
        }
        
        if (matching.getConnections().get(changeIndex) == -1) {
        		int leftSize = leftOver.size();
            int randIndex = rand.nextInt(leftSize);
        		matching.getConnections().set(changeIndex, leftOver.get(randIndex));
        	
        } else {
        	leftOver.add(-1);
        	int leftSize = leftOver.size();
         int randIndex = rand.nextInt(leftSize);
        		matching.getConnections().set(changeIndex, leftOver.get(randIndex));
        	
        }
        
  	  double newDensity = logDensity();
  	  double alpha = Math.min(1, Math.exp(newDensity)/Math.exp(currentDensity));
  	  double u = Math.random();
  	  if (alpha < u) {
  		  for (int i = 0; i < deepCurrentConnections.size(); i ++ ) {
  			  matching.getConnections().set(i, deepCurrentConnections.get(i));
  			
  		  }		  
  	  }
}


  
  
  private double logDensity() {
    double sum = 0.0;
    for (LogScaleFactor f : numericFactors)
      sum += f.logDensity();
    return sum;
  }
  

}