package br.bhmy.util;

import java.util.Random;

/**
 * @author Bruno Yamashita 
 */

public class Util {
	
	/**
	 * Generates a random value [0.0, 1.0]
	 * @return random value
	 */
	public static final double generateRandom(){
		return new Random().nextDouble();
	}

	/**
	 * Generates a random value with range [minValue, maxValue]
	 * @return random value
	 */
	public static final double generateRandom(double minValue, double maxValue){
		double value = new Random().nextDouble();
		value = ((maxValue - minValue) * value) + minValue;
		return value;
	}
	
	/**
	 * Gets a random position particle. This method can be used to start particles.
	 * @param minValue
	 * @param maxValue
	 * @param numDimensions
	 * @return double[] random position
	 */
	public static final double[] getRandomPosition(double minValue, double maxValue, int numDimensions){
		double[] randomPos = new double[numDimensions];
		for (int i = 0; i < numDimensions; i++) {
			randomPos[i] = generateRandom(minValue, maxValue);
		}
		return randomPos;
	}
	
	/**
	 * Sets 0 values an array 
	 * @param numDimensions
	 * @return [0, 0, 0, ... 0]
	 */
	public static final double[] setInitState(int numDimensions){
		double[] d = new double[numDimensions];
		for (int i = 0; i < d.length; i++) {
			d[i] = 0;
		}
		return d;
	}
}
