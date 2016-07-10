package TablaSimbolos.Tipos;

import TablaSimbolos.Clase;
import TablaSimbolos.TS;

import exceptions.SemanticException;
import minijava.Token;



public class TipoClase extends Tipo{
	private Token k;
	public TipoClase(Token k){
		this.k=k;
	}

	public String getID(){
		return this.k.getLexema();
	}

	public Token getToken(){
		return k;
	}
	public boolean conforma(Tipo c) throws SemanticException{
		if (c instanceof TipoNull)
			return true;
		
		
		if (c instanceof TipoClase){
			Clase clase = TS.ts().getClass(((TipoClase) c).getToken());
		
			Clase thisClass = TS.ts().getClass(k);
			return thisClass.esAncestro(clase.getClassID());
		}
		return false;
	}
	public String toString(){
		return "Clase<" + k.getLexema()+">";
	}

	@Override
	public void gen(Token k) {
		
	}

	@Override
	public boolean equals(Tipo tipo) {
		if (tipo instanceof TipoClase)
			return ((TipoClase)tipo).getToken().getLexema().equals(k.getLexema());
		
		return false;
	}

	@Override
	public boolean esTipoClase() {
		// TODO Auto-generated method stub
		return true;
	}

}
