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
	
	public Particle(int numDimensions){
		this.numDimensions = numDimensions;
		pBest = new double[numDimensions];
		speed = new double[numDimensions];
		currentPosition = new double[numDimensions];
	}
	
	public void updateParticle(double c1, double c2, double[] gBest){
		
		double r1 = Util.generateRandom();
		double r2 = Util.generateRandom();
		
		double c1r1 = Constants.C1 * r1;
		double c2r2 = Constants.C2 * r2;
		
		for (int i = 0; i < numDimensions; i++){
			
			// new speed
			speed[i] = Constants.W * speed[i]
					+ c1r1 * (pBest[i] - currentPosition[i])
					+ c2r2 * (gBest[i] - currentPosition[i]);
			
			// threshold
			double threshold = Constants.MAX_POSITION / 4.0d; 
			if (speed[i] > threshold){
				speed[i] =  threshold;
			} else if (speed[i] < -threshold){
				speed[i] =  -threshold;
			}
			
			// update position
			currentPosition[i] += speed[i];
			
			// max current position
			if(currentPosition[i] > Constants.MAX_POSITION){
				currentPosition[i] = Constants.MAX_POSITION;
				speed[i] = -speed[i];
			} else if (currentPosition[i] < -Constants.MAX_POSITION){
				currentPosition[i] = -Constants.MAX_POSITION;
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

}
