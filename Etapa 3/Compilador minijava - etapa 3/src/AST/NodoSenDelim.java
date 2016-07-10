package AST;

import minijava.Token;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import exceptions.SemanticException;

public class NodoSenDelim extends NodoSentencia{
	
	Token k;
	public NodoSenDelim(Token k){
		this.k=k;
	}
	public boolean check(Metodo metodo) throws SemanticException {
		return true;
	}
	
	public int getLine() {
		return k.getLine();
	}

	
}

