package TablaSimbolos;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;

import minijava.GCI;
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
	private int codes;

	private TS() {
		codes = 0;
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
		Clase c = new Clase("class" + codes, k);
		codes ++;
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
		/* Protocolo de inicializacion */
		Date d = new Date();
		
		GCI.gen().comment("# Codigo genenerado por el compilador minijava");
		GCI.gen().comment("# Generado: " + d.toString());
		GCI.gen().comment("# Autores:\tFrancisco Cuenca");
		GCI.gen().comment("# \t\t\tBrenda Dilschneider");
		GCI.gen().comment("# Compiladores e Interpretes 2014");
		GCI.gen().comment("# DCIC UNS - Argentina");
		GCI.gen().ln();
		GCI.gen().ln();
		
		GCI.gen().comment("<<<<< Codigo de inicializacion de la maquina virtual >>>>");
		GCI.gen().ln();
		GCI.gen().code();
		GCI.gen().gen("PUSH lheap_init","");
		GCI.gen().gen("CALL","");
		GCI.gen().gen("PUSH "+clasesMain.get(0).getMetodos().get("main").getCode(),"");
		GCI.gen().gen("CALL","");
		GCI.gen().gen("HALT","");
		GCI.gen().ln();

		GCI.gen().gen("lmalloc", "LOADFP", "Inicializacion unidad");
		GCI.gen().gen("LOADSP","");
		GCI.gen().gen("STOREFP","Finaliza inicializacion del RA");
		GCI.gen().gen("LOADHL","hl");
		GCI.gen().gen("DUP","hl");
		GCI.gen().gen("PUSH 1","1");
		GCI.gen().gen("ADD","hl + 1");
		GCI.gen().gen("STORE 4","Guarda resultado (puntero a base del bloque)");
		GCI.gen().gen("LOAD 3","Carga cantidad de celdas a alojar (parametro)");
		GCI.gen().gen("ADD","");
		GCI.gen().gen("STOREHL","Mueve el heap limit (hl)");
		GCI.gen().gen("STOREFP","");
		GCI.gen().gen("RET 1","Retorna eliminando el parametro");
		GCI.gen().ln();

		GCI.gen().gen("lheap_init","RET 0","Inicializacion simplicada del :heap");
		GCI.gen().ln();
		GCI.gen().ln();
		GCI.gen().comment("<<<< Inicio de generacion de codigo del progama fuente. >>>>");
		for (Clase c : clases.values()) {
			c.checkSentencias();
		}
		
		System.out.println("El control de sentencias ha finalizado con exito.");
	}

	
}