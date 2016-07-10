package AST;

import java.util.List;

import minijava.Token;
import TablaSimbolos.Argumento;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Tipos.Tipo;
import exceptions.SemanticException;

public class NodoPrimNew extends NodoExpPrimario {
	private Token k;
	private List<NodoExpresion> argsActuales;
	private ListaLlamadas listaLlamadas;

	public NodoPrimNew(Token k, List<NodoExpresion> args, List<NodoLlamada> llamadas) {
		this.k = k;
		this.argsActuales = args;
		this.listaLlamadas = new ListaLlamadas(llamadas);
	}

	public Tipo check(Metodo llamador) throws SemanticException {
		Clase claseConstruir = TS.ts().getClass(k);
		// la verificacion de nombre del constructor fue analizado en la primer
		// pasada.
		Metodo constructor = claseConstruir.getConstructor();

		/*********************************************/
		// VERIFICO QUE LOS ARGUMENTOS ACTUALES DEL CONSTRUCTOR CONFORMEN LOS
		// ARGUEMNTOS FORMALES DE LA DECLARACION

		// verifico que la cantidad de argumentos formales y actuales sea la
		// misma.
		if (constructor.getArgsFormales().size() != argsActuales.size())
			throw new SemanticException(k.getLine(),"El constructor no coincide con la declaracion.");

	
		// para cada argumento formal a del constructor hago:
		
		Argumento aFormal =null;
		NodoExpresion e =null;
		Tipo tipoExpresion=null;
		
		List<Argumento> argsFormales =constructor.getArgsFormalesL();
		for (int i = 0; i<argsFormales.size();i++){
			aFormal = argsFormales.get(i);
			e = argsActuales.get(i);
			
			tipoExpresion = e.check(llamador);
			
			if (!tipoExpresion.conforma(aFormal.getTipo()))
				throw new SemanticException(e.getLine() , "El tipo " + tipoExpresion.toString() + " no conforma con el tipo "
						+ aFormal.getTipo().toString() + ".");
		}
		
		/********************************************/

		if (listaLlamadas.getList().size() > 0)
			return listaLlamadas.check(claseConstruir, "dinamica", llamador,true);

		
		return claseConstruir.getConstructor().getRetorno();
	}

	@Override
	public int getLine() {
		return k.getLine();
	}

}