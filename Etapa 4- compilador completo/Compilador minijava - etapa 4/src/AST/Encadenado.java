package AST;

import exceptions.SemanticException;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;

public abstract class Encadenado {
	
	
	public abstract Tipo chequear(Clase claseCalificadora,Clase claseActual,String tipo_llamada,Metodo metodoCalificador,Metodo metodoActual,boolean flag) throws SemanticException;
	//clase calificadora y clase actual , metodo calificador y metodo actual
	public abstract void setEncadenado(Encadenado enc);
	public abstract Encadenado getEncadenado();

}
