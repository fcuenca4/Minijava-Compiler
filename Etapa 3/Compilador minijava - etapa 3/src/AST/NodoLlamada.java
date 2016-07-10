package AST;

import java.util.List;

import minijava.Token;
import TablaSimbolos.Argumento;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.Tipos.Tipo;
import exceptions.SemanticException;

public class NodoLlamada {

	protected Token k;
	protected List<NodoExpresion> argsActuales;

	public NodoLlamada(Token k, List<NodoExpresion> argsActuales) {
		this.k = k;
		this.argsActuales = argsActuales;
	}

	public String getID() {
		return k.getLexema();
	}

	public Token getToken() {
		return k;
	}

	public List<NodoExpresion> getArgsActuales() {
		return argsActuales;
	}

	public Tipo check(Clase objetoReceptor, String tipoLlamada, Metodo metodoLlamador,boolean flag) throws SemanticException {

		// obtengo el metodo asociado a la llamada.
		if (!objetoReceptor.getMetodos().containsKey(k.getLexema()))
			throw new SemanticException(k.getLine(),"El metodo " + k.getLexema() + " no esta declarado en la clase "
					+ objetoReceptor.getClassID() + ".");

		Metodo metodo = objetoReceptor.getMetodos().get(k.getLexema());

		// verifico que la cantidad de argumentos actuales coincida con la
		// cantidad de argumentos formales.
		if (metodo.getArgsFormales().size() != argsActuales.size())
			throw new SemanticException(k.getLine(),"La llamada al metodo " + metodo.getID() + "de la clase "
					+ objetoReceptor.getClassID() + " no coincide con la declaracion");

		if (tipoLlamada.equals("estatica")) {
			// Si el tipo de la llamada es estatica, entonces el metodo a
			// invocar debe ser estatico.

			if (!metodo.isStatic())
				throw new SemanticException(k.getLine(),"Se esperaba que el metodo " + metodo.getID() + " de la clase "
						+ objetoReceptor.getClassID() + " sea estatico, y se encontro que es dinamico.");
			
			genStatic(metodoLlamador, metodo, objetoReceptor);
		} else if(tipoLlamada.equals("dinamica")){
			// Si el tipo de la llamada es dinamica, entonces el metodo a
			// invocar debe ser dinamico.
			if (!metodo.isDynamic())
				throw new SemanticException(k.getLine(),"Se esperaba que el metodo " + metodo.getID() + " de la clase "
						+ objetoReceptor.getClassID() + " sea dinamico, y se encontro que es estatico.");

			genDynamic(metodoLlamador, metodo, objetoReceptor,flag);
		}else{
			if(metodo.isDynamic())
				genDynamic(metodoLlamador, metodo, objetoReceptor,flag);
			else
				genStatic(metodoLlamador, metodo, objetoReceptor);
		}

		return metodo.getRetorno();
	}

	private void genStatic(Metodo llamador, Metodo metodo, Clase objetoReceptor) throws SemanticException {
		
		
		
		Argumento aFormal =null;
		NodoExpresion e =null;
		Tipo tipoExpresion=null;
		
		List<Argumento> argsFormales =metodo.getArgsFormalesL();
		for (int i = 0; i<argsFormales.size();i++){
			aFormal = argsFormales.get(i);
			e = argsActuales.get(i);
			
			tipoExpresion = e.check(llamador);
			
			if (!tipoExpresion.conforma(aFormal.getTipo()))
				throw new SemanticException(e.getLine() , "El tipo " + tipoExpresion.toString() + " no conforma con el tipo "
						+ aFormal.getTipo().toString() + ".");
		}

		
	}

	private void genDynamic(Metodo llamador, Metodo metodo, Clase objetoReceptor,boolean flag) throws SemanticException {
		
		
		
		// controlo que el tipo de lo argumentos actuales conforme los tipos
		// de los argumentos formales, ademas se genera el codigo.
		
		Argumento aFormal =null;
		NodoExpresion e =null;
		Tipo tipoExpresion=null;
		
		List<Argumento> argsFormales =metodo.getArgsFormalesL();
		for (int i = 0; i<argsFormales.size();i++){
			aFormal = argsFormales.get(i);
			e = argsActuales.get(i);
			
			tipoExpresion = e.check(llamador);
			
			if (!tipoExpresion.conforma(aFormal.getTipo()))
				throw new SemanticException(e.getLine() , "El tipo " + tipoExpresion.toString() + " no conforma con el tipo "
						+ aFormal.getTipo().toString() + ".");
			
			
		}


		
	}

}

