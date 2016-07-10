package exceptions;

public class SyntaxError extends Exception {
	private int line;
	private String exp;
	private String found;
	public SyntaxError(int line, String msg, String exp, String found) {
		super("Syntax Error at line "+ line + ". Expected " + exp +" and " +found+ " has found: "+msg);
		this.line=line;
		this.exp=exp;
		this.found=found;
	}
	
	public SyntaxError(String msg, String exp, String found) {
		super("Syntax Error . Expected  " + exp +" and " +found+ " has found: "+msg);
		this.exp=exp;
		this.found=found;
	}
	
	public SyntaxError(String msg) {
		super(msg);
	}


	public int getLine() {
		return line;
	}

	public String getExp() {
		return exp;
	}

	public String getFound() {
		return found;
	}
}