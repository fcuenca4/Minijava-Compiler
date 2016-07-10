package AST;

import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import exceptions.SemanticException;

public class NodoBloque extends NodoSentencia{
	private Bloque b;

	public NodoBloque(Bloque b) {
		this.b=b;
	}

	public boolean check(Metodo metodoActual) throws SemanticException {
		return b.check(metodoActual);
	}

	public int getLine() {
		return b.getLine();
	}



}