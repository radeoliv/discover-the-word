package br.bhmy.mecanics.pso;

import java.util.Arrays;

import br.bhmy.challenges.IChallenge;
import br.bhmy.constants.Constants;
import br.bhmy.util.Util;

/**
 * @author Bruno Yamashita
 */

public class PSO {
	
	private IChallenge challenge;
	private Particle[] swarm;
	private double[] gBest;
	private boolean isFstIterationOfGBest;
	private int topologyType;
	private boolean isDebug;
	private boolean useConstriction;
	
	public PSO (IChallenge challenge, int topologyType, boolean isDebug){
		this.challenge = challenge;
		this.setSwarm(new Particle[challenge.getNumberOfParticles()]);
		setFstIterationOfGBest(true);
		this.topologyType = topologyType;
		this.isDebug = isDebug;
		this.useConstriction = challenge.useConstriction();
	}
	
	public void init(){
		
		int qtyOfParticles = challenge.getNumberOfParticles();
		int numDimensions  = challenge.getNumberOfDimensions();
		double minPosition = challenge.getMinPosition();
		double maxPosition = challenge.getMaxPosition();
		
		for (int i = 0; i < qtyOfParticles; i++) {
			Particle particle = new Particle(numDimensions, minPosition, maxPosition);

			particle.setSpeed(Util.setInitState(numDimensions, minPosition, maxPosition));
			
			double[] initialPosition = Util.getRandomPosition(minPosition, maxPosition, numDimensions);
			particle.setCurrentPosition(Arrays.copyOf(initialPosition, numDimensions));
			particle.setPBest(Arrays.copyOf(initialPosition, numDimensions));
			
			swarm[i] = particle;
		}
	}
	
	public void start(){
		int numberOfIterations = challenge.getNumberOfIterations();
		for (int i = 0; i < numberOfIterations; i++) {
			run(i);
		}
		System.out.println("GBest["+ getTopologyName(topologyType) +"]: " + challenge.getFitness(gBest));
	}
	
	private void run(int iteration) {
		
		int qtyOfParticles = challenge.getNumberOfParticles();
		
		// verify bests positions
		for(int i = 0; i < qtyOfParticles; i++){
			Particle particle = swarm[i];
			updatePBest(particle);
			updateGBest(particle);
		}
		
		if(isDebug){
			System.out.println("GBest["+ getTopologyName(topologyType) +"] - "+ (iteration + 1) +": " + challenge.getFitness(gBest));
		}
		
		// update position and speed
		for (int i = 0; i < qtyOfParticles; i++) {
			
			Particle particle = swarm[i];
			double w = challenge.getInertialWeight();
			
			switch (topologyType) {
			case Constants.GLOBAL:
				particle.updateParticle(w, Constants.C1, Constants.C2, gBest, useConstriction);
				break;
			case Constants.LOCAL:
				int bestLocalIndex = bestNeighboorIndex(i);
				particle.updateParticle(w, Constants.C1, Constants.C2, swarm[bestLocalIndex].getPBest(), useConstriction);
				break;
			case Constants.FOCAL:
				int focalIndex = challenge.getFocalIndex();
				if(i == focalIndex){
					particle.updateParticle(w, Constants.C1, Constants.C2, gBest, useConstriction);
				} else {
					int bestFocalIndex = bestFocalIndex(i);
					particle.updateParticle(w, Constants.C1, Constants.C2, swarm[bestFocalIndex].getPBest(), useConstriction);
				}
				break;
			default:
				break;
			}
			
		}
	}

	public void updatePBest(Particle particle){
		double[] pBest = particle.getPBest();
		double[] currentPos = particle.getCurrentPosition();
		
		double fitnessPBest = challenge.getFitness(pBest);
		double fitnessCurrentPos = challenge.getFitness(currentPos);
		
		if(challenge.isThisFitnessBetter(fitnessCurrentPos, fitnessPBest)){
			particle.setPBest(Arrays.copyOf(currentPos, challenge.getNumberOfDimensions()));
		}
	}
	
	public void updateGBest(Particle particle){
		
		double[] pBest = particle.getPBest();
		double pBestFitness = challenge.getFitness(pBest);
		
		if(isFstIterationOfGBest){
			this.gBest = Arrays.copyOf(pBest, challenge.getNumberOfDimensions());
			isFstIterationOfGBest = false;
		} else {
			double gBestFitness = challenge.getFitness(gBest);
			if(challenge.isThisFitnessBetter(pBestFitness, gBestFitness)){
				this.gBest = Arrays.copyOf(pBest, challenge.getNumberOfDimensions());
			}
		}
	}
	
	public int bestNeighboorIndex(int index){
		
		int lastIndex = challenge.getNumberOfParticles() - 1;
		
		int currentIndex = index;
		int leftIndex = (index > 0) ? index - 1 : lastIndex;
		int rightIndex = (index < lastIndex) ? index + 1 : 0;
		
		double fitCurrentParticle = challenge.getFitness(swarm[currentIndex].getPBest());
		double fitLeftParticle = challenge.getFitness(swarm[leftIndex].getPBest());
		double fitRightParticle = challenge.getFitness(swarm[rightIndex].getPBest());
		
		int bestFitnessIndex = index;
		double bestFitness = fitCurrentParticle;
		
		if(challenge.isThisFitnessBetter(fitLeftParticle, fitCurrentParticle)){
			bestFitnessIndex = leftIndex;
			bestFitness = fitLeftParticle;
		}
		
		if(challenge.isThisFitnessBetter(fitRightParticle, bestFitness)){
			bestFitnessIndex = rightIndex;
		}
		
//		System.out.println(fitLeftParticle + " " + fitCurrentParticle + " " + fitRightParticle);
//		System.out.println(index + " " + bestFitnessIndex);
//		System.out.println();
		
		return bestFitnessIndex;
	}
	
	public int bestFocalIndex(int index){
		int focalIndex = challenge.getFocalIndex();
		
		double focalFitness = challenge.getFitness(swarm[focalIndex].getPBest());
		double currentFitness = challenge.getFitness(swarm[index].getPBest());
		
		int bestFitnessIndex = focalIndex;
		
		if(challenge.isThisFitnessBetter(currentFitness, focalFitness)){
			bestFitnessIndex = index;
		}
		
		return bestFitnessIndex;
	}
	
	private String getTopologyName(int topology){
		String result = "";
		switch (topology) {
		case Constants.GLOBAL:
			result = "GLOBAL";
			break;
		case Constants.LOCAL:
			result = "LOCAL";
			break;
		case Constants.FOCAL:
			result = "FOCAL";
			break;
		default:
			break;
		}
		return result;
	}
	
	public Particle[] getSwarm() {
		return swarm;
	}

	public void setSwarm(Particle[] swarm) {
		this.swarm = swarm;
	}

	public double[] getgBest() {
		return gBest;
	}

	public void setgBest(double[] gBest) {
		this.gBest = gBest;
	}

	public boolean isFstIterationOfGBest() {
		return isFstIterationOfGBest;
	}

	public void setFstIterationOfGBest(boolean isFstIterationOfGBest) {
		this.isFstIterationOfGBest = isFstIterationOfGBest;
	}

	public int getTopologyType() {
		return topologyType;
	}

	public void setTopologyType(int topologyType) {
		this.topologyType = topologyType;
	}

}
