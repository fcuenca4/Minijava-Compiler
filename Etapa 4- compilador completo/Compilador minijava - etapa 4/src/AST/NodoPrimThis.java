package AST;

import minijava.GCI;
import minijava.Token;
import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import exceptions.SemanticException;

public class NodoPrimThis extends NodoExpPrimario{
	private Token k;
	public NodoPrimThis(Token k){
		this.k=k;
	}
	public Tipo check(Metodo metodo) throws SemanticException{
		if (metodo.isStatic())
			throw new SemanticException(k.getLine(),"No es posible hacer referencia a this en un metodo estatico.");

		GCI.gen().gen("LOAD 3", "Apila el puntero a THIS de la clase <"+metodo.getClaseDeclaracion().getClassID()+">");
		return metodo.getClaseDeclaracion().getConstructor().getRetorno();
	}

	@Override
	public int getLine() {
		return k.getLine();
	}

}