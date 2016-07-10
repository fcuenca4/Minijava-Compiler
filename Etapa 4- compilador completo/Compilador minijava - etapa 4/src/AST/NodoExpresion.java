package AST;

import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import exceptions.SemanticException;

public abstract class NodoExpresion{
	public abstract Tipo check(Metodo metodo) throws SemanticException;
	public abstract int getLine();
}
