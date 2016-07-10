package AST;

import minijava.Token;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import exceptions.SemanticException;

public class NodoPrimLiteral extends NodoExpPrimario{
	private Token k;
	private Tipo tipo;
	public NodoPrimLiteral(Token k, Tipo tipo) {
		this.k=k;
		this.tipo = tipo;
	}

	public Tipo check(Metodo metodo) throws SemanticException{
		tipo.gen(k);
		return tipo;
	}

	@Override
	public int getLine() {
		return k.getLine();
	}
}