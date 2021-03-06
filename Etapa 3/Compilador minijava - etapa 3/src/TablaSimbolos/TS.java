package TablaSimbolos;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;


import minijava.Token;
import exceptions.SemanticException;

public class TS {

	private static TS ts;

	public static TS ts() {
		if (ts == null) {
			ts = new TS();
			ts.init();
		}

		return ts;
	}

	private Hashtable<String, Clase> clases;
	private List<Clase> clasesMain;

	private TS() {
		clases = new Hashtable<String, Clase>();
		clasesMain = new LinkedList<Clase>();
		// inicializar Object y System.
	}

	private void init() {
		try {
			Clase claseObject = addClass(new Token("id", 0, "Object"));
			claseObject.setConstructor(claseObject.getToken());

			CSystem s = new CSystem();
			s.init();

		} catch (SemanticException e) {
		}
	}

	public boolean containsClass(String id) {
		return clases.containsKey(id);
	}

	public Clase getClass(Token k) throws SemanticException {
		if (clases.containsKey(k.getLexema()))
			return clases.get(k.getLexema());
		throw new SemanticException(k.getLine(), "La clase " + k.getLexema() + " no ha sido declarada.");
	}

	public boolean isMainDeclared() {
		return clasesMain.size() > 0;
	}

	public void mainHasBeenDeclared(Clase clase) {
		this.clasesMain.add(clase);
	}

	public Clase addClass(Token k) throws SemanticException {
		if (clases.containsKey(k.getLexema()))
			throw new SemanticException(k.getLine(), "La clase " + k.getLexema() + " ya ha sido declarada anteriormente.");
		Clase c = new Clase(k);
		clases.put(k.getLexema(), c);

		return c;
	}

	public void controlDeclaracion() throws SemanticException {
		for (Clase c : clases.values()) {
			c.checkDeclarations();
		}
		if (!this.isMainDeclared())
			throw new SemanticException(0, "No se ha declarado un metodo main.");

		if (clasesMain.size() > 1) {
			System.out.println("<Warning>: Se han encontrado multiples declaraciones del metodo main.");
			System.out.println("\tLa clases que contienen metodo main son: ");
			for (Clase c : clasesMain) {
				System.out.println("\t\t> " + c.getClassID());
			}
			System.out.println("\tSe ejecutara el metodo main de la clase: " + clasesMain.get(0).getClassID());
			System.out.println();

		}
		System.out.println("El control de declaraciones ha finalizado con exito.");
	}

	public void controlSentencias() throws SemanticException {
		for (Clase c : clases.values()) {
			c.checkSentencias();
		}
		System.out.println("El control de sentencias ha finalizado con exito.");
	}

	
}