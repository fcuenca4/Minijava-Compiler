package AST;

import minijava.GCI;
import TablaSimbolos.Metodo;
import exceptions.SemanticException;

public class BloqueSystem extends Bloque{
	private String implementacion;
	public BloqueSystem(String implementacion){
		super(null);
		this.implementacion=implementacion;
	}
	@Override
	public boolean check(Metodo metodo) throws SemanticException {
		String[]  simp = implementacion.split("\n");
		for (String s : simp){
			GCI.gen().gen(s,"");
		}
		return true;
	}
}	
	
