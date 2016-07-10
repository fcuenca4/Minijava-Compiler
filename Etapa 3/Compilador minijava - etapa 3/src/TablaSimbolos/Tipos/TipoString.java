package TablaSimbolos.Tipos;


import minijava.Token;

public class TipoString extends TipoPrimitivo {
	private static TipoString instance;
	public static TipoString instance(){
		if(instance == null)
			return new TipoString();
		
		return instance;
	}
	private TipoString() {
	}

	public boolean conforma(Tipo c) {
		return c instanceof TipoString;
	}

	public String toString() {
		return "String";
	}

	@Override
	public void gen(Token k) {
		
	}

	@Override
	public boolean equals(Tipo tipo) {
		return tipo instanceof TipoString;
	}
	@Override
	public boolean esTipoClase() {
		// TODO Auto-generated method stub
		return false;
	}

}
