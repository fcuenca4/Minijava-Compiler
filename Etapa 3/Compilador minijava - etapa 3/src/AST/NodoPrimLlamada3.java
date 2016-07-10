package AST;

import java.util.List;

import minijava.Token;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import exceptions.SemanticException;

public class NodoPrimLlamada3 extends NodoExpPrimario {
	private List<NodoExpresion> argsActuales;
	private Token k;
	private ListaLlamadas listaLlamadas;

	public NodoPrimLlamada3(Token k, List<NodoExpresion> args, List<NodoLlamada> llamadas) {
		this.k = k;
		this.argsActuales = args;
		this.listaLlamadas = new ListaLlamadas(llamadas);
		listaLlamadas.getList().add(0,new NodoLlamada(k,argsActuales));
	}

	public Tipo check(Metodo metodoLlamador) throws SemanticException {
		if (metodoLlamador.isStatic())
			return listaLlamadas.check(metodoLlamador.getClaseDeclaracion(), "estatica", metodoLlamador,true);
		else
			return listaLlamadas.check(metodoLlamador.getClaseDeclaracion(), "both", metodoLlamador,true);
	}

	@Override
	public int getLine() {
		return k.getLine();
	}

}

