package TablaSimbolos;

import minijava.Token;
import TablaSimbolos.Tipos.Tipo;

public abstract class Var {
	protected Tipo tipo;
	protected Token k;


	protected Var(Token k, Tipo tipo) {
		this.k = k;
		this.tipo = tipo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public String getID() {
		return k.getLexema();
	}


}