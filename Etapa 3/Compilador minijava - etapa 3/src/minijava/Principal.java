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
				System.out.println("a source file was expected.");
				return;
			}

			LexicalAnalyzer a = new LexicalAnalyzer(args[0]);
			SyntacticalAnalyzer s= new SyntacticalAnalyzer(a);
			s.analyze();
			a.close();

		} catch (IOException e) {
			System.out.println("Cannot open the source file.");
		}

	}
	
}

