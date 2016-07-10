package AST;

import minijava.Token;
import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoBool;
import TablaSimbolos.Tipos.TipoInt;
import exceptions.SemanticException;

public class NodoExpresionBinaria extends NodoExpresion {
	private NodoExpresion eIzq;
	private NodoExpresion eDer;
	private Token operador;

	public NodoExpresionBinaria(NodoExpresion eIzq, NodoExpresion eDer, Token operador) {
		this.eIzq = eIzq;
		this.eDer = eDer;
		this.operador = operador;
	}

	public Tipo check(Metodo metodo) throws SemanticException {
		Tipo tipoIzq = eIzq.check(metodo);
		Tipo tipoDer = eDer.check(metodo);

		switch (operador.getLexema()) {
			case "+":
			case "-":
			case "*":
			case "/":
			case "%":
				if (tipoIzq instanceof TipoInt && tipoDer instanceof TipoInt) {
					switch (operador.getLexema()) {
						case "+":
							
							break;
						case "-":
							
							break;
						case "*":
							
							break;
						case "/":
							
							break;
						case "%":
							
							break;
					}
					return TipoInt.instance();

				}

				throw new SemanticException(operador.getLine(), "El tipo " + tipoDer.toString() + " no conforma con el tipo "
						+ tipoIzq.toString() + ".");
			case "&&":
			case "||":
				if (tipoIzq instanceof TipoBool && tipoDer instanceof TipoBool) {
					switch (operador.getLexema()) {
						case "&&":
							
							break;
						case "||":
							
							break;
					}
					return TipoBool.instance();
				}

				throw new SemanticException(operador.getLine(), "El tipo " + tipoDer.toString() + " no conforma con el tipo "
						+ tipoIzq.toString() + ".");
			case ">":
			case "<":
			case ">=":
			case "<=":
				if (tipoIzq instanceof TipoInt && tipoDer instanceof TipoInt) {
					switch (operador.getLexema()) {
						case ">":
							
							break;
						case "<":
							
							break;
						case ">=":
							
							break;
						case "<=":
							
							break;
					}
					return TipoBool.instance();
				}

				throw new SemanticException(operador.getLine(), "El tipo " + tipoDer.toString() + " no conforma con el tipo "
						+ tipoIzq.toString() + ".");
			case "==":
			case "!=":
				if (tipoIzq.conforma(tipoDer) || tipoDer.conforma(tipoIzq)) {
					switch (operador.getLexema()) {
						case "==":
							
							break;
						case "!=":
							
							break;
					}
					return TipoBool.instance();
				}
				throw new SemanticException(operador.getLine(), "El tipo " + tipoDer.toString() + " no conforma con el tipo "
						+ tipoIzq.toString() + ".");
		}
		return null;

	}

	@Override
	public int getLine() {
		return eIzq.getLine();
	}

}