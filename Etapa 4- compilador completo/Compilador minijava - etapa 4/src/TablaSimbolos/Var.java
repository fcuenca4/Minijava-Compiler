package TablaSimbolos;

import minijava.Token;
import TablaSimbolos.Tipos.Tipo;

public abstract class Var {
	protected Tipo tipo;
	protected Token k;
	protected int offset;

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

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}