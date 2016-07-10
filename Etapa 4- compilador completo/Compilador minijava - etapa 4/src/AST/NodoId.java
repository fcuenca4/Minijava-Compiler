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

public class NodoId extends NodoSentencia {
	private Token t;
	private NodoIdEncadenado siguiente;

	public NodoId(Token t) {
		this.t = t;
		siguiente = null;

	}

	public void setEncadenado(NodoIdEncadenado nodoEncadenado2) {
		if (nodoEncadenado2 != null)
			siguiente = nodoEncadenado2;

	}

	public Token getToken() {
		return t;
	}

	public NodoIdEncadenado getEncadenado() {
		return siguiente;
	}

	public Tipo chequear(Metodo metodo) throws SemanticException {
		Clase actual = metodo.getClaseDeclaracion();
		Tipo toRet = null;
		Tipo a;
		Var va = null;

		if (siguiente != null) {

			if (metodo.getVarsLocales().containsKey(t.getLexema())) { // ES UNA
																		// VARLOCAL

				a = metodo.getVarsLocales().get(t.getLexema()).getTipo();

				va = metodo.getVarsLocales().get(t.getLexema());

				GCI.gen().gen("LOAD " + va.getOffset(),
						"Cargo la variable local <" + va.getID() + ">");
				
				Clase c = TS.ts().getClass(((TipoClase) a).getToken());
				
				return siguiente.check(metodo,c );
			} else {
				a = actual.getAtributosInstancia().get(t.getLexema()).getTipo(); // ES

				va = actual.getAtributosInstancia().get(t.getLexema()); // VARINST

				if (a.esTipoClase()) {// SI HAGO UNA LLAMADA ENCADENADA A.B.C ,
										// Y A
										// ES TIPO INT.. ES INCORRECTO
					Clase c = TS.ts().getClass(((TipoClase) a).getToken());

					GCI.gen().gen(
							"LOAD 3",
							"Apila la referencia a THIS el cual apunta a un objeto de la clase <"
									+ metodo.getClaseDeclaracion().getClassID()
									+ ">");

					GCI.gen().gen(
							"LOADREF " + va.getOffset(),
							"Almacena el tope de la pila en la variable de instancia <"
									+ va.getID() + ">");
					return siguiente.check(metodo, c);

				} else
					throw new SemanticException(t.getLine(),
							"Se realizan llamadas concatenadas incorrectamente utilizando un tipo "
									+ a.toString());
			}

		} else {

			// si k.lex hace referencia a una variable local
			if (metodo.getVarsLocales().containsKey(t.getLexema())) {

				va = metodo.getVarsLocales().get(t.getLexema());

				GCI.gen().gen(
						"STORE " + va.getOffset(),
						"Almacena el tope de la pila en la variable local <"
								+ va.getID() + ">");
				return metodo.getVarsLocales().get(t.getLexema()).getTipo();

			}
			// si k.lex hace referencia a un argumento
			else if (metodo.getArgsFormales().containsKey(t.getLexema())) {

				
				va = metodo.getArgsFormales().get(t.getLexema());
				GCI.gen().gen(
						"STORE " + va.getOffset(),
						"Almacena el tope de la pila en el argumento <"
								+ va.getID() + ">");

				return metodo.getArgsFormales().get(t.getLexema()).getTipo();
			}

			// si k.lex hace referencia a un atributo de instancia.
			else if (metodo.getClaseDeclaracion().getAtributosInstancia()
					.containsKey(t.getLexema())) {

				va = metodo.getClaseDeclaracion().getAtributosInstancia()
						.get(t.getLexema());
				GCI.gen().gen(
						"LOAD 3",
						"Apila la referencia a THIS el cual apunta a un objeto de la clase <"
								+ metodo.getClaseDeclaracion().getClassID()
								+ ">");
				GCI.gen()
						.gen("SWAP",
								"Invierte los argumentos, es necesario para ejecutar STOREREF");
				GCI.gen().gen(
						"STOREREF " + va.getOffset(),
						"Almacena el tope de la pila en la variable de instancia <"
								+ va.getID() + ">");

				return metodo.getClaseDeclaracion().getAtributosInstancia()
						.get(t.getLexema()).getTipo();

			} else
				throw new SemanticException(t.getLine(), "El identificador "
						+ t.getLexema()
						+ " no se encuentra declarado en la clase "
						+ metodo.getClaseDeclaracion().getClassID() + ".");

		}

	}

	public boolean check(Metodo metodo) throws SemanticException {

		return false;
	}

	@Override
	public int getLine() {
		return t.getLine();
	}

}
