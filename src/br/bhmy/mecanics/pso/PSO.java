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
	
	public PSO (IChallenge challenge){
		this.challenge = challenge;
		this.setSwarm(new Particle[challenge.getNumberOfParticles()]);
		setFstIterationOfGBest(true);
	}
	
	public void init(){
		
		int qtyOfParticles = challenge.getNumberOfParticles();
		int numDimensions = challenge.getNumberOfDimensions();
		double minPosition = challenge.getMinPosition();
		double maxPosition = challenge.getMaxPosition();
		
		for (int i = 0; i < qtyOfParticles; i++) {
			Particle particle = new Particle(numDimensions);

			particle.setSpeed(Util.setInitState(numDimensions));
			
			double[] initialPosition = Util.getRandomPosition(minPosition, maxPosition, numDimensions);
			particle.setCurrentPosition(initialPosition);
			particle.setPBest(initialPosition);
			
			swarm[i] = particle;
		}
	}
	
	public void start(){
		int numberOfIterations = challenge.getNumberOfIterations();
		for (int i = 0; i < numberOfIterations; i++) {
			run(i);
		}
	}
	
	private void run(int iteration) {
		
		int qtyOfParticles = challenge.getNumberOfParticles();
		
		// verify bests positions
		for(int i = 0; i < qtyOfParticles; i++){
			Particle particle = swarm[i];
			updatePBest(particle);
			updateGBest(particle);
		}
		
		// print gBest
		System.out.println("GBest - "+ (iteration + 1) +": " + challenge.getFitness(gBest));
		
		// update position and speed
		for (int i = 0; i < qtyOfParticles; i++) {
			Particle particle = swarm[i];
			particle.updateParticle(Constants.C1, Constants.C2, gBest);
		}
	}

	public void updatePBest(Particle particle){
		double[] pBest = particle.getPBest();
		double[] currentPos = particle.getCurrentPosition();
		
		double fitnessPBest = challenge.getFitness(pBest);
		double fitnessCurrentPos = challenge.getFitness(currentPos);
		
		if(challenge.isThisFitnessBetter(fitnessCurrentPos, fitnessPBest)){
			particle.setPBest(currentPos);
		}
	}
	
	public void updateGBest(Particle particle){
		
		double[] pBest = particle.getPBest();
		double pBestFitness = challenge.getFitness(pBest);
		
		if(isFstIterationOfGBest){
			this.gBest = pBest;
			isFstIterationOfGBest = false;
		} else {
			double gBestFitness = challenge.getFitness(gBest);
			if(challenge.isThisFitnessBetter(pBestFitness, gBestFitness)){
				this.gBest = Arrays.copyOf(pBest, challenge.getNumberOfDimensions());
			}
		}
		
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

}
