package exceptions;

@SuppressWarnings("serial")
public class SemanticException extends Exception{
	
	public SemanticException(int line, String s){
		super ("Error semantico en la linea: "+line+". "+s);
	}
	public SemanticException(){super();}
}
