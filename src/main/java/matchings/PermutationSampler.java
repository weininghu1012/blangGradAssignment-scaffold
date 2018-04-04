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
  

  List<Integer> currentConnections;

  double currentDensity;
  double newDensity;
  double alpha;
  double u;
  int firstIndex;
  int secondIndex;
  double[] sizeProbabilities;
  int chooseSize;

  @Override
  public void execute(Random rand) {	  
      currentConnections = permutation.getConnections();
      ArrayList<Integer> deepCurrentConnections = new ArrayList<Integer>(currentConnections);
     
      currentDensity = logDensity();
      
      permutation.sampleUniform(rand);

	  newDensity = logDensity();
	  alpha = Math.min(1, Math.exp(newDensity)/Math.exp(currentDensity));
	  u = Math.random();
	  if (alpha < u) {
		  for (int i = 0; i < currentConnections.size(); i ++ ) {
			  permutation.getConnections().set(i, deepCurrentConnections.get(i));
			
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
