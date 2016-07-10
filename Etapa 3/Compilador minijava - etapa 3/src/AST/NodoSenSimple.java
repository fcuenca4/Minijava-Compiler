package AST;

import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoVoid;
import exceptions.SemanticException;

public class NodoSenSimple extends NodoSentencia{

	private NodoExpresion e;

	public NodoSenSimple(NodoExpresion e) {
		this.e = e;
	}

	public boolean check(Metodo metodo) throws SemanticException {
		Tipo tipo = e.check(metodo);	
		
		return false;
	}

	public int getLine() {
		return e.getLine();
	}
}
