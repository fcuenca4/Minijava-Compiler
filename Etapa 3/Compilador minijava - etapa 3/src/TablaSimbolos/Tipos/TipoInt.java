package TablaSimbolos.Tipos;


import minijava.Token;

public class TipoInt extends TipoPrimitivo{
	private static TipoInt instance;
	public static TipoInt instance(){
		if(instance == null)
			return new TipoInt();
		
		return instance;
	}
	
	private TipoInt(){}
	public boolean conforma(Tipo c){
		return c instanceof TipoInt;
	}
	public String toString(){
		return "int";
	}
	@Override
	public void gen(Token k) {
	}
	@Override
	public boolean equals(Tipo tipo) {
		return tipo instanceof TipoInt;
	}

	@Override
	public boolean esTipoClase() {
		// TODO Auto-generated method stub
		return false;
	}
}
