package AST;

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
		
		return true;
	}
}	
