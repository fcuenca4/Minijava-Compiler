package minijava;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import exceptions.LexicalError;

/**
 * 
 * @author Francisco Cuenca, Brenda Dilschneider
 * 
 */

public class LexicalAnalyzer {

	private enum ErrorCode {
		CharacterLiteral, Character, Comment, NumberLiteral, StringLiteral
	};

	private List<String> KeyWords; // palabras reservadas
	private BufferedReader buffer;
	private DataInputStream dis;
	private FileInputStream fstream;
	private int line, col;
	private char last;
	private boolean eof, eol;

	/**
	 * Constructor para el analizador lexico, que recibe la direccion del codigo
	 * fuente.
	 * 
	 * @param path
	 *            direccion del archivo codigo fuente.
	 * @throws IOException
	 *             si el archivo no existe, o no es posible accederlo.
	 */
	public LexicalAnalyzer(String path) throws IOException {
		
		initKeyWords();
		
		// read file.
		fstream = new FileInputStream(path);
		dis = new DataInputStream(fstream);
		buffer = new BufferedReader(new InputStreamReader(dis));

		eof = false;
		eol = false;
		line = 1;
		col = 1;
		last = nextChar();
	}

	private char nextChar() {
		if (eol) {
			eol = false;
			col = 1;
			line++;
		} else
			col++;
		char ret;
		try {
			ret = (char) buffer.read();
		} catch (IOException e) {
			e.printStackTrace();
			eof = true;
			return (char) -1;
		}

		if (ret == (char) -1)
			eof = true;
		else if (ret == '\n')
			eol = true;

		return ret;

	}

	/**
	 * Inicializa la lista de palabras reservadas.
	 */
	private void initKeyWords() {
		KeyWords = new ArrayList<String>();
		KeyWords.add("class");
		KeyWords.add("extends");
		KeyWords.add("varinst");
		KeyWords.add("varlocal");
		KeyWords.add("static");
		KeyWords.add("dynamic");
		KeyWords.add("void");
		KeyWords.add("boolean");
		KeyWords.add("char");
		KeyWords.add("int");
		KeyWords.add("String");
		KeyWords.add("if");
		KeyWords.add("else");
		KeyWords.add("while");
		KeyWords.add("for");
		KeyWords.add("return");
		KeyWords.add("this");
		KeyWords.add("new");
		KeyWords.add("true");
		KeyWords.add("false");
		KeyWords.add("null");
	}

