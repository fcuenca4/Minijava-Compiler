package TablaSimbolos;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import exceptions.SemanticException;
import minijava.Token;

import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;
import TablaSimbolos.Tipos.TipoVoid;

public class Clase {

	// Token del ID de la clase.
	private Token k;

	// Token de la clase ancestro.
	private Token ancestro;

	// Atributos de instancias.
	private Hashtable<String, Variable> atributosInstancia;

	// Metodos
	private Hashtable<String, Metodo> metodos;

	// Constructor.
	private Metodo constructor;

	// Flag para saber si ya se ha resuelto la herencia de esta clase.
	private boolean herenciaResuelta;

	/**
	 * Constructor para una clase.
	 * 
	 * @param k
	 *            Token que corresponde con el ID de la clase.
	 */
	public Clase(Token k) {
		this.k = k;
		atributosInstancia = new Hashtable<String, Variable>();
		metodos = new Hashtable<String, Metodo>();
	}

	/**
	 * Realiza el control de declaraciones.
	 * 
	 * @throws SemanticException
	 */
	public void checkDeclarations() throws SemanticException {
		// la clase object esta perfecta
		if (this.getClassID().equals("Object"))
			return;

		// Checkeo las declaraciones de variables de instancia
		for (Variable v : atributosInstancia.values()) {
			if (v.getTipo() instanceof TipoClase)
				TS.ts().getClass(((TipoClase) v.getTipo()).getToken());

		}

		// Controlo que no halla herencia circular y ademas controla que las
		// clases implicadas en la herencia esten declaradas.
		if (hayHerenciaCircular(this.getClassID()))
			throw new SemanticException(k.getLine(),
					"Hay herencia circular en la clase " + this.getClassID());

		// Resuelvo la herencia de los metodos
		resolverHerencia();

		// Realizo el chequeo de declaracion de los metodos
		for (Metodo m : getMetodos().values())
			m.checkDeclaracion();

		// si esta clase tiene un metodo main() entonces le indica al TS que
		// esta clase tiene un metodo main.
		if (getMetodos().containsKey("main")
				&& getMetodos().get("main").getArgsFormales().size() == 0)
			TS.ts().mainHasBeenDeclared(this);

	}

	/**
	 * Realiza el control de sentencias.
	 * 
	 * @throws SemanticException
	 */
	public void checkSentencias() throws SemanticException {
		boolean hayReturn = constructor.checkSentencias(); // Chequea la
															// correctitud del
															// constructor
		if (hayReturn)
			throw new SemanticException(k.getLine(),
					"Un constructor no puede contener una sentencia return.");

		// check del cuerpo de los metodos.
		for (Metodo m : getMetodosL()) {
			if (!m.isSentenciasChequeadas()) {
				hayReturn = m.checkSentencias();
				// si m es una funcion y no hay un return entonces es un error.
				if (!hayReturn && !(m.getRetorno() instanceof TipoVoid))
					throw new SemanticException(m.getToken().getLine(),
							"Falta sentencia return en el metodo " + m.getID()
									+ ".");

			}
		}
	}

	/**
	 * Retorna una lista con los metodos 
	 * 
	 * @return
	 */
	public List<Metodo> getMetodosL() {
		List<Metodo> listMetodos = new ArrayList<Metodo>(getMetodos().values());
		return listMetodos;
	}

	public Token getToken() {
		return k;
	}

	public Metodo getConstructor() {
		return this.constructor;
	}

	/**
	 * Retorna si esta clase es algun ancestro de la clase h
	 * 
	 * @param h
	 *            id de una clase
	 * @return true si esta clase es algun ancestro de la clase h; false en otro
	 *         caso
	 * @throws SemanticException
	 */
	public boolean esAncestro(String h) throws SemanticException {
		if (this.getClassID().equals("Object"))
			return false;
		if (this.getClassID().equals(h))
			return true;
		Clase claseAncestro = TS.ts().getClass(this.ancestro);
		return claseAncestro.esAncestro(h);
	}

	/**
	 * Retorna si esta clase esta involucrada en una herencia circular. Esto es
	 * si alguna clase ancestro a esta clase hereda de esta clase.
	 * 
	 * @param id
	 * @return
	 * @throws SemanticException
	 */
	public boolean hayHerenciaCircular(String id) throws SemanticException {
		if (this.ancestro.getLexema().equals(id))
			return true;
		if (this.ancestro.getLexema().equals("Object"))
			return false;
		Clase superClase = TS.ts().getClass(this.ancestro);
		return superClase.hayHerenciaCircular(id);
	}

