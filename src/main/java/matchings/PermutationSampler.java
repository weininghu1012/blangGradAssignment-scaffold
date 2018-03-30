package matchings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

import bayonet.distributions.Random;
import blang.core.LogScaleFactor;
import blang.distributions.Generators;
import blang.mcmc.ConnectedFactor;
import blang.mcmc.SampledVariable;
import blang.mcmc.Sampler;
import briefj.collections.UnorderedPair;

/**
 * Each time a Permutation is encountered in a Blang model, 
 * this sampler will be instantiated. 
 */
public class PermutationSampler implements Sampler {
  /**
   * This field will be populated automatically with the 
   * permutation being sampled. 
   */
  @SampledVariable Permutation permutation;
  /**
   * This will contain all the elements of the prior or likelihood 
   * (collectively, factors), that depend on the permutation being 
   * resampled. 
   */
  @ConnectedFactor List<LogScaleFactor> numericFactors;

  @Override
  public void execute(Random rand) {
    // Fill this.
//	  System.out.println("Previous -------------------------------------");
permutation.getConnections();
      
	  
//	  System.out.println("Current -------------------------------------");
//	  double[] probabilities = {0.1,0.1,0.8};
	  
//	  long n = (long)rand.nextCategorical(probabilities) + 1;
//	  Random curRandom = new Random(n);
//	  permutation.sampleUniform(curRandom);
System.out.println("Current -------------------------------------");
      List<Integer> currentConnections = permutation.getConnections();
      System.out.println(currentConnections);
      ArrayList<Integer> deepCurrentConnections = new ArrayList<Integer>(currentConnections);
      double currentDensity = logDensity();
      
      permutation.sampleUniform(rand);
      List<Integer> newConnections = permutation.getConnections();
      
	  double newDensity = logDensity();
	  System.out.println("deepCurrent -------------------------------------");
	  System.out.println(deepCurrentConnections);
	  System.out.println("newConnections -------------------------------------");
	  System.out.println(newConnections);
	  double alpha = Math.min(1, Math.exp(newDensity)/Math.exp(currentDensity));
	  //double alpha = Math.min(1, (newDensity)/(currentDensity));
	 //Random currand = new Random();
	  double u = Math.random();
	  System.out.println("Alpha is");
	  System.out.println(alpha);
	  System.out.println("u is");
	  System.out.println(u);
	  if (alpha >= u) {
		 // permutation.setConnections(permutation.getConnection);
		  for (int i = 0; i < newConnections.size(); i ++ ) {
			  deepCurrentConnections.set(i, newConnections.get(i));
			
		  }
		  System.out.println("The new sampled one is");
		  System.out.println(permutation.getConnections());
	  }
	  
	  
	    
	  
  }
  
  private double logDensity() {
    double sum = 0.0;
    for (LogScaleFactor f : numericFactors)
      sum += f.logDensity();
    return sum;
  }
}
