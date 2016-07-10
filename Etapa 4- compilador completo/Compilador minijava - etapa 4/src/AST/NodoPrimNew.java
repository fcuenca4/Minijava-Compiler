package AST;

import java.util.List;

import minijava.GCI;
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
	
	private Encadenado listaLlamadas;

	
	public NodoPrimNew(Token k, List<NodoExpresion> args, Encadenado llamadas) {
		this.k = k;
		this.argsActuales = args;
		
		this.listaLlamadas=llamadas;
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

		GCI.gen().openCommentD("Inicia construccion de un objeto de la clase <"+claseConstruir.getClassID()+">");
		GCI.gen().gen("RMEM 1","Reserva espacio para el retorno del constructor de la clase <"+ claseConstruir.getClassID()+">");
		GCI.gen().gen("PUSH " + claseConstruir.getLastOffsetAI(),"Apila el tamano de CIR de la clase <"+claseConstruir.getClassID()+">");
		GCI.gen().gen("PUSH lmalloc","Reserva espacio en la memoria heap para el CIR");
		GCI.gen().gen("CALL","Invoca a la rutina de malloc.");
		GCI.gen().gen("DUP","Duplica la direccion del CIR que se encuentra en el tope de la pila.");
		GCI.gen().gen("PUSH " + claseConstruir.getCode(),"Apila la etiqueta de la VT de la clase <"+claseConstruir.getClassID()+">");
		GCI.gen().gen("STOREREF 0","");
		GCI.gen().gen("DUP","");
		// para cada argumento formal a del constructor hago:
		
		Argumento aFormal =null;
		NodoExpresion e =null;
		Tipo tipoExpresion=null;
		
		List<Argumento> argsFormales =constructor.getArgsFormalesOrdenados();
		for (int i = 0; i<argsFormales.size();i++){
			aFormal = argsFormales.get(i);
			e = argsActuales.get(i);
			
			tipoExpresion = e.check(llamador);
			GCI.gen().gen("SWAP","");
			if (!tipoExpresion.conforma(aFormal.getTipo()))
				throw new SemanticException(e.getLine() , "El tipo " + tipoExpresion.toString() + " no conforma con el tipo "
						+ aFormal.getTipo().toString() + ".");
		}
		
		/********************************************/

		GCI.gen().gen("PUSH " + claseConstruir.getConstructor().getCode(),"Apila la etiqueta del constructor de la clase <"+claseConstruir.getClassID()+">");
		GCI.gen().gen("CALL","Hace una llamada al constructor de la clase <"+claseConstruir.getClassID()+">");
		
		if(listaLlamadas!=null)
			return listaLlamadas.chequear(claseConstruir,claseConstruir, "dinamica", llamador,llamador,true);

		GCI.gen().closeCommentD("Fin de la construccion del objeto de la clase <"+claseConstruir.getClassID()+">");
		return claseConstruir.getConstructor().getRetorno();
	}

	@Override
	public int getLine() {
		return k.getLine();
	}

}