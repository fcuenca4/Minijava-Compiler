package AST;

import minijava.Token;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoVoid;
import exceptions.SemanticException;

public class NodoReturn extends NodoSentencia {

	private NodoExpresion e;
	private Token k;

	public NodoReturn(Token k) {
		this.e = null;
		this.k = k;
	}

	public NodoReturn(Token k, NodoExpresion e) {
		this.e = e;
		this.k = k;
	}

	public boolean check(Metodo metodoLlamador) throws SemanticException {
		if (e != null) { // return algo;
			Tipo tipoExp = e.check(metodoLlamador);

			// si algo no conforma con el tipo de la declaracion del metodo
			// entonces hay error.
			if (!tipoExp.conforma(metodoLlamador.getRetorno()))
				throw new SemanticException(e.getLine(), "El tipo " + tipoExp.toString() + " no conforma con el tipo "
						+ metodoLlamador.getRetorno().toString() + ".");

			// ret_val = cantidad de argumentos + 1 (Puntero retorno) + 1
			// (enlace dinamico) + 1 (para llegar al retorno)

			int ret_val = metodoLlamador.getArgsFormales().size() + 3;

			if (metodoLlamador.isDynamic())
				ret_val++; // para pasar el this.

		} else
		// return;
		// si es una funcion hay error porque se debe retornar algo!
		if (!(metodoLlamador.getRetorno() instanceof TipoVoid))
			throw new SemanticException(k.getLine(), "Se debe retornar un resultado de tipo " + metodoLlamador.getRetorno().toString()
					+ ".");

		return true;
	}

	public int getLine() {
		return k.getLine();
	}

}