	/**
	 * Retorna un token.
	 * 
	 * @return un token.
	 * @throws LexicalError
	 *             si se encontro un error al reconocer el token.
	 */
	public Token getToken() throws LexicalError {
		// last = nextChar();
		int state = 0;
		String readed = "";

		while (true) {
			if (eof) {
				switch (state) {
				case 6:
				case 7:
				case 8:
					error(ErrorCode.Comment,line,col,"");
				case 10:
					error(ErrorCode.StringLiteral,line,col,readed);
				case 35:
				case 36:
				case 38:
				case 39:
				case 40:
				case 42:
					error(ErrorCode.CharacterLiteral,line,col,readed);
				}
				return new Token("EOF",line,"EOF");
			}

			switch (state) {
			case 0:
				// consume delimitadores. (espacios, tabuladores, enters)
				if (last == '\t' || last == ' ' || last == '\n'
						|| last == (char) 13 || last == (char) 10) {
					last = nextChar();
					break;
				}

				switch (last) {
				case '/':
					state = 2;
					readed += last;
					break;
				case '"':
					state = 10;
					break;
				case '(':
					state = 12;
					readed += last;
					break;
				case ')':
					state = 13;
					readed += last;
					break;
				case '{':
					state = 14;
					readed += last;
					break;
				case '}':
					state = 15;
					readed += last;
					break;
				case ';':
					state = 16;
					readed += last;
					break;
				case ',':
					state = 17;
					readed += last;
					break;
				case '.':
					state = 18;
					readed += last;
					break;
				case '>':
					state = 19;
					readed += last;
					break;
				case '<':
					state = 21;
					readed += last;
					break;
				case '!':
					state = 23;
					readed += last;
					break;
				case '=':
					state = 25;
					readed += last;
					break;
				case '+':
					state = 27;
					readed += last;
					break;
				case '*':
					state = 28;
					readed += last;
					break;
				case '-':
					state = 29;
					readed += last;
					break;
				case '&':
					state = 30;
					readed += last;
					break;
				case '|':
					state = 32;
					readed += last;
					break;
				case '%':
					state = 34;
					readed += last;
					break;
				case '\'':
					state = 35;
					break;
				default:
					if (Character.isLetter(last) || last == '_') {
						state = 1;
						readed += last;
						break;
					}
					if (Character.isDigit(last)) {
						state = 9;
						readed += last;
						break;
					}
					error(ErrorCode.Character, line,col,""+last);
				}
				break;
			case 1:
				last = nextChar();
				if (Character.isLetterOrDigit(last) || last == '_') {
					readed += last;
					break;
				}

				if (KeyWords.contains(readed))
					return new Token(readed, line, readed);
				else
					return new Token("id", line, readed);
				
			case 2:
				last= nextChar();
				if (last=='/'){
					state=3;
					break;
				}
				if(last=='*'){ 
					state=6;
					break;
				}
				return new Token("/", line, readed);
			case 3:
				last = nextChar();
				switch (last) {
				case '*':
					state = 6;
					break;
				case '\n':
					state = 5;
					break;
				default:
					state = 4;
					break;
				}
				break;
			case 4:
				last = nextChar();
				state = last == '\n' ? 5 : 4;
				break;
			case 5:
				last = nextChar();
				state = 0;
				readed = "";
				break;
			case 6:
				last = nextChar();
				state = last == '*' ? 7 : 6;
				break;
			case 7:
				last = nextChar();
				switch (last) {
				case '*':
					break;
				// Me quedo en el mismo estado.
				case '/':
					state = 5;  
					break;
				default:
					state = 6;
					break;
				}
				break;
			case 8:
				last = nextChar();
				state = last == '/' ? 5 : 6;
				break;
			case 9:
				last = nextChar();
				if (Character.isDigit(last)) {
					readed += last;
					break;
				} else if (Character.isLetter(last) || last == '('
						|| last == '{' || last == '\"' || last == '\''
						|| last == '.')
					error(ErrorCode.NumberLiteral, line,col,readed+last);

				return new Token("intLiteral", line, readed);
			case 10:
				last = nextChar();
				switch (last) {
				case '"':
					state = 11;
					break;
				case '\n':
					error(ErrorCode.StringLiteral,line,col,readed);
				default:
					readed += last;
				}

				break;
			case 11:
				last = nextChar();
				return new Token("stringLiteral", line, readed);
			case 12:
				last = nextChar();
				return new Token("(", line, readed);
			case 13:
				last = nextChar();
				return new Token(")", line, readed);
			case 14:
				last = nextChar();
				return new Token("{", line, readed);
			case 15:
				last = nextChar();
				return new Token("}", line, readed);
			case 16:
				last = nextChar();
				return new Token(";", line, readed);
			case 17:
				last = nextChar();
				return new Token(",", line, readed);
			case 18:
				last = nextChar();
				return new Token(".", line, readed);
			case 19:
				last = nextChar();
				if (last == '=') {
					readed += last;
					state = 20;
					break;
				}
				return new Token(">", line, readed);
			case 20:
				last = nextChar();
				return new Token(">=", line, readed);
			case 21:
				last = nextChar();
				if (last == '=') {
					readed += last;
					state = 22;
					break;
				}
				return new Token("<", line, readed);
			case 22:
				last = nextChar();
				return new Token("<=", line, readed);
			case 23:
				last = nextChar();
				if (last == '=') {
					readed += last;
					state = 24;
					break;
				}
				return new Token("!", line, readed); // negative
			case 24:
				last = nextChar();
				return new Token("!=", line, readed); // not equal
			case 25:
				last = nextChar();
				if (last == '=') {
					readed += last;
					state = 26;
					break;
				}
				return new Token("=", line, readed);
			case 26:
				last = nextChar();
				return new Token("==", line, readed);
			case 27:
				last = nextChar();
				return new Token("+", line, readed);
			case 28:
				last = nextChar();
				return new Token("*", line, readed);
			case 29:
				last = nextChar();
				return new Token("-", line, readed);
			case 30:
				last = nextChar();
				if (last == '&') {
					readed += last;
					state = 31;
				}
				break;
			case 31:
				last = nextChar();
				return new Token("&&", line, readed);
			case 32:
				last = nextChar();
				if (last == '|') {
					readed += last;
					state = 33;
				}
				break;
			case 33:
				last = nextChar();
				return new Token("||", line, readed);
			case 34:
				last = nextChar();
				return new Token("%", line, readed);
			case 35:
				last = nextChar();
				if (last != '\\' && last != '\'' && last != '\n') {
					readed += last;
					state = 36;
				} else if (last == '\\') {
					state = 38;
				}
				break;
			case 36:
				last = nextChar();
				if (last == '\'') {
					state = 37;
					break;
				}
				error(ErrorCode.CharacterLiteral, line,col,""+last);
			case 37:
				last = nextChar();
				return new Token("charLiteral", line, readed);
			case 38:
				last = nextChar();
				if (last != 'n' && last != 't') {
					readed += last;
					state = 36;
				} else if (last == 'n') {
					readed += '\n';
					state = 36;
				} else {
					readed += '\t';
					state = 36;
				}
				break;
			}
			
		}

	}

	public void error(ErrorCode code, int linea, int col, String readed)
			throws LexicalError {
		String str = "Error Lexico en la linea " + linea + ", columna " + col
				+ ". ";
		switch (code) {
		case Character:
			str += "El caracter " + readed + " es invalido.";
			break;
		case CharacterLiteral:
			str += "El literal caracter " + readed + " es invalido.";
			break;
		case Comment:
			str += "Error de comentario.";
			break;
		case NumberLiteral:
			str += "El literal numero " + readed + "es invalido";
			break;
		case StringLiteral:
			str += "El literal string \'" + readed + "\' es invalido.";
			break;
		}
		throw new LexicalError(str);
	}

	/**
	 * Cierra el archivo del codigo fuente.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		dis.close();
	}
}
