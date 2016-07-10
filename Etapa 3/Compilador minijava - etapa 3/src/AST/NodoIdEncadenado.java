package AST;

import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Var;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;
import exceptions.SemanticException;
import minijava.Token;

public class NodoIdEncadenado {
	private Token t;
	private NodoIdEncadenado nodoSiguiente;

	public NodoIdEncadenado(Token t) {
		this.t = t;
		nodoSiguiente = null;
	}

	public void setEncadenado(NodoIdEncadenado n2) {
		if (n2 != null)
			nodoSiguiente = n2;

	}

	public NodoIdEncadenado getEncadenado() {
		return nodoSiguiente;
	}

	public Token getToken() {
		return t;
	}

	public Tipo check(Metodo metodo, Clase c) throws SemanticException {

		if (c.getAtributosInstancia().containsKey(t.getLexema())) { // A.B.C-> B
																	// ESTA
																	// DEFINIDO
																	// EN A
			Tipo a = c.getAtributosInstancia().get(t.getLexema()).getTipo(); // OBTENGO
																				// EL
																				// TIPO
																				// DE
																				// T
			if (nodoSiguiente != null) {
				if (a.esTipoClase()) {
					Clase csig = TS.ts().getClass(((TipoClase) a).getToken());
					return nodoSiguiente.check(metodo, csig);
				} else
					throw new SemanticException(t.getLine(),
							"Se realizan llamadas concatenadas incorrectamente utilizando un tipo "
									+ a.toString());
			} else
				
				return a;

		} else
			throw new SemanticException(t.getLine(), "El tipo "
					+ c.getClassID() + "no contiene un identificador"
					+ t.getLexema() + ".");
	}
}