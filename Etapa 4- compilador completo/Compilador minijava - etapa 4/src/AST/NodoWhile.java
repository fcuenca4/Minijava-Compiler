package AST;

import minijava.GCI;
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
		String l1 = GCI.gen().label();
		String l2 = GCI.gen().label();

		GCI.gen().openCommentD("Inicio bloque WHILE");
		GCI.gen().gen(l1,"NOP","");

		Tipo tipoExp = e.check(metodo);
		if (!(tipoExp instanceof TipoBool))
			throw new SemanticException(e.getLine() , "Se esperaba que la expresion sea de tipo boolean y es de tipo "
					+ tipoExp.toString() + ".");

		GCI.gen().gen("BF " + l2,"");

		boolean toRet = s.check(metodo);

		GCI.gen().gen("JUMP " + l1,"");
		GCI.gen().gen(l2,"NOP","");

		GCI.gen().closeCommentD("Fin bloque WHILE");
		return toRet;
	}

	@Override
	public int getLine() {
		return e.getLine();
	}

}


