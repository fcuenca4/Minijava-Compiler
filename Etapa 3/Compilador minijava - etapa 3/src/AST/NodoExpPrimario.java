package AST;

import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import exceptions.SemanticException;

public abstract class NodoExpPrimario extends NodoExpresion{
	public abstract Tipo check(Metodo metodo) throws SemanticException;

}

