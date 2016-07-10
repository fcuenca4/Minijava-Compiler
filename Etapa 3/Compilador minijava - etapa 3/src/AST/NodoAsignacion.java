package AST;

import minijava.Token;
import TablaSimbolos.Metodo;
import TablaSimbolos.Var;
import TablaSimbolos.Tipos.Tipo;
import exceptions.SemanticException;

public class NodoAsignacion extends NodoSentencia {
	private NodoId n;
	private NodoExpresion e;

	public NodoAsignacion(NodoId nodo, NodoExpresion e) {
		this.n = nodo;
		this.e = e;
	}

	@Override
	public boolean check(Metodo metodoLlamador) throws SemanticException {
		Tipo tipoExp =e.check(metodoLlamador);
		n.check(metodoLlamador);
		Token k=n.getToken();
		

		if (!tipoExp.conforma(n.getV()))
			throw new SemanticException(k.getLine(),"El tipo " + tipoExp.toString() + " no conforma con el tipo "
					+ n.getV().toString() + ".");
		return false;
		}

	@Override
	public int getLine() {
		return n.getToken().getLine();
	}

}