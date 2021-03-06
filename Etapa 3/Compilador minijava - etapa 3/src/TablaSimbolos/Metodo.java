package TablaSimbolos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;


import minijava.Token;
import AST.Bloque;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;
import exceptions.SemanticException;

public class Metodo {

	// Token del id del metodo.
	private Token k;

	// Clase que declaro este metodo.
	private Clase claseDeclaraion;

	// Variables Locales
	private Hashtable<String, Variable> varsLocales;

	// Argumentos formales
	private Hashtable<String, Argumento> argsFormales;

	// Tipo del retorno de este metodo.
	private Tipo retorno;

	// Modificador del metodo
	private String modificador;

	// Bloque del metodo.
	private Bloque bloque;

	// Flags para saber si el metodo fue chequeado o no.
	private boolean sentenciasChequeadas, declaracionChequeada;

	/**
	 * Constructor para un metodo.
	 * 
	 * @param mod
	 *            Modificador de este metodo
	 * @param k
	 *            Token correspondiente con el ID
	 * @param retorno
	 *            Tipo de retorno
	 * @param claseDeclaracion
	 *            Clase que declaro este metodo.
	 */
	public Metodo(String mod, Token k, Tipo retorno, Clase claseDeclaracion) {
		this.k = k;
		this.retorno = retorno;
		this.modificador = mod;
		this.varsLocales = new Hashtable<String, Variable>();
		this.argsFormales = new Hashtable<String, Argumento>();
		this.bloque = new Bloque(k);
		this.claseDeclaraion = claseDeclaracion;
		this.sentenciasChequeadas = false;
		this.declaracionChequeada = false;
	}

	/**
	 * Realiza un chequedo de la declaracion del metodo.
	 * 
	 * @throws SemanticException
	 */
	public void checkDeclaracion() throws SemanticException {
		if (declaracionChequeada)
			return;

		declaracionChequeada = true;

		// Si retorna un tipo clase, entonces dicha clase debe estar declarada.
		Tipo ret = getRetorno();
		if (ret instanceof TipoClase) {
			if (!TS.ts().containsClass(((TipoClase) ret).getID()))
				throw new SemanticException(((TipoClase) ret).getToken().getLine(), "La clase " + ((TipoClase) ret).getID()
						+ " no ha sido declarada.");
		}

		// Verifica que para los argumentos de tipo clase, la misma este
		// declarada.
		for (Argumento a : getArgsFormales().values()) {
			if (a.getTipo() instanceof TipoClase) {
				if (!TS.ts().containsClass(((TipoClase) a.getTipo()).getID()))
					throw new SemanticException(((TipoClase) a.getTipo()).getToken().getLine(), "La clase "
							+ ((TipoClase) a.getTipo()).getID() + " no ha sido declarada.");
			}
		}

		// Verifica que para las variables locales de tipo clase, la misma este
		// declarada.
		for (Variable a : getVarsLocales().values()) {
			if (a.getTipo() instanceof TipoClase) {
				if (!TS.ts().containsClass(((TipoClase) a.getTipo()).getID()))
					throw new SemanticException(((TipoClase) a.getTipo()).getToken().getLine(), "La clase "
							+ ((TipoClase) a.getTipo()).getID() + " no ha sido declarada.");
			}
		}
	}

	/***
	 * Chequea las sentencias correspondientes al bloque de este metodo
	 * 
	 * @return true si el retorno del bloque es correcto; false, en otro caso.
	 * @throws SemanticException
	 */
	public boolean checkSentencias() throws SemanticException {
	return getBloque().check(this);
		
	}

	/**
	 * Agrega una variable local.
	 * 
	 * @param k
	 * @param tipo
	 * @throws SemanticException
	 */
	public void addVarLocal(Token k, Tipo tipo) throws SemanticException {
		if (varsLocales.containsKey(k.getLexema()) || argsFormales.containsKey(k.getLexema()))
			throw new SemanticException(k.getLine(), "El identificador de la variable " + k.getLexema()
					+ " fue declarado previamente en la clase " + claseDeclaraion.getClassID() + " .");
		Variable v = new Variable(k, tipo);
		varsLocales.put(k.getLexema(), v);
	}

