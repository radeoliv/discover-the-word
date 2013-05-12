package br.bhmy.challenges.functions;

import br.bhmy.challenges.IChallenge;

/**
 * @author Bruno Yamashita
 */

public class SphereFunction implements IChallenge {

	private int numDimensions;
	private int numberOfParticles;
	private int maxPosition;
	private int minPosition;
	private int numberOfIterations;
	private double inertialWeight;
	private boolean useConstriction;
	
	//decay factor
	private double maxRange = 0.8;
	private double minRange = 0.4;
	private double decayFactor;
	private boolean useDecayFactor;

	private double fBias = 0;
	
	public SphereFunction() {
		numDimensions = 30;
		numberOfParticles = 30;
		maxPosition = 100;
		minPosition = -100;
		numberOfIterations = 10000;
		inertialWeight = 0.8;
		useConstriction = true;
		useDecayFactor 	= false;
		decayFactor = (maxRange - minRange) / numberOfIterations;
	}

	@Override
	public double getFitness(double... dimension) {
		double result = 0;
		for (double d : dimension) {
			result += Math.pow(d, 2.0d) + fBias;
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

	@Override
	public double getDecayFactor(){
		return this.decayFactor;
	}

	@Override
	public boolean useDecayFactor() {
		return useDecayFactor;
	}

	public void setUseDecayFactor(boolean useDecayFactor) {
		this.useDecayFactor = useDecayFactor;
	}
}
