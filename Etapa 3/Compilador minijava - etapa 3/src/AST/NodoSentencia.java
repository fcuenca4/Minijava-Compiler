package AST;

import TablaSimbolos.Metodo;
import exceptions.SemanticException;

public abstract class NodoSentencia{
	public abstract boolean check(Metodo metodo) throws SemanticException;
	public abstract int getLine();
}