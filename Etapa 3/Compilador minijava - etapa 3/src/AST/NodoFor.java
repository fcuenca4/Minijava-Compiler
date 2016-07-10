package AST;

import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoBool;
import exceptions.SemanticException;

public class NodoFor extends NodoSentencia{
	private NodoExpresion e;
	private NodoSentencia s;
	private NodoAsignacion a1,a2;

	public NodoFor(NodoAsignacion a1, NodoExpresion e,
			NodoAsignacion a2, NodoSentencia s) {
		this.e=e;
		this.s=s;
		this.a1=a1;
		this.a2=a2;
	}

	public boolean check(Metodo metodo) throws SemanticException {
				
		a1.check(metodo);
		Tipo tipoExp = e.check(metodo);
		if (!(tipoExp instanceof TipoBool))
			throw new SemanticException(e.getLine(), "El tipo de la expresion debe ser boolean.");

		
		boolean toRet = s.check(metodo);
		a2.check(metodo);
		return toRet;
	}

	@Override
	public int getLine() {
		return e.getLine();
	}

}

