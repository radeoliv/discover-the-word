package textoutil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Escritor {
	
	private BufferedWriter escritor;
	private String path;
	
	public Escritor(){
		
	}
	
	public void inicie() throws IOException{
		escritor = new BufferedWriter(new FileWriter(this.path, true));
	}
	
	public void escreva(String text) throws IOException{
		escritor.flush();
		escritor.write(text);
	}
	
	public void pularLinha() throws IOException{
		escritor.flush();
		escritor.newLine();
	}
	
	public void fechar() throws IOException{
		escritor.flush();
		escritor.close();
	}

	public String getPath() {
		return path;
	}
	
	public void atualizeCaminhoDoArquivo(String path) {
		this.path = path;
	}
}	
