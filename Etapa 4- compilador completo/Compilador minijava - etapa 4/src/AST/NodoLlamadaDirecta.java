package AST;

import java.util.List;

import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.Argumento;
import TablaSimbolos.TS;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoVoid;
import TablaSimbolos.Tipos.TipoClase;
import exceptions.SemanticException;

import minijava.GCI;
import minijava.Token;

//Pertenece al primer nodo de una cadena de llamadas
public class NodoLlamadaDirecta extends NodoExpPrimario {
	private Token k;
	private Encadenado siguiente;
	private List<NodoExpresion> argumentos;
	
	public NodoLlamadaDirecta(Token ka,List<NodoExpresion>  args,Encadenado sig){
		k=ka;
		siguiente=sig;
		argumentos=args;
		
	}

	@Override
	public Tipo check(Metodo metodo) throws SemanticException {
		
		
		Metodo metodoActual=metodo;
		Clase claseActual= metodo.getClaseDeclaracion();
		
		Metodo esteMetodo;
		Tipo toRet;
		if(claseActual.getMetodos().containsKey(k.getLexema())){
			esteMetodo=claseActual.getMetodos().get(k.getLexema());
			toRet=esteMetodo.getRetorno();
		}
		else throw new SemanticException(getLine(),"El metodo "+k.getLexema()+
				" no se encuentra declarado en la clase "+claseActual.getClassID());
		
		//Si el contexto es estatico y este metodo es dinamico, es un error semantico.
		if(metodo.isStatic()&&esteMetodo.isDynamic())
			throw new SemanticException(getLine(),"El metodo " +k.getLexema()+" es dinamico y no puede " +
					"encontrarse en un contexto estatico.");
		
		// verifico que la cantidad de argumentos actuales coincida con la
				// cantidad de argumentos formales.
		
		if (esteMetodo.getArgsFormales().size() != argumentos.size())
			throw new SemanticException(k.getLine(),"La llamada al metodo " + esteMetodo.getID() + "de la clase "
				+ claseActual.getClassID() + " no coincide con la declaracion");
		String tipo_llamada;
		boolean flag=true;
		if(esteMetodo.isStatic()){
			
			genStatic(metodo, esteMetodo, claseActual);
			tipo_llamada="estatica";}
		else{
			
			genDynamic(metodo, esteMetodo, claseActual,flag);
			tipo_llamada="both";} //tipo_llamada="dinamica"
		
		Clase claseCalificadora=null;	
		if (toRet instanceof TipoClase){
			claseCalificadora=TS.ts().getClass(((TipoClase) toRet).getToken()); 
		}
		
		if(siguiente!=null){
			if(!toRet.esTipoClase())
				throw new SemanticException(getLine(),"Se esperaba que el retorno del método "+k.getLexema()+" sea de tipo clase.");
			
			
			return siguiente.chequear(claseCalificadora, claseActual, "dinamica", esteMetodo, metodoActual,true); //flag
		}
		return toRet;
	}

private void genStatic(Metodo llamador, Metodo metodo, Clase objetoReceptor) throws SemanticException {
	if(!(metodo.getRetorno() instanceof TipoVoid))
		GCI.gen().gen("RMEM 1","Se reserva espacio para el retorno de la llamada al metodo <"+ metodo.getClaseDeclaracion().getClassID()+"::"+metodo.getID()+">");

	
	Argumento aFormal =null;
	NodoExpresion e =null;
	Tipo tipoExpresion=null;
	
	List<Argumento> argsFormales =metodo.getArgsFormalesOrdenados();
	for (int i = 0; i<argsFormales.size();i++){
		aFormal = argsFormales.get(i);
		e = argumentos.get(i);
		
		tipoExpresion = e.check(llamador);
		
		if (!tipoExpresion.conforma(aFormal.getTipo()))
			throw new SemanticException(e.getLine() , "El tipo " + tipoExpresion.toString() + " no conforma con el tipo "
					+ aFormal.getTipo().toString() + ".");
	}

	GCI.gen().gen("PUSH " +metodo.getCode(),"Apila la etiqueta del metodo <"+ metodo.getClaseDeclaracion().getClassID()+"::"+metodo.getID()+">");
	GCI.gen().gen("CALL","Hace la llamada al metodo <"+metodo.getClaseDeclaracion().getClassID()+"::"+metodo.getID()+">");
}

private void genDynamic(Metodo llamador, Metodo metodo, Clase objetoReceptor,boolean flag) throws SemanticException {
	if (llamador.isDynamic() && flag)
		GCI.gen().gen("LOAD 3","Apila el puntero a THIS el cual apunta a un objeto de la clase <" + llamador.getClaseDeclaracion().getClassID() +">");
	
	
	if(!(metodo.getRetorno() instanceof TipoVoid)){
		GCI.gen().gen("RMEM 1","Se reserva espacio para el retorno de la llamada al metodo <"+ metodo.getClaseDeclaracion().getClassID()+"::"+metodo.getID()+">");
		GCI.gen().gen("SWAP",""); // se agrega este swap por el this
	}
	
	// controlo que el tipo de lo argumentos actuales conforme los tipos
	// de los argumentos formales, ademas se genera el codigo.
	
	Argumento aFormal =null;
	NodoExpresion e =null;
	Tipo tipoExpresion=null;
	
	List<Argumento> argsFormales =metodo.getArgsFormalesOrdenados();
	for (int i = 0; i<argsFormales.size();i++){
		aFormal = argsFormales.get(i);
		e = argumentos.get(i);
		
		tipoExpresion = e.check(llamador);
		
		if (!tipoExpresion.conforma(aFormal.getTipo()))
			throw new SemanticException(e.getLine() , "El tipo " + tipoExpresion.toString() + " no conforma con el tipo "
					+ aFormal.getTipo().toString() + ".");
		
		GCI.gen().gen("SWAP","");
	}


	GCI.gen().gen("DUP","");
	GCI.gen().gen("LOADREF 0","Accede a la VT de la clase <"+metodo.getClaseDeclaracion().getClassID()+">");
	GCI.gen().gen("LOADREF " + metodo.getOffset(),"Se desplaza en la VT y Carga el metodo <"+metodo.getClaseDeclaracion().getClassID()+"::"+metodo.getID()+">");
	GCI.gen().gen("CALL","Hace una llamada al metodo <"+metodo.getClaseDeclaracion().getClassID()+"::"+metodo.getID()+">");
}

	@Override
	public int getLine() {
		return k.getLine();
	}

}
