package AST;

import java.util.List;

import exceptions.SemanticException;

import minijava.GCI;
import minijava.Token;
import TablaSimbolos.Argumento;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoClase;
import TablaSimbolos.Tipos.TipoVoid;

public class LlamadaEncadenada extends Encadenado{

	private Token k;
	private Encadenado siguiente;
	private List<NodoExpresion> argumentos;
	
	public LlamadaEncadenada(Token ka,Encadenado sig, List<NodoExpresion> arg){
		k=ka;
		siguiente=sig;
		argumentos=arg;
	}
	
	@Override
	public Tipo chequear(Clase claseCalificadora,Clase claseActual,String tipo_llamada, Metodo metodoLlamador, Metodo metodoActual,boolean flag) throws SemanticException {
		
		
		Metodo esteMetodo;
		if(claseCalificadora.getMetodos().containsKey(k.getLexema())){
			esteMetodo=claseCalificadora.getMetodos().get(k.getLexema());
			
			
			
			
			
			//Controlo los argumentos: misma cantidad
			if (esteMetodo.getArgsFormales().size() != argumentos.size())
				throw new SemanticException(k.getLine(),"La llamada al metodo " + esteMetodo.getID() + " de la clase "
						+ claseCalificadora.getClassID() + " no coincide con la declaracion");
			
			
			if (tipo_llamada.equals("estatica")) {
				// Si el tipo de la llamada es estatica, entonces el metodo a
				// invocar debe ser estatico.

				if (!esteMetodo.isStatic())
					throw new SemanticException(k.getLine(),"Se esperaba que el metodo " + esteMetodo.getID() + " de la clase "
							+ claseCalificadora.getClassID() + " sea estatico, y se encontro que es dinamico.");
				
				genStatic( esteMetodo,argumentos,metodoActual, claseActual);
			}else if(tipo_llamada.equals("dinamica")){
				// Si el tipo de la llamada es dinamica, entonces el metodo a
				// invocar debe ser dinamico.
				if (!esteMetodo.isDynamic())
					throw new SemanticException(k.getLine(),"Se esperaba que el metodo " + esteMetodo.getID() + " de la clase "
							+ claseCalificadora.getClassID() + " sea dinamico, y se encontro que es estatico.");

				genDynamic(metodoActual, esteMetodo,flag);
			}else{
				if(esteMetodo.isDynamic())
					genDynamic(metodoActual, esteMetodo,flag); 
				else{
					genStatic( esteMetodo,argumentos,metodoActual, claseActual);}
			}
			
			Tipo toRet=esteMetodo.getRetorno();	
			if (toRet instanceof TipoClase){
				claseCalificadora=TS.ts().getClass(((TipoClase) toRet).getToken()); //la clase a pasar es el retorno de esta clase
			}
			
			
			if (siguiente!=null){
				if(!esteMetodo.getRetorno().esTipoClase())
					throw new SemanticException(getLine(),"Se esperaba que el retorno del método "+k.getLexema()+" sea de tipo clase.");
				
				//sea la llamada que sea, si hay siguiente la llamada va a ser dinamica.
				//si era estatica: B.m().n() (n es dinamica)
				//si era dinamica: b().m().n() (n es dinamica)
				flag=false;
				return siguiente.chequear(claseCalificadora,claseActual, "dinamica", esteMetodo,metodoActual,flag);
				
			}

			return esteMetodo.getRetorno();
			
		}
		else 
			throw new SemanticException(getLine(),"Error semantico: El metodo "+k.getLexema()+" no ha sido declarado en la clase "+claseCalificadora.getClassID()+".");
			
			
		
	}
	
	public void controlarArgumentos(Metodo metodoActual,List<NodoExpresion> argsActuales,Metodo metodoActualPosta, Clase claseActualPosta)throws SemanticException{
		Tipo parActual;
		Tipo parFormal;
		

		// Me fijo si conforman los tipos
		for (int i = 0; i < argsActuales.size(); i++) {
			parActual = argsActuales.get(i).check(metodoActualPosta);
			parFormal = metodoActual.getArgsFormalesOrdenados().get(i)
					.getTipo();
			if (parActual instanceof TipoClase
					&& parFormal instanceof TipoClase) {
				if (!parActual.conforma(parFormal))
					throw new SemanticException(
							k.getLine(),
							"La invocacion al metodo \""
									+ k.getLexema()
									+ "\" no posee los tipos de clase conformantes en los parametros (Linea: "
									+ k.getLine() + ").");
			} else if (!parActual.toString().equals(parFormal.toString()))
				throw new SemanticException(
						k.getLine(),
						"La invocacion al metodo \""
								+ k.getLexema()
								+ "\" no posee los tipos primitivos correctos en los parametros.");
		}

	}
	private void genStatic(Metodo metodo, List<NodoExpresion>argumentos,Metodo metodoActualPosta, Clase claseActualPosta) throws SemanticException {
		if(!(metodo.getRetorno() instanceof TipoVoid))
			GCI.gen().gen("RMEM 1","Se reserva espacio para el retorno de la llamada al metodo <"+ metodo.getClaseDeclaracion().getClassID()+"::"+metodo.getID()+">");
		controlarArgumentos(metodo,argumentos,metodoActualPosta, claseActualPosta);
		
		
		
		GCI.gen().gen("PUSH " +metodo.getCode(),"Apila la etiqueta del metodo <"+ metodo.getClaseDeclaracion().getClassID()+"::"+metodo.getID()+">");
		GCI.gen().gen("CALL","Hace la llamada al metodo <"+metodo.getClaseDeclaracion().getClassID()+"::"+metodo.getID()+">");
	}
	
	private void genDynamic(Metodo llamador, Metodo metodo,boolean flag) throws SemanticException {
		//System.out.println("Flag en el genDynamic:"+flag);
		//if (llamador.isDynamic()&&flag){
			//GCI.gen().gen("LOAD 3","Apila el puntero a THIS el cual apunta a un objeto de la clase <" + llamador.getClaseDeclaracion().getClassID() +">");
			
		//}
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
	public void setEncadenado(Encadenado enc) {
		siguiente=enc;
		
	}

	@Override
	public Encadenado getEncadenado() {
		return siguiente;
	}
	
	public int getLine(){
		return k.getLine();
	}

}
