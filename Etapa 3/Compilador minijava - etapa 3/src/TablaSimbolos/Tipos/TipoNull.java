package TablaSimbolos.Tipos;

import minijava.Token;
import exceptions.SemanticException;

public class TipoNull extends TipoClase{
	private static TipoNull instance;
	public static TipoNull instance(){
		if(instance == null)
			return new TipoNull();
		
		return instance;
	}
	private TipoNull(){
		super(null);
	}

	
	@Override
	public String getID() {
		return "";
	}

	@Override
	public Token getToken() {
		return null;
	}


	//Decision de diseño: Asumimos que no conforma con los Strings.
	public boolean conforma(Tipo c) throws SemanticException{
		if (c instanceof TipoClase)
			return true;
		
		return false;
	}
	public String toString(){
		return "null";
	}

	@Override
	public void gen(Token k) {
	}


	@Override
	public boolean equals(Tipo tipo) {
		return tipo instanceof TipoNull;
	}
	
}