	/**
	 * Agrega un un argumento formal
	 * 
	 * @param k
	 * @param tipo
	 * @param indice
	 * @throws SemanticException
	 */
	public void addArgFormal(Token k, Tipo tipo, int indice) throws SemanticException {
		if (varsLocales.containsKey(k.getLexema()) || argsFormales.containsKey(k.getLexema()))
			throw new SemanticException(k.getLine(), "El identificador del argumento formal " + k.getLexema()
					+ " fue declarado previamente en la clase " + claseDeclaraion.getClassID() + ".");
		Argumento a = new Argumento(k, tipo, indice);
		argsFormales.put(k.getLexema(), a);
	}

	public boolean isDynamic() {
		return this.getModificador().equals("dynamic");
	}

	public boolean isStatic() {
		return this.getModificador().equals("static");
	}



	/**
	 * Establece el bloque.
	 * 
	 * @param bloque
	 */
	public void setBloque(Bloque bloque) {
		this.bloque = bloque;
	}



	/**
	 * Obtiene el modificador
	 * 
	 * @return
	 */
	public String getModificador() {
		return modificador;
	}

	/**
	 * Obtiene los argumentos formales.
	 * 
	 * @return
	 */
	public Hashtable<String, Argumento> getArgsFormales() {
		return argsFormales;
	}

	/**
	 * Obtiene el tipo de retorno.
	 * 
	 * @return
	 */
	public Tipo getRetorno() {
		return retorno;
	}

	/**
	 * Obtiene las variables locales.
	 * 
	 * @return
	 */
	public Hashtable<String, Variable> getVarsLocales() {
		return varsLocales;
	}

	/**
	 * Obtiene el ID del metodo.
	 * 
	 * @return
	 */
	public String getID() {
		return k.getLexema();
	}

	/**
	 * Obtiene el bloque.
	 * 
	 * @return
	 */
	public Bloque getBloque() {
		return this.bloque;
	}

	/**
	 * Obtiene la clase que declaro este metodo.
	 * 
	 * @return
	 */
	public Clase getClaseDeclaracion() {
		return claseDeclaraion;
	}

	/**
	 * Retorna el token asociado al ID.
	 * 
	 * @return el token asociado al ID.
	 */
	public Token getToken() {
		return k;
	}


	/**
	 * Retorna si la declaracion de este metodo ha sido chequeada
	 * 
	 * @return true si la declaracion de este metodo ha sido chequeada; false,
	 *         en otro caso.
	 */
	public boolean isDeclaracionChequeada() {
		return declaracionChequeada;
	}

	/**
	 * Retorna si las sentencias de este metodo han sido chequeadas.
	 * 
	 * @return true si las sentencias de este metodo han sido chequeadas; false,
	 *         en otro caso.
	 */
	public boolean isSentenciasChequeadas() {
		return sentenciasChequeadas;
	}

	/**
	 * Obtiene una lista de argumentos formales ordenados de menor a mayor segun
	 * los indices.
	 * 
	 * @return
	 */
	public List<Argumento> getArgsFormalesL() {
			List<Argumento> list = new ArrayList<Argumento>(this.argsFormales.values());
			Collections.sort(list);
			return list;

	}

	/**
	 * Retorna si la declaracion de este metodo, es igual a la declaracion del
	 * metodo pasado por parametro.
	 * 
	 * @param m
	 * @return true si la declaracion de este metodo, es igual a la declaracion
	 *         del metodo pasado por parametro.
	 * @throws SemanticException
	 */
	public boolean equalsDeclaration(Metodo m) throws SemanticException {
		// Si los ID's son distintos o,
		// Si los modificadores son distintos o,
		// Si los retornos no son del mismo tipo o,
		// Si la cantidad de argumentos formales es distinta, entonces retorna
		// false
		// Sino, comprueba que los argumentos formales coincidan.

		if (!m.getID().equals(this.getID()) || !m.getModificador().equals(this.getModificador())
				|| !m.getRetorno().equals(this.getRetorno()) || this.getArgsFormales().size() != m.getArgsFormales().size())
			return false;

		List<Argumento> argsThis = getArgsFormalesL();
		List<Argumento> argsM = m.getArgsFormalesL();
		Argumento aThis, aM;
		for (int i = 0; i < argsThis.size(); i++) {
			aThis = argsThis.get(i);
			aM = argsM.get(i);
			if (!aThis.getTipo().equals(aM.getTipo()))
				return false;
		}

		return true;
	}




}