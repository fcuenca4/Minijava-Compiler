package AST;

import java.util.List;

import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;
import TablaSimbolos.Tipos.TipoVoid;
import exceptions.SemanticException;

public class ListaLlamadas {
	private List<NodoLlamada> list;

	public ListaLlamadas(List<NodoLlamada> list) {
		this.list = list;
	}

	public List<NodoLlamada> getList() {
		return list;
	}

	/**
	 * Resuelve una lista de llamadas y retorna el tipo que retorna la ultima
	 * llamada en la lista. (e.g si la lista es m1().m2().m3() se retorna el
	 * tipo de m3() )
	 * 
	 * @param objetoReceptor
	 *            Es la clase que debe contener al metodo de la primer llamada
	 *            de la lista. Es decir, sea el mensaje la primer llamada de
	 *            lista, el objetoReceptor es la clase del objeto que recibe el
	 *            mensaje.
	 * @param tipoLlamada
	 *            Si es "dinamica" eso significa que la primer llamada debe ser
	 *            un metodo dinamico. Si es "estatica" entonces significa que la
	 *            primer llamada debe ser un metodo estatico. Si es "both" es
	 *            que no hay condiciones sobre el metodo de la primer llamada,
	 *            por lo cual puede ser tanto estatico como dinamico.
	 * @param claseLlamadora 
	 * @param metodoLlamador
	 * @param flag
	 * @return
	 * @throws SemanticException
	 */
	public Tipo check(Clase objetoReceptor, String tipoLlamada, Metodo metodoLlamador, boolean flag)
			throws SemanticException {
		if (!objetoReceptor.getMetodos().containsKey(list.get(0).getToken().getLexema()))
			throw new SemanticException(list.get(0).getToken().getLine(), "El metodo " + list.get(0).getToken().getLexema()
					+ " no esta declarado en la clase " + objetoReceptor.getClassID() + ".");

		Metodo m = objetoReceptor.getMetodos().get(list.get(0).getToken().getLexema());

		Tipo toRet = list.get(0).check(objetoReceptor, tipoLlamada, metodoLlamador, flag);
		flag = false;
		// El resto de las llamadas son dinamicas.

		Clase objetoReceptorBis = null;
		int i = 1;
		while (i < list.size()) {
			if (toRet instanceof TipoClase) {
				objetoReceptorBis = TS.ts().getClass(((TipoClase) toRet).getToken());
				toRet = list.get(i).check(objetoReceptorBis, "dinamica", metodoLlamador, flag);
			} else
				throw new SemanticException(list.get(i - 1).getToken().getLine(), "EL tipo de retorno de la llamada "
						+ list.get(i - 1).getID() + " debe ser de tipo clase y se encontro que es de tipo " + toRet.toString());

			i++;
		}
		return toRet;
	}

	public static void m1() {

	}
}

