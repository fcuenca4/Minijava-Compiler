package AST;

import minijava.GCI;
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
		String l1 = GCI.gen().label();
		String l2 = GCI.gen().label();

		GCI.gen().openCommentD("Inicia bloque IF-THEN-ELSE");
		Tipo tipoExp = e.check(metodo);
		if (!(tipoExp instanceof TipoBool))
			throw new SemanticException(e.getLine(),"El tipo de la expresion debe ser boolean.");

		GCI.gen().gen("BF " + l1,"");
		boolean hayReturnIf = sIf.check(metodo);
		boolean hayReturnElse = false;

		if (sElse != null) {
			GCI.gen().gen("JUMP " + l2,"");
			GCI.gen().gen(l1,"NOP","");
			hayReturnElse = sElse.check(metodo);
			GCI.gen().gen(l2,"NOP","");
		} else
			GCI.gen().gen(l1,"NOP","");

		GCI.gen().closeCommentD("Fin bloque IF-THEN-ELSE");
		return hayReturnIf && hayReturnElse;
	}

	@Override
	public int getLine() {
		return e.getLine();
	}

}

