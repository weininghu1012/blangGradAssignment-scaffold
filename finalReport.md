Weining Hu (45606134)

# Understanding the test
## Class ExhaustiveDebugRandom()

First create variables:
- oldDecision
- newDecison
- oldDeepestIncrementableBranch
- newDeepestIncrementableBranch
- currentDepth
- hasNext
- pr

Create a long variable called serialVersionUID to make sure if the object is eing serialized and deserialized, it could check back the initial


Construct the ExhaustiveDebugRandom object by call the Random Class and pass a seed (1)

### nextBernoulli() method:
In this method, a double value p is passed as the parameter for generating the next Bernoulli value

### effectiveProbabilities() method
Given a generated double array of probability, create a empty list of integer and add all the index from probabilities where its corresponding probability is larger than 1

### nextCategorical() method
In this method, we first check if there is still positive probability left for us to choose. Then we update the oldDecision by checking if it is null or if the currentDepth is larger than the oldDeepestIncrementableBranch. If either of the previous is true we set as 0 else the previous one. We add the new currentDecison to the previous one and check if there is still category not picked. Return the new category. 









## Class DiscreteMCTest()

First initialize these variables
- verbose (a boolean variable indicating if the message should be print out or not during testing)
- stateIndexer (a Indexer object that map between integer and state )
- targetDistribution (a DenseMatrix object with size 1 $\times$ nState() that indicating the target distribution for different states)
- transitionMatrices (a ArrayList object with transition kernels)


### DiscreteMCTest() constructor

This constructor takes in these parameters: 
- model (sampledModel to test)
- equalityAssessor (an assessor for comparing model and object)

### Initial Variables

- probabilities (a counter object)
- stateCopies (a linkedhashMap that )
- exhaustive (a ExhaustiveDebugRandom object that will ensure cover all states)
- normalization (a double object that represent the normalization (the denominator))

### Forward sampling
While (there is new state not exhaustively covered):
	use our sampledModel to get a new sample
	create a double object **probability** that is the realization (curPosterior/normalization)
	create a object representation of current model called **representative**
	keep record of our current model representation in **stateIndexer** and also increment the counter value in **probabilities**
	create a copy of current model and keep in **stateCopies**

### Calculate the target distribution
Using the previous procedure's result to get the target distribution

### Build transition kernals for each kernal
for each kernal:

	- matrix (a sparse matrix with size $nStates \times nStates$ )


	for each state:

		get the model from stateCopies

		initialize a new ExhaustiveDebugRandom() variable

		while (there is new state not exhaustively covered):

			get the current model

			do one step posterior sampling step

			get the next state representative

			keep record in the stateIndexer

			increment the probability in the matrix

### Check Invariance

Check the mixture of kernels by checking each individual kernel is invariant. This is due to the property that if each of the kernel is invariant, then the mixture of the kernels must be invariant. By checking each of them, we could find out which one did not pass.

### Check Irreduccibility

Check the irreducibility by checking the mixture of the kernels for their irreducibility. This is due to the property that even if all of the individual kernel is not irreducible, the mixture could also be irreducible. Thus we need to test the mixture.




### Run your model on synthetic data

The report is attached here:
[Posterior](https://github.com/weininghu1012/blangGradAssignment-scaffold/blob/master/nextflow/deliverables/permuted-clustering%202/permutations-posterior.pdf)




			











