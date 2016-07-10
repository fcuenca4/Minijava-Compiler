package AST;

import exceptions.SemanticException;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;

public class NodoPrimParentizado  extends NodoExpPrimario {
	private NodoExpresion e;
	private Encadenado listaLlamadas;

	public NodoPrimParentizado(NodoExpresion e, Encadenado llamadas) {
		this.e = e;
		this.listaLlamadas = llamadas;
		
	}

	@Override
	public Tipo check(Metodo metodoLlamador) throws SemanticException {
		Tipo tipoExpresion = e.check(metodoLlamador);

		if (tipoExpresion instanceof TipoClase) {
			Clase c = TS.ts().getClass(((TipoClase) tipoExpresion).getToken());
			
			if (listaLlamadas!=null)
				return listaLlamadas.chequear(c,c, "dinamica", metodoLlamador,metodoLlamador,false);
		} else if (listaLlamadas!=null)
			throw new SemanticException(e.getLine(), "Se esperaba que la expresion sea de Tipo Clase y es de tipo "
					+ tipoExpresion.toString() + ".");

		return tipoExpresion;
	}

	@Override
	public int getLine() {
		return e.getLine();
	}
}