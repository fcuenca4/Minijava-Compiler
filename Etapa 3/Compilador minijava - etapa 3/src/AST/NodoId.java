package AST;

import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Var;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;
import exceptions.SemanticException;
import minijava.Token;

public class NodoId extends NodoSentencia {
	private Token t;
	private NodoIdEncadenado siguiente;
	private Tipo v;

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

	@Override
	public boolean check(Metodo metodo) throws SemanticException {
		Clase actual = metodo.getClaseDeclaracion();
		Tipo a;
		
		if (siguiente != null) {

			if (metodo.getVarsLocales().containsKey(t.getLexema())) { // ES UNA
																		// VARLOCAL

				a = metodo.getVarsLocales().get(t.getLexema()).getTipo();

			} else
				a = actual.getAtributosInstancia().get(t.getLexema()).getTipo(); // ES
																					// VARINST

			if (a.esTipoClase()) {// SI HAGO UNA LLAMADA ENCADENADA A.B.C , Y A
									// ES TIPO INT.. ES INCORRECTO
				Clase c = TS.ts().getClass(((TipoClase) a).getToken());
				v = siguiente.check(metodo, c);

			} else
				throw new SemanticException(t.getLine(),
						"Se realizan llamadas concatenadas incorrectamente utilizando un tipo "
								+ a.toString());

		} else {
			Tipo v;
			// si k.lex hace referencia a una variable local
			if (metodo.getVarsLocales().containsKey(t.getLexema())) {
				v = metodo.getVarsLocales().get(t.getLexema()).getTipo();

			}
			// si k.lex hace referencia a un argumento
			else if (metodo.getArgsFormales().containsKey(t.getLexema())) {
				v = metodo.getArgsFormales().get(t.getLexema()).getTipo();

			}

			// si k.lex hace referencia a un atributo de instancia.
			else if (metodo.getClaseDeclaracion().getAtributosInstancia()
					.containsKey(t.getLexema())) {
				v = metodo.getClaseDeclaracion().getAtributosInstancia()
						.get(t.getLexema()).getTipo();

			} else
				throw new SemanticException(t.getLine(), "El identificador "
						+ t.getLexema()
						+ " no se encuentra declarado en la clase "
						+ metodo.getClaseDeclaracion().getClassID() + ".");
			this.v = v;

		}
		
		return true;
	}

	@Override
	public int getLine() {
		return t.getLine();
	}

	public Tipo getV() {
		return v;
	}

}
