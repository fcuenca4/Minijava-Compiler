package AST;

import java.util.LinkedList;
import java.util.List;

import minijava.Token;
import TablaSimbolos.Metodo;
import exceptions.SemanticException;

public class Bloque {

	private List<NodoSentencia> sentencias;
	Token k;

	public Bloque(Token k) {
		sentencias = new LinkedList<NodoSentencia>();
		this.k=k;
	}
	
	public boolean check(Metodo metodo) throws SemanticException {
		boolean hayReturn = false;

		for (NodoSentencia s : sentencias) {
			// como habia un return, entonces esta sentencia y las siguientes
			// son inalcanzables.!!
			if (hayReturn)
				throw new SemanticException(s.getLine(),"Codigo inalcanzable.");

			hayReturn = s.check(metodo);
		}

		return hayReturn;
	}

	public void addSent(NodoSentencia sent) {
		this.sentencias.add(sent);

	}
	
	public int getLine(){
		return k.getLine();
	}


}