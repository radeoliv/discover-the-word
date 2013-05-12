package br.bhmy.mecanics.pso;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import br.bhmy.challenges.IChallenge;
import br.bhmy.constants.Constants;
import br.bhmy.util.Util;

/**
 * @author Bruno Yamashita
 */

public class PSO {
	
	// print matlab codes on TXT
	private BufferedWriter writer;
	
	private IChallenge challenge;
	private Particle[] swarm;
	private double[] gBest;
	private boolean isFstIterationOfGBest;
	private int topologyType;
	private boolean isDebug;
	private boolean useConstriction;
	
	private boolean useDecayFactor;
	private double decayFactor = 0.0;
	
	private int indexRun;
	
	public PSO (IChallenge challenge, int topologyType, boolean isDebug, int indexRun){
		this.challenge = challenge;
		this.setSwarm(new Particle[challenge.getNumberOfParticles()]);
		setFstIterationOfGBest(true);
		this.topologyType = topologyType;
		this.isDebug = isDebug;
		this.useConstriction = challenge.useConstriction();
		this.useDecayFactor = challenge.useDecayFactor();
		this.indexRun = indexRun;
	}
	
	public void init() throws IOException{
		
		int qtyOfParticles = challenge.getNumberOfParticles();
		int numDimensions  = challenge.getNumberOfDimensions();
		double minPosition = challenge.getMinPosition();
		double maxPosition = challenge.getMaxPosition();
		
		if(isDebug){
			writer = new BufferedWriter(new FileWriter(Constants.PATH_FILE, true));
		}
		
		for (int i = 0; i < qtyOfParticles; i++) {
			Particle particle = new Particle(numDimensions, minPosition, maxPosition);

			particle.setSpeed(Util.setInitState(numDimensions, minPosition, maxPosition));
			
			double[] initialPosition = Util.getRandomPosition(minPosition, maxPosition, numDimensions);
			particle.setCurrentPosition(Arrays.copyOf(initialPosition, numDimensions));
			particle.setPBest(Arrays.copyOf(initialPosition, numDimensions));
			
			swarm[i] = particle;
		}
	}
	
	public void start() throws IOException{
		int numberOfIterations = challenge.getNumberOfIterations();
		
		if(useDecayFactor){
			calculateDecayFactor();
		}
		
		for (int i = 0; i < numberOfIterations; i++) {
			run(i);
		}
		
		if(isDebug){
			if(writer != null){
				writer.flush();
				writer.close();
			}
			
			System.out.println(indexRun + " - GBest Final["+ getTopologyName(topologyType) +"]: " + challenge.getFitness(gBest));
		}
	}
	
	private void calculateDecayFactor() {
		decayFactor = challenge.getDecayFactor();
	}

	private void run(int iteration) throws IOException {
		
		int qtyOfParticles = challenge.getNumberOfParticles();
		int numIterations = challenge.getNumberOfIterations();
		
		// verify bests positions
		for(int i = 0; i < qtyOfParticles; i++){
			Particle particle = swarm[i];
			updatePBest(particle);
			updateGBest(particle);
		}
		
		if(isDebug){
			printDebug(iteration, numIterations);
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
			
			if(useDecayFactor){
				challenge.setInertialWeight(w - decayFactor);
			}
			
		}
	}

	private void printDebug(int iteration, int numIterations) throws IOException {
		
		writer.flush();
		
		if(iteration == 0){
			writer.write("vector("+indexRun+", :) = [");
		}
		
		writer.write(challenge.getFitness(gBest) + "");
		
		if(iteration == numIterations - 1){
			writer.write("];");
			writer.newLine();
		} else {
			writer.write(", ");
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
