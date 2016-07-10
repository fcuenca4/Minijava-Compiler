package AST;

import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoBool;
import exceptions.SemanticException;

public class NodoWhile extends NodoSentencia {
	private NodoExpresion e;
	private NodoSentencia s;

	public NodoWhile(NodoExpresion e, NodoSentencia s) {
		this.e = e;
		this.s = s;
	}

	public boolean check(Metodo metodo) throws SemanticException {
		
		Tipo tipoExp = e.check(metodo);
		if (!(tipoExp instanceof TipoBool))
			throw new SemanticException(e.getLine() , "Se esperaba que la expresion sea de tipo boolean y es de tipo "
					+ tipoExp.toString() + ".");

		boolean toRet = s.check(metodo);

		return toRet;
	}

	@Override
	public int getLine() {
		return e.getLine();
	}

}