	/**
	 * Hereda todos los metodos dinamicos de las clases ancestro. Ademas en la
	 * misma pasada resuelve la herencia de todas las clases involucradas en la
	 * misma linea de herencia. Si este metodo ya ha sido invocado anteriormente
	 * para esta clase entonces no vuelve a invocarse, para eso usa la variable
	 * "completo" como flag, para saber si ya se ha invocado este metodo
	 * anteriormente para esta clase.
	 * 
	 * Y retorna finalmente todos los metodos.
	 * 
	 * @return
	 * @throws SemanticException
	 */
	public void resolverHerencia() throws SemanticException {
		if (this.herenciaResuelta) // es para ejecutarlo una sola vez
			return;

		// Si la clase ancestro es Object entonces corto y retorno los metodos
		// de esta clase.
		if (this.getClassID().equals("Object")) {
			return;
		}

		// Obtengo la clase ancestro. Como se supone que no hay herencia
		// circular entonces sabemos que existe.
		Clase superClase = TS.ts().getClass(this.ancestro);

		// obtengo todos los metodos de la clase ancestro. LLamada recursiva, lo
		// cual hace que la clase ancestro primero resuelva su linea de
		// herencia.
		superClase.resolverHerencia();

		Hashtable<String, Metodo> metodosAncestro = superClase.getMetodos();

		// Ahora incorporamos los metodos dinamicos de la clase ancestro a esta
		// clase. Resolviendo asi la herecia de metodos.

		// por cada metodo m de la clase ancestro
		for (Metodo m : metodosAncestro.values()) {
			// Solo nos interesan los metodos dinamicos.
			if (m.isDynamic()) {
				/*
				 * Si m existe en esta clase entonces debo controlar que la
				 * declaracion sea la misma. sino (es decir, m no existe en esta
				 * clase) entonces agrego m a esta clase ya que lo hereda del
				 * padre.
				 */
				if (this.metodos.containsKey(m.getID())) {
					Metodo m2 = this.metodos.get(m.getID());
					if (!m2.equalsDeclaration(m))
						throw new SemanticException(
								m2.getToken().getLine(),
								"El metodo "
										+ m.getID()
										+ " de la clase "
										+ m.getClaseDeclaracion().getClassID()
										+ " no coincide con la declaracion del metodo "
										+ m2.getID() + " de la clase "
										+ m2.getClaseDeclaracion().getClassID());
				} else
					this.metodos.put(m.getID(), m);

			}
		}
		// esto hace que no vuelva a ejecutar el metodo, ya que esta completo.!
		this.herenciaResuelta = true;
	}

	

	public Hashtable<String, Metodo> getMetodos() {
		return metodos;
	}

	public Metodo addMetodo(String mod, Token k, Tipo retorno)
			throws SemanticException {
		if (metodos.containsKey(k.getLexema())
				|| getClassID().equals(k.getLexema())
				|| atributosInstancia.containsKey(k.getLexema()))
			throw new SemanticException(k.getLine(), "El metodo "
					+ k.getLexema() + " es invalido.");
		Metodo m = new Metodo(mod, k, retorno, this);

		metodos.put(k.getLexema(), m);
		return m;
	}

	public void addAtributoInst(Token k, Tipo tipo) throws SemanticException {
		if (metodos.containsKey(k.getLexema())
				|| getClassID().equals(k.getLexema())
				|| atributosInstancia.containsKey(k.getLexema()))
			throw new SemanticException(k.getLine(), "El atributo "
					+ k.getLexema() + " es invalido.");
		Variable v = new Variable(k, tipo);
		atributosInstancia.put(k.getLexema(), v);
	}

	public Metodo setConstructor(Token k) throws SemanticException {
		if (!getClassID().equals(k.getLexema()) || constructor != null)
			throw new SemanticException(k.getLine(), "El constructor "
					+ k.getLexema() + " es invalido.");
		constructor = new Metodo("dynamic", k, new TipoClase(k), this);

		return constructor;
	}

	public void setAncestro(Token k) throws SemanticException {
		if (getClassID().equals(k.getLexema()) || ancestro != null)
			throw new SemanticException(k.getLine(), "El ancestro "
					+ k.getLexema() + " es invalido.");
		ancestro = k;
	}

	public String getClassID() {
		return k.getLexema();
	}

	public Hashtable<String, Variable> getAtributosInstancia() {
		return atributosInstancia;
	}

}
