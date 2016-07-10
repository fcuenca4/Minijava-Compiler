package AST;

import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoBool;
import exceptions.SemanticException;

public class NodoIf extends NodoSentencia {
	private NodoExpresion e;
	private NodoSentencia sIf;
	private NodoSentencia sElse;

	public NodoIf(NodoExpresion e, NodoSentencia ifTrue) {
		this.e = e;
		this.sIf = ifTrue;

	}

	public NodoIf(NodoExpresion e, NodoSentencia ifTrue, NodoSentencia ifFalse) {
		this(e, ifTrue);
		this.sElse = ifFalse;
	}

	public boolean check(Metodo metodo) throws SemanticException {
		
		Tipo tipoExp = e.check(metodo);
		if (!(tipoExp instanceof TipoBool))
			throw new SemanticException(e.getLine(),"El tipo de la expresion debe ser boolean.");

		boolean hayReturnIf = sIf.check(metodo);
		boolean hayReturnElse = false;

		if (sElse != null) {
			hayReturnElse = sElse.check(metodo);
			
		} 

		
		return hayReturnIf && hayReturnElse;
	}

	@Override
	public int getLine() {
		return e.getLine();
	}

}

