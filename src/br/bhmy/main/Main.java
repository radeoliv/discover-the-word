package br.bhmy.main;

import java.io.IOException;

import br.bhmy.challenges.IChallenge;
import br.bhmy.challenges.functions.SphereFunction;
import br.bhmy.constants.Constants;
import br.bhmy.mecanics.pso.PSO;

public class Main {
	public static void main(String[] args) throws IOException {
		StartPSO.runForIterations();
	}
}

class StartPSO{

	public static final int numberOfIterations = 30;
	
	public static void runForIterations () throws IOException{
		for(int i = 0; i < numberOfIterations; i++){
			StartPSO.startChallenge(new SphereFunction(), i + 1);
		}
	}

	public static void startChallenge(IChallenge challenge, int indexRun) throws IOException{
		PSO pso = new PSO(challenge, Constants.GLOBAL, true, indexRun);
		pso.init();
		pso.start();
	}
	
}
