package AST;

import java.util.List;

import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;
import exceptions.SemanticException;

public class NodoPrimLlamada1 extends NodoExpPrimario {
	private NodoExpresion e;
	private ListaLlamadas listaLlamadas;

	public NodoPrimLlamada1(NodoExpresion e, List<NodoLlamada> llamadas) {
		this.e = e;
		this.listaLlamadas = new ListaLlamadas(llamadas);
	}

	@Override
	public Tipo check(Metodo metodoLlamador) throws SemanticException {
		Tipo tipoExpresion = e.check(metodoLlamador);

		if (tipoExpresion instanceof TipoClase) {
			Clase c = TS.ts().getClass(((TipoClase) tipoExpresion).getToken());
			if (listaLlamadas.getList().size() > 0)
				return listaLlamadas.check(c, "dinamica", metodoLlamador, false);
		} else if (listaLlamadas.getList().size() > 0)
			throw new SemanticException(e.getLine(), "Se esperaba que la expresion sea de Tipo Clase y es de tipo "
					+ tipoExpresion.toString() + ".");

		return tipoExpresion;
	}

	@Override
	public int getLine() {
		return e.getLine();
	}
}