package br.bhmy.challenges;

/**
 * @author Bruno Yamashita
 */

public interface IChallenge {
	
	/**
	 * @return Number of particles in PSO
	 */
	int getNumberOfParticles();
	
	/**
	 * Need define the fitness value on function
	 * @param dimension
	 * @return fitness value to that point
	 */
	double getFitness(double... dimension);
	
	/**
	 * This method should return the number of dimensions to this challenge 
	 * @return
	 */
	int getNumberOfDimensions();
	
	/**
	 * This method should return the max position of the particles
	 * @return
	 */
	double getMaxPosition();

	/**
	 * This method should return the minimum position of the particles
	 * @return
	 */
	double getMinPosition();

	/**
	 * This method returns number of iterations of this challenge
	 * @return
	 */
	int getNumberOfIterations();

	/**
	 * This method need to do the comparation between this values and choice the better fitness
	 * @param fitness1 - new fitness
	 * @param fitness2 - best fitness
	 * @return True, if fit1 is better than fit2. False, if the opposite occurs 
	 */
	boolean isThisFitnessBetter(double newFitness, double bestFitness);
	
	
	
}