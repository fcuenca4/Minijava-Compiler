package AST;

import minijava.Token;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import exceptions.SemanticException;

public class NodoAsignacion extends NodoSentencia {
	private NodoId n;
	private NodoExpresion e;

	public NodoAsignacion(NodoId nodo, NodoExpresion e) {
		this.n = nodo;
		this.e = e;
	}

	@Override
	public boolean check(Metodo metodoLlamador) throws SemanticException {
		Tipo tipoExp =e.check(metodoLlamador);
		
		Tipo tipoEncadenadoIzq= n.chequear(metodoLlamador);
		
	
		Token k=n.getToken();
		
			if (!tipoExp.conforma(tipoEncadenadoIzq)){	
			throw new SemanticException(k.getLine(),"El tipo " + tipoExp.toString() + " no conforma con el tipo "
					+tipoEncadenadoIzq.toString() + ".");}
					
		return false;
		}

	@Override
	public int getLine() {
		return n.getToken().getLine();
	}

}