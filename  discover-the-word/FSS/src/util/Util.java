package util;

import java.util.Arrays;
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
		double value = generateRandom();
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

	public static final int generateRandom(int maxValue){
		return new Random().nextInt(maxValue);
	}
	
	/**
	 * Sets 0 values an array 
	 * @param numDimensions
	 * @return [0, 0, 0, ... 0]
	 */
	public static final double[] vetorZero(int numDimensions){
		double[] d = new double[numDimensions];
		for (int i = 0; i < d.length; i++) {
			d[i] = 0.0;
		}
		return d;
	}
	
	public static final double[] multVetorPorEscalar(double[] vetor, double valor){
		double[] novoVetor = Arrays.copyOf(vetor, vetor.length);
		for (int i = 0; i < vetor.length; i++) {
			novoVetor[i] *= valor;
		}
		return novoVetor;
	}
	
	public static final double[] somaDeDoisVetores(double[] v1, double[] v2){
		double[] novoVetor = Arrays.copyOf(v1, v1.length);
		for (int i = 0; i < novoVetor.length; i++) {
			novoVetor[i] += v2[i];
		}
		return novoVetor;
	}

	public static final double[] subDeDoisVetores(double[] v1, double[] v2){
		double[] novoVetor = Arrays.copyOf(v1, v1.length);
		for (int i = 0; i < novoVetor.length; i++) {
			novoVetor[i] -= v2[i];
		}
		return novoVetor;
	}
	
	public static final double distEuclidiana(double[] v1, double[] v2){
		int tam = v1.length;
		double sum = 0.0;
		for (int i = 0; i < tam; i++) {
			double subtracao = v1[i] - v2[i];
			subtracao = Math.pow(subtracao, 2);
			sum += subtracao;
		}
		return Math.pow(sum, 1.0/(double)tam);
	}
	
	public static final double[] setInitState(int numDimensions, double minPosition, double maxPosition){
		double[] d = new double[numDimensions];
		for (int i = 0; i < d.length; i++) {
			d[i] = generateRandom(minPosition/4.0, maxPosition/4.0);
		}
		return d;
	}
}
