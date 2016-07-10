package minijava;

/**
 * 
 * @author Francisco Cuenca, Brenda Dilschneider
 * 
 */

public class Token {
	private String name;
	private int line;
	private String lexema;

	public Token(String name, int line, String lexema) {
		this.line = line;
		this.name = name;
		this.lexema = lexema;
	}

	public String getName() {
		return name;
	}

	public int getLine() {
		return line;
	}

	public String getLexema() {
		return lexema;
	}

}
