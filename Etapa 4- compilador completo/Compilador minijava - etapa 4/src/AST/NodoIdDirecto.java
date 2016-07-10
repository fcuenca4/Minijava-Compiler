package AST;

import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Var;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;
import exceptions.SemanticException;
import minijava.GCI;
import minijava.Token;

public class NodoIdDirecto extends NodoExpPrimario{
	private Token k;
	private Encadenado siguiente;

	
	public NodoIdDirecto(Token ka,Encadenado sig){
		k=ka;
		siguiente=sig;
	
	}
	
	

	@Override
	public Tipo check( Metodo metodo) throws SemanticException {
		
		
		Var v = null;
		Clase receptor = null;
		//verifico si el id es un parametro, varlocal o varinst
			
			// el id es una variable local
			if (metodo.getVarsLocales().containsKey(k.getLexema())) {
				v = metodo.getVarsLocales().get(k.getLexema());
				
				GCI.gen().gen("LOAD " + v.getOffset(),"Apilo el contenido de la variable local <"+v.getID()+">");
			}
			// el id es un argumento
			else if (metodo.getArgsFormales().containsKey(k.getLexema())) {
				v = metodo.getArgsFormales().get(k.getLexema());
				
				GCI.gen().gen("LOAD " + v.getOffset(), "Apilo el contenido del argumento <"+v.getID()+">");
			}
			// el id es un atributo de instancia.
			else if ((metodo.isDynamic())
					&& metodo.getClaseDeclaracion().getAtributosInstancia().containsKey(k.getLexema())) {
				v = metodo.getClaseDeclaracion().getAtributosInstancia().get(k.getLexema());
				
				GCI.gen().gen("LOAD 3","Apilo la referencia a THIS el cual apunta a un objeto de la clase <"+metodo.getClaseDeclaracion().getClassID()+">");
				GCI.gen().gen("LOADREF " + v.getOffset(),"Apilo el contenido de la variable de instancia <"+v.getID()+">");
			}
			// el id es una clase, y es una llamada estatica.
			else if (TS.ts().containsClass(k.getLexema())) {
				// Como id es una clase, entonces la primer llamada debe ser
				// estatica.
				
				receptor = TS.ts().getClass(k);
				
				if (siguiente!=null)
					return siguiente.chequear(receptor,receptor, "estatica", metodo,metodo,false);
				else
					throw new SemanticException(k.getLine(), "No se encuentra una referencia a identificador " + k.getLexema());
				// retorna un error porque es un id sin llamada y ese id no
				// pertence a nada

			} else
				throw new SemanticException(k.getLine(), "No se encuentra una referencia a identificador " + k.getLexema());

			// como no hay llamadas posteriores, eso significa que no hay llamadas y
			// es solo un ID.
			if (siguiente == null) {
				// como el id hace referencia a una variable local ,argumento, o
				// de instancia entoces retorna el tipo.
				return v.getTipo();
			}

			// hay llamadas posteriores, entonces las resuelvo.

			// Como la primer llamada es dinamica, entonces el tipo de v debe
			// ser una clase.
			if (v.getTipo() instanceof TipoClase) {
				receptor = TS.ts().getClass(((TipoClase) v.getTipo()).getToken());
				
				return siguiente.chequear(receptor,receptor, "dinamica", metodo,metodo,false);
			}
		
		
		return null;
	}
	
	public Encadenado getEncadenado(){
		return siguiente;
	}
	
	public void setEncadenado(Encadenado sig){
		siguiente=sig;
	}
	
	public int getLine(){
		return k.getLine();
	}

}
