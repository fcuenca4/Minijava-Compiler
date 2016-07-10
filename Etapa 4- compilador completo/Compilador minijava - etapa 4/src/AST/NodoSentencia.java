package AST;

import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import exceptions.SemanticException;

public abstract class NodoSentencia{
	public abstract boolean check(Metodo metodoActual) throws SemanticException;
	public abstract int getLine();
}