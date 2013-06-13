package util;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ArquivoUtil {

	
	public static double[][] carregarArquivoNaMatriz(BancoDeDados banco)
	{
		String name = null;
		int qtCities = 0;
		switch (banco) {

		case ATT48:
			name = Constantes.NAME_ATT48_TXT;
			qtCities = 48;
			break;
		case OLIVER30:
			name = Constantes.NAME_OLIVER30_TXT;
			qtCities = 30;
			break;
		case CUBO:
			name = Constantes.CUBO_TXT;
			qtCities = 4;
			break;
		case EIL51:
			name = Constantes.NAME_EIL51_TXT;
			qtCities = 51;
			break;
		default:
			System.out.println("ERRO!!");
			break;
		}
		
		File file = new File(name);
		
		double[][] matrix = new double[qtCities][qtCities];
		
		try{
			
			Scanner scan = new Scanner(file);
			int index;
			double cost;
			
			for (int i = 0; i < qtCities; i++) {
				
				for (int j = 0; j < qtCities; j++) {
					
					if(i != j){
					
						index = scan.nextInt();
						cost = scan.nextDouble();
					}
					else
					{
						index = i;
						cost = 0;						
					}
					
					matrix[i][index] = cost;
					
				}			
			}
			scan.close();
			
		}catch(IOException e)
		{
			e.printStackTrace();			
		}
		
		return matrix;
		
	}
	
	/*
	public static double[][] loadFileByCoord(Database database)
	{
		
		String name = null;
		int qtCities = 0;
		switch(database)
		{
		
		case OLIVER30: 
			name = SimulationConstants.NAME_OLIVER30_COORD;
			qtCities = 30;
		break;
		
		}
		
		double[][] matrix = new double[qtCities][qtCities];
		
		File file = new File(SimulationConstants.PATH + name);
		try {
			Scanner scan = new Scanner(file);
			
			double[][] coords = new double[qtCities][2];
			
			for (int i = 0; i < qtCities; i++) {
				
				coords[i][0] = scan.nextDouble();
				coords[i][1] = scan.nextDouble();				
				
			}
			scan.close();
			
			for (int i = 0; i < qtCities; i++) {
				
				for (int j = 0; j < qtCities; j++) {
					
					if(i != j)
					{
						matrix[i][j] = dist(coords[i][0], coords[i][1], coords[j][0], coords[j][1]);
					}
					else
					{
						matrix[i][j] = 0;						
					}
					
					
				}
				
			}
			
			file = new File(SimulationConstants.PATH + SimulationConstants.NAME_OLIVER30);
			
			PrintWriter writer = new PrintWriter(file);
		
			for (int i = 0; i < qtCities; i++) {
				for (int j = 0; j < qtCities; j++) {
					
					if(i != j)
					{
						writer.println(j+"\t"+ matrix[i][j]);						
					}					
					
				}
			}
			
			writer.flush();
			writer.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return matrix;	
		
	}
	*/
	
	public static double dist(double coord1X, double coord1Y, double coord2X, double coord2Y)
	{		
		return Math.sqrt(Math.pow(coord1X - coord2X,2) + Math.pow(coord1Y - coord2Y,2));		
	}
	
	
	
}
