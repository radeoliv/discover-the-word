package br.bhmy.challenges.functions;

import br.bhmy.challenges.IChallenge;

public class RastriginFunction implements IChallenge {

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

	public RastriginFunction() {
		numDimensions = 30;
		numberOfParticles = 30;
		maxPosition = 5;
		minPosition = -5;
		numberOfIterations = 10000;
		inertialWeight = 0.8;
		useConstriction = true;
		useDecayFactor 	= false;
		decayFactor = (maxRange - minRange) / numberOfIterations;
	}

	@Override
	public double getFitness(double... z) {
		double result = 0;
		int size = z.length;
		for (int i = 0; i < size; i++) {
			double z2 = z[i]*z[i];
			double cos = -10 * Math.cos(2 * Math.PI * z[i]);
			result += z2 - cos + 10;
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
	
	@Override
	public String getFunctionName() {
		return "RastriginFunction";
	}
}
