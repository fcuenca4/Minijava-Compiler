package AST;

import minijava.GCI;
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
		String l1 = GCI.gen().label();
		String l2 = GCI.gen().label();
		
		GCI.gen().openCommentD("Inicia bloque FOR.");
		a1.check(metodo);
		GCI.gen().gen(l1,"NOP","");
		Tipo tipoExp = e.check(metodo);
		if (!(tipoExp instanceof TipoBool))
			throw new SemanticException(e.getLine(), "El tipo de la expresion debe ser boolean.");

		GCI.gen().gen("BF " + l2,"");
		boolean toRet = s.check(metodo);
		a2.check(metodo);
		GCI.gen().gen("JUMP " + l1,"");
		GCI.gen().gen(l2 ,"NOP","");
		GCI.gen().closeCommentD("Fin bloque FOR.");
		
		return toRet;
	}

	@Override
	public int getLine() {
		return e.getLine();
	}

}

