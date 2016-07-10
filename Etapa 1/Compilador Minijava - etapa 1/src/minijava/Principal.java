package minijava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import exceptions.LexicalError;

/**
 * 
 * @author Francisco Cuenca, Brenda Dilschneider
 * 
 */
public class Principal {
	public static void main(String args[]) {
		try {
			if (args.length < 1) {
				System.out.println("Se esperaban argumentos.");
				System.out.println("Use: input [output]");
				return;
			}
			if (args.length > 2) {
				System.out.println("Demasiados argumentos.");
				System.out.println("Use: input.minijava [output]");
				return;
			}

			File f = new File(".//");
			try{
			LexicalAnalyzer a = new LexicalAnalyzer(args[0]);
			Token t= a.getToken();
			
			if (args.length == 2) {
				// Me pasan el archivo de salida.
				
				try{
				String path = f.getCanonicalPath() + System.getProperty("file.separator") + args[1];
				FileWriter w = new FileWriter(path);
				BufferedWriter bw = new BufferedWriter(w);
				PrintWriter wr = new PrintWriter(bw); 
				wr.write("Nro de linea	Token 		Lexema");
				wr.println();
				while(t.getName()!="EOF"){
					wr.println("	" +t.getLine()+"	" + t.getName() +"		" + t.getLexema());
					t=a.getToken();
				}
				wr.println("Compilacion exitosa");
				wr.close();
				bw.close();
				System.out.println("Compilacion impresa en el archivo de salida...");
				}catch(IOException e){System.out.println("Error en el archivo de salida");};
			
			} else {
				
				// no pasan el output, imprimo en pantalla
				System.out.println("Nro de linea	Token 		Lexema");
				while(t.getName()!="EOF"){
					System.out.println("	" +t.getLine()+"	" + t.getName() +"		" + t.getLexema());
					t=a.getToken();
				}
				
				System.out.println("Compilacion exitosa.");
			}

			
							
				a.close();
			} catch (LexicalError e) {
				
				System.out.println(e.getMessage());
				System.out.println("Error lexico: compilacion terminada");
			}
			
			

				
			
		} catch (IOException e) {
			System.out.println("El archivo de entrada es invalido.");
			System.out.println("Use: input [output]");
		}

	}
	
}
