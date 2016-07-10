package AST;

import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Var;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;
import exceptions.SemanticException;
import minijava.GCI;
import minijava.Token;

public class NodoIdEncadenado {
	private Token t;
	private NodoIdEncadenado nodoSiguiente;
	private Tipo a;

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
			a = c.getAtributosInstancia().get(t.getLexema()).getTipo(); // OBTENGO
			
																			// EL
			
			Var va = c.getAtributosInstancia().get(t.getLexema());			// TIPO
																				// DE
																				// T
			if (nodoSiguiente != null) {
				if (a.esTipoClase()) {
					
					
					GCI.gen().gen("LOADREF " + va.getOffset(),"Almacena el tope de la pila en la variable de instancia <"+va.getID()+">");
					Clase csig = TS.ts().getClass(((TipoClase) a).getToken());
					
					
					
					return nodoSiguiente.check(metodo, csig);
				} else
					throw new SemanticException(t.getLine(),
							"Se realizan llamadas concatenadas incorrectamente utilizando un tipo "
									+ a.toString());
			} else{
				
				
				GCI.gen().gen("SWAP","Invierte los argumentos, es necesario para ejecutar STOREREF");
				GCI.gen().gen("STOREREF " + va.getOffset(),"Almacena el tope de la pila en la variable de instancia <"+va.getID()+">");
				
				
				
				return a;}

		} else
			throw new SemanticException(t.getLine(), "El tipo "
					+ c.getClassID() + "no contiene un identificador"
					+ t.getLexema() + ".");
	}
	
}