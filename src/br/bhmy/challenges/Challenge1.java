package br.bhmy.challenges;

/**
 * @author Bruno Yamashita
 */

public class Challenge1 implements IChallenge {

	private int numDimensions;
	private int numberOfParticles;
	private int maxPosition;
	private int minPosition;
	private int numberOfIterations;
	private double inertialWeight;
	private boolean useConstriction;
	
	public Challenge1() {
		numDimensions = 30;
		numberOfParticles = 30;
		maxPosition = 100;
		minPosition = -100;
		numberOfIterations = 10000;
		inertialWeight = 0.72984;
	}

	@Override
	public double getFitness(double... dimension) {
		double result = 0;
		for (double d : dimension) {
			result += Math.pow(d, 2.0d);
		}
		return result;
	}

	@Override
	public boolean isThisFitnessBetter(double fitness1, double fitness2) {
		return fitness1 < fitness2;
	}

	@Override
	public int getNumberOfDimensions() {
		return numDimensions;
	}

	@Override
	public int getNumberOfParticles() {
		return numberOfParticles;
	}

	@Override
	public double getMaxPosition() {
		return maxPosition;
	}

	@Override
	public double getMinPosition() {
		return minPosition;
	}

	@Override
	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	@Override
	public int getFocalIndex() {
		return 0;
	}

	@Override
	public double getInertialWeight() {
		return inertialWeight;
	}

	@Override
	public void setInertialWeight(double inertialWeight) {
		this.inertialWeight = inertialWeight;
	}

	@Override
	public boolean useConstriction() {
		return this.useConstriction;
	}

}
