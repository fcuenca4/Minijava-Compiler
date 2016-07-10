package minijava;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author Francisco Cuenca, Brenda Dilschneider
 * 
 */
public class Principal {
	
	public static void main(String args[]) {
		try {
			if (args.length <= 1) {
				System.out.println("Se esperaban mas argumentos.");
				System.out.println("Use: input [output]");
				return;
			}
			if (args.length > 2) {
				System.out.println("Demasiados argumentos.");
				System.out.println("Use: input.minijava [output]");
				return;
			}

			File f = new File(".//");

			
			GCI.path = f.getCanonicalPath() + System.getProperty("file.separator") + args[1];
			
			GCI.gen();

			LexicalAnalyzer a = new LexicalAnalyzer(args[0]);
			SyntacticalAnalyzer s = new SyntacticalAnalyzer(a);
			s.analyze();
			a.close();
			GCI.gen().close();

		} catch (IOException e) {
			System.out.println("El archivo de entrada es invalido.");
			System.out.println("Use: input [output]");
		}

	}
}

