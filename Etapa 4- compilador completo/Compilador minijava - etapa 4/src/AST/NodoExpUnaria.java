package AST;

import minijava.GCI;
import minijava.Token;
import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoBool;
import TablaSimbolos.Tipos.TipoInt;
import exceptions.SemanticException;

public class NodoExpUnaria extends NodoExpresion {

	private NodoExpresion eIzq;
	private Token operador;

	public NodoExpUnaria(NodoExpresion eIzq, Token operador) {
		this.eIzq = eIzq;
		this.operador = operador;
	}

	@Override
	public Tipo check(Metodo metodo) throws SemanticException {
		Tipo tipoIzq = eIzq.check(metodo);
		switch (operador.getLexema()) {
			case "-":
			case "+":
				if (tipoIzq instanceof TipoInt) {
					switch (operador.getLexema()) {
						case "-":
							GCI.gen().gen("NEG","");
					}
					return TipoInt.instance();
				}

				throw new SemanticException(operador.getLine() , " El tipo de la expresion debe ser int.");
			case "!":
				if (tipoIzq instanceof TipoBool) {
					GCI.gen().gen("NOT","");
					return TipoBool.instance();
				}

				throw new SemanticException(operador.getLine() , " El tipo de la expresion debe ser boolean.");

		}
		return null;

	}

	@Override
	public int getLine() {
		return eIzq.getLine();
	}

}

