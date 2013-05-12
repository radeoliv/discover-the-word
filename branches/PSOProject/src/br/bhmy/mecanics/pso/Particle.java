package br.bhmy.mecanics.pso;

import br.bhmy.constants.Constants;
import br.bhmy.util.Util;

/**
 * @author Bruno Yamashita
 */

public class Particle {
	
	private int numDimensions;
	private double[] pBest;
	private double[] speed;
	private double[] currentPosition;
	private double fitnessPBest;
	private double minPosition;
	private double maxPosition;
	
	public Particle(int numDimensions, double minPosition, double maxPosition){
		this.numDimensions = numDimensions;
		pBest = new double[numDimensions];
		speed = new double[numDimensions];
		currentPosition = new double[numDimensions];
		this.minPosition = minPosition;
		this.maxPosition = maxPosition;
	}
	
	public void updateParticle(double w, double c1, double c2, double[] gBest, boolean useConstriction){

		double[] r1 = new double[numDimensions];
		double[] r2 = new double[numDimensions];

		for (int i = 0; i < numDimensions; i++){
			r1[i] = Util.generateRandom();
			r2[i] = Util.generateRandom();
		}
		
		for (int i = 0; i < numDimensions; i++){
			
			double c1r1 = Constants.C1 * r1[i];
			double c2r2 = Constants.C2 * r2[i];

			// new speed
			if(useConstriction){
				speed[i] = Constants.CONSTRICTION_COEFFICIENT * (speed[i]
						+ (c1r1 * (pBest[i] - currentPosition[i]))
						+ (c2r2 * (gBest[i] - currentPosition[i])));
			} else {
				speed[i] = (w * speed[i])
						+ (c1r1 * (pBest[i] - currentPosition[i]))
						+ (c2r2 * (gBest[i] - currentPosition[i]));
			}
			
			if (speed[i] > maxPosition / 4.0){
				speed[i] =  maxPosition / 4.0;
			} else if (speed[i] < minPosition / 4.0){
				speed[i] =  minPosition / 4.0;
			}
			
			// update position
			currentPosition[i] += speed[i];
			
			// max current position
			if(currentPosition[i] > maxPosition){
				currentPosition[i] = maxPosition;
				speed[i] = -speed[i];
			} else if (currentPosition[i] < minPosition){
				currentPosition[i] = minPosition;
				speed[i] = -speed[i];
			}
		}
	}
	
	public int getNumDimensions() {
		return numDimensions;
	}

	public double[] getPBest() {
		return pBest;
	}

	public void setPBest(double[] bestPosition) {
		this.pBest = bestPosition;
	}

	public double[] getSpeed() {
		return speed;
	}

	public void setSpeed (double[] speed) {
		this.speed = speed;
	}

	public double[] getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(double[] currentPosition) {
		this.currentPosition = currentPosition;
	}

	public double getFitnessPBest() {
		return fitnessPBest;
	}

	public void setFitnessPBest(double fitnessPBest) {
		this.fitnessPBest = fitnessPBest;
	}

}
