package matchings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bayonet.distributions.Multinomial;
import bayonet.distributions.Random;
import blang.core.LogScaleFactor;
import blang.distributions.Generators;
import blang.mcmc.ConnectedFactor;
import blang.mcmc.SampledVariable;
import blang.mcmc.Sampler;

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
  long startTime = System.nanoTime();
  List<Integer> currentConnections;

  double currentDensity;
  double newDensity;
  double alpha;
  double u;
  int firstIndex;
  int secondIndex;

  @Override
  public void execute(Random rand) {
      long startTime1 = System.nanoTime();
      currentConnections = matching.getConnections();
      ArrayList<Integer> deepCurrentConnections = new ArrayList<Integer>(currentConnections);
     
      currentDensity = logDensity();
      
      //permutation.sampleUniform(rand);
      firstIndex = Generators.distinctPair(rand, currentConnections.size()).getFirst();
      secondIndex = Generators.distinctPair(rand, currentConnections.size()).getSecond();
      
      Collections.swap(matching.getConnections(), firstIndex, secondIndex);

	  newDensity = logDensity();
	  alpha = Math.min(1, Math.exp(newDensity)/Math.exp(currentDensity));
	  u = Math.random();
	  long startTime2 = System.nanoTime();
	  if (alpha < u) {
		  for (int i = 0; i < currentConnections.size(); i ++ ) {
			  matching.getConnections().set(i, deepCurrentConnections.get(i));
			
		  }	


		 
	  }
	  long endTime = System.nanoTime();
	  long totalTime1 = endTime - startTime2;
	  long totalTime2 = endTime - startTime1;
	  System.out.println("The running time of sampling is" + Long.toString(totalTime1));
	  System.out.println("The total running time is" + Long.toString(totalTime2));
  }
  
  private double logDensity() {
    double sum = 0.0;
    for (LogScaleFactor f : numericFactors)
      sum += f.logDensity();
    return sum;
  }
}
