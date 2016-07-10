package AST;

import exceptions.SemanticException;
import minijava.GCI;
import minijava.Token;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Var;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;

public class NodoIDEncadenadoDer extends Encadenado{
	
	private Token k;
	private Encadenado siguiente;
	
	
	public NodoIDEncadenadoDer(Token ka, Encadenado enc){
		k=ka;
		siguiente=enc;
	}

	@Override
	public Tipo chequear(Clase c,Clase claseActual,String tipo_llamada, Metodo metodo,Metodo metodoActual,boolean flag) throws SemanticException {
		
		
		//Tengo que verificar que el metodo este en la clase c.
		
		Var v = null;
		
		Clase receptor = null;
		// el id es un atributo de instancia.
		
		if ( c.getAtributosInstancia().containsKey(k.getLexema())) { //metodo.isDynamic()) &&
				v = c.getAtributosInstancia().get(k.getLexema());
				
				
				GCI.gen().gen("LOADREF " + v.getOffset(),"Apilo el contenido de la variable de instancia <"+v.getID()+">");
				
				
		}
		else
			throw new SemanticException(k.getLine(), "No se encuentra una referencia a identificador " + k.getLexema());
				
			
			
			// como no hay llamadas posteriores, eso significa que no hay llamadas y
			// es el ultimo Id de las llamadas.
			if (siguiente == null) {
				// como el id hace referencia a una variable local ,argumento, o
				// de instancia entoces retorna el tipo.
				
				return v.getTipo();
			}
			else{ //Hay siguiente 
				
			}

			// hay llamadas posteriores, entonces las resuelvo.

			// Como la primer llamada es dinamica, entonces el tipo de v debe
			// ser una clase.
			if (v.getTipo() instanceof TipoClase) {
				receptor = TS.ts().getClass(((TipoClase) v.getTipo()).getToken());
				return siguiente.chequear(receptor,claseActual, tipo_llamada, metodo, metodoActual,flag);
			}
			else  throw new SemanticException(k.getLine(),"Error semantico: La variable "+k.getLexema()+ " deberia ser de tipo clase y es de tipo "+v.getTipo().toString()+".");
		
		
		
	}

	@Override
	public void setEncadenado(Encadenado enc) {
		siguiente=enc;
		
	}

	@Override
	public Encadenado getEncadenado() {
		return siguiente;
	}

}
