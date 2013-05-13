package br.bhmy.main;

import java.io.IOException;

import br.bhmy.challenges.IChallenge;
import br.bhmy.challenges.functions.GriewankFunction;
import br.bhmy.challenges.functions.RastriginFunction;
import br.bhmy.challenges.functions.RosenbrockFunction;
import br.bhmy.challenges.functions.SchwefelFunction;
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
		runIterations(Constants.GLOBAL);
		System.out.println();
		runIterations(Constants.LOCAL);
		System.out.println();
		runIterations(Constants.FOCAL);
		System.out.println();
	}
	
	public static void runIterations(int topology) throws IOException{
		for(int i = 0; i < numberOfIterations; i++){
//			StartPSO.startChallenge(new SchwefelFunction(), topology, i);
//			StartPSO.startChallenge(new RosenbrockFunction(), topology, i);
//			StartPSO.startChallenge(new RastriginFunction(), topology, i);
			StartPSO.startChallenge(new GriewankFunction(), topology, i);
//			StartPSO.startChallenge(new SphereFunction(), topology, i);
		}
	}
	
	public static void startChallenge(IChallenge challenge, int topology, int indexRun) throws IOException{
		PSO pso = new PSO(challenge, topology, true, indexRun + 1);
		pso.init();
		pso.start();
	}
	
}
