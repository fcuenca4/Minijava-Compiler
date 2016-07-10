package TablaSimbolos;

import TablaSimbolos.Tipos.Tipo;

import minijava.Token;



public class Argumento extends Var implements Comparable<Argumento>{
	private int indice;
	public Argumento(Token k,Tipo tipo, int indice){
		super(k,tipo);
		this.indice=indice;
	}

	public int getIndice(){
		return indice;
	}
	
	public boolean equals(Argumento a){
		// SIEMPRE TIENEN EL MISMO ID
		return this.getIndice() == a.getIndice() &&
			this.getTipo().toString().equals(a.getTipo().toString());
	}

	@Override
	public int compareTo(Argumento a) {
		return getIndice() < a.getIndice()? -1 : (getIndice()>a.getIndice()? 1:0);
	}
	
}
