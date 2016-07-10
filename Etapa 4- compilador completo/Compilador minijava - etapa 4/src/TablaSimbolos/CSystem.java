package TablaSimbolos;

import minijava.Token;
import AST.BloqueSystem;
//import AST.BloqueSystem;
import TablaSimbolos.Tipos.TipoBool;
import TablaSimbolos.Tipos.TipoChar;
import TablaSimbolos.Tipos.TipoInt;
import TablaSimbolos.Tipos.TipoString;
import TablaSimbolos.Tipos.TipoVoid;
import exceptions.SemanticException;

public class CSystem {

	public CSystem() {

	}

	public void init() throws SemanticException {
		
		Clase clase = TS.ts().addClass(new Token("id", 0, "System"));
		clase.setAncestro(new Token("id", 0, "Object"));
		clase.setConstructor(new Token("id", 0, "System"));

		Metodo read = clase.addMetodo("static", new Token("id", 0, "read"), TipoInt.instance());
		Metodo printB = clase.addMetodo("static", new Token("id", 0, "printB"), TipoVoid.instance());
		Metodo printI = clase.addMetodo("static", new Token("id", 0, "printI"), TipoVoid.instance());
		Metodo printC = clase.addMetodo("static", new Token("id", 0, "printC"), TipoVoid.instance());
		Metodo printS = clase.addMetodo("static", new Token("id", 0, "printS"), TipoVoid.instance());
		Metodo println = clase.addMetodo("static", new Token("id", 0, "println"), TipoVoid.instance());
		Metodo printBln = clase.addMetodo("static", new Token("id", 0, "printBln"), TipoVoid.instance());
		Metodo printIln = clase.addMetodo("static", new Token("id", 0, "printIln"), TipoVoid.instance());
		Metodo printCln = clase.addMetodo("static", new Token("id", 0, "printCln"),TipoVoid.instance());
		Metodo printSln = clase.addMetodo("static", new Token("id", 0, "printSln"), TipoVoid.instance());

		printB.addArgFormal(new Token("id", 0, "b"), TipoBool.instance(), 0);
		printI.addArgFormal(new Token("id", 0, "i"), TipoInt.instance(), 0);
		printC.addArgFormal(new Token("id", 0, "c"), TipoChar.instance(), 0);
		printS.addArgFormal(new Token("id", 0, "s"), TipoString.instance(), 0);

		printBln.addArgFormal(new Token("id", 0, "b"), TipoBool.instance(), 0);
		printIln.addArgFormal(new Token("id", 0, "i"), TipoInt.instance(), 0);
		printCln.addArgFormal(new Token("id", 0, "c"), TipoChar.instance(), 0);
		printSln.addArgFormal(new Token("id", 0, "s"), TipoString.instance(), 0);

		String imp_read = "READ\nSTORE 3";
		String imp_printB = "LOAD 3\nBPRINT";
		String imp_printI = "LOAD 3\nIPRINT";
		String imp_printC = "LOAD 3\nCPRINT";
		String imp_printS = "LOAD 3\nSPRINT";
		String imp_println = "PRNLN";
		String imp_printBln = imp_printB + '\n' + imp_println;
		String imp_printIln = imp_printI + '\n' + imp_println;
		String imp_printCln = imp_printC + '\n' + imp_println;
		String imp_printSln = imp_printS + '\n' + imp_println;

		read.setBloque(new BloqueSystem(imp_read));
		printB.setBloque(new BloqueSystem(imp_printB));
		printI.setBloque(new BloqueSystem(imp_printI));
		printC.setBloque(new BloqueSystem(imp_printC));
		printS.setBloque(new BloqueSystem(imp_printS));
		println.setBloque(new BloqueSystem(imp_println));
		printBln.setBloque(new BloqueSystem(imp_printBln));
		printIln.setBloque(new BloqueSystem(imp_printIln));
		printCln.setBloque(new BloqueSystem(imp_printCln));
		printSln.setBloque(new BloqueSystem(imp_printSln));
		

	}
}
