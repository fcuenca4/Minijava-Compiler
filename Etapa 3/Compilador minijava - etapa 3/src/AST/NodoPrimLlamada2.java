package AST;

import java.util.List;

import minijava.Token;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Var;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;
import exceptions.SemanticException;

public class NodoPrimLlamada2 extends NodoExpPrimario {

	private Token k;
	private ListaLlamadas listaLlamadas;

	public NodoPrimLlamada2(Token k, List<NodoLlamada> llamadas) {
		this.k = k;
		this.listaLlamadas = new ListaLlamadas(llamadas);
	}

	public Tipo check(Metodo metodoLlamador) throws SemanticException {
		Var v = null;
		Clase receptor = null;

		// el id es una variable local
		if (metodoLlamador.getVarsLocales().containsKey(k.getLexema())) {
			v = metodoLlamador.getVarsLocales().get(k.getLexema());
		}
		// el id es un argumento
		else if (metodoLlamador.getArgsFormales().containsKey(k.getLexema())) {
			v = metodoLlamador.getArgsFormales().get(k.getLexema());
		}
		// el id es un atributo de instancia.
		else if ((metodoLlamador.isDynamic())
				&& metodoLlamador.getClaseDeclaracion().getAtributosInstancia().containsKey(k.getLexema())) {
			v = metodoLlamador.getClaseDeclaracion().getAtributosInstancia().get(k.getLexema());
		}

		// el id es una clase, y es una llamada estatica.
		else if (TS.ts().containsClass(k.getLexema())) {
			// Como id es una clase, entonces la primer llamada debe ser
			// estatica.

			receptor = TS.ts().getClass(k);
			if (listaLlamadas.getList().size() > 0)
				return listaLlamadas.check(receptor, "estatica", metodoLlamador, false);
			else
				throw new SemanticException(k.getLine(), "No se encuentra una referencia a identificador " + k.getLexema());
			// retorna un error porque es un id sin llamada y ese id no
			// pertence a nada

		} else
			throw new SemanticException(k.getLine(), "No se encuentra una referencia a identificador " + k.getLexema());

		// como no hay llamadas posteriores, eso significa que no hay llamadas y
		// es solo un ID.
		if (listaLlamadas.getList().size() == 0) {
			// como el id hace referencia a una variable local ,argumento, o
			// de instancia entoces retorna el tipo.
			return v.getTipo();
		}

		// hay llamadas posteriores, entonces las resuelvo.

		// Como la primer llamada es dinamica, entonces el tipo de v debe
		// ser una clase.
		if (v.getTipo() instanceof TipoClase) {
			receptor = TS.ts().getClass(((TipoClase) v.getTipo()).getToken());
			return listaLlamadas.check(receptor, "dinamica", metodoLlamador,false);
		}

		return null;
	}

	@Override
	public int getLine() {
		return k.getLine();
	}
}

