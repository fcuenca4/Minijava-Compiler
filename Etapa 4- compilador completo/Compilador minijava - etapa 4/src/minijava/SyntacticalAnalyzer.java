package minijava;

import java.util.LinkedList;
import java.util.List;

import AST.Bloque;
import AST.Encadenado;
import AST.LlamadaEncadenada;
import AST.NodoAsignacion;
import AST.NodoBloque;
import AST.NodoExpPrimario;
import AST.NodoExpUnaria;
import AST.NodoExpresion;
import AST.NodoExpresionBinaria;
import AST.NodoFor;
import AST.NodoIDEncadenadoDer;
import AST.NodoId;
import AST.NodoIdDirecto;
import AST.NodoIdEncadenado;
import AST.NodoIf;
import AST.NodoLlamadaDirecta;
import AST.NodoPrimLiteral;
import AST.NodoPrimNew;
import AST.NodoPrimParentizado;
import AST.NodoPrimThis;
import AST.NodoReturn;
import AST.NodoSenDelim;
import AST.NodoSenSimple;
import AST.NodoSentencia;
import AST.NodoWhile;
import TablaSimbolos.Clase;
import TablaSimbolos.Metodo;
import TablaSimbolos.TS;
import TablaSimbolos.Tipos.Tipo;
import TablaSimbolos.Tipos.TipoBool;
import TablaSimbolos.Tipos.TipoChar;
import TablaSimbolos.Tipos.TipoClase;
import TablaSimbolos.Tipos.TipoInt;
import TablaSimbolos.Tipos.TipoNull;
import TablaSimbolos.Tipos.TipoString;
import TablaSimbolos.Tipos.TipoVoid;
import exceptions.LexicalError;
import exceptions.SemanticException;
import exceptions.SyntaxError;

public class SyntacticalAnalyzer {

        private Token lookAhead;
        private LexicalAnalyzer lexAnalyzer;

        public SyntacticalAnalyzer(LexicalAnalyzer lexAnalyzer) {
                this.lexAnalyzer = lexAnalyzer;
        }
        
        private Token match(String name) throws LexicalError, SyntaxError{
                if (lookAhead.getName().equals(name)){
                		Token k=lookAhead;
                        lookAhead = lexAnalyzer.getToken();
                        return k;
                }        
                else
                        throw new SyntaxError(lookAhead.getLine(), "", name,
                                        lookAhead.getLexema());

        }

        public void analyze() {
    		try {
    			lookAhead = lexAnalyzer.getToken();
    			S();
    			System.out.println("El proceso de compilacion ha finalizado exitosamente.");
    		} catch (LexicalError e) {
    			System.out.println(e.getMessage());
    		} catch (SyntaxError e2) {
    			System.out.println(e2.getMessage());
    		} catch (SemanticException e3) {
    			System.out.println(e3.getMessage());
    		}
    	}
        
        private void S() throws LexicalError, SyntaxError, SemanticException {
    		TS.ts();
    		inicial();
    		TS.ts().controlDeclaracion();
    		TS.ts().controlSentencias();
    	}

        private void inicial() throws LexicalError, SyntaxError,SemanticException {
                clase();
                inicialP();
        }

        private void inicialP() throws LexicalError, SyntaxError, SemanticException {
                if (lookAhead != null && !lookAhead.getName().equals("EOF"))
                        inicial();
        }

        private void clase() throws LexicalError, SyntaxError,SemanticException {
        	match("class");
    		Token kid = match("id");
    		Clase clase = TS.ts().addClass(kid);
    		claseP(clase);
    		if (clase.getConstructor() == null)
    			clase.setConstructor(new Token("id",0,clase.getClassID()));

        }

        private void claseP(Clase clase) throws LexicalError, SyntaxError,SemanticException {
        	switch (lookAhead.getName()) {
        	case "extends":
        		Token ancestro = herencia();
        		clase.setAncestro(ancestro);
        		break;
        	case "{":
        		clase.setAncestro(new Token("id",0,"Object"));
        		break;
        	}
        	match("{");
        	miembroL(clase);
        	match("}");
        }

        private void miembroL(Clase clase) throws LexicalError, SyntaxError,SemanticException {
                switch (lookAhead.getName()) {
                case "varinst":
                case "id":
                case "static":
                case "dynamic":
                        miembro(clase);
                        miembroL(clase);
                        break;
                }
        }

        private Token herencia() throws LexicalError, SyntaxError {
    		match("extends");
    		return match("id");
    	}

        private void miembro(Clase clase) throws LexicalError, SyntaxError,SemanticException {
                switch (lookAhead.getName()) {
                case "varinst":
                	atributo(clase, null);
        			break;
                case "id":
                	ctor(clase);
        			break;
                case "static":
                case "dynamic":
                	metodo(clase);
        			break;
                default:
                        throw new SyntaxError(lookAhead.getLine(), "",
                                        "{varinst,id,static,dynamic}", lookAhead.getLexema());
                }

        }

        private void atributo(Clase clase, Metodo metodo) throws LexicalError, SyntaxError, SemanticException {
                match("varinst");
                Tipo tipo = tipo();
                listaDecVars(tipo, clase, metodo,false);
                match(";");
        }

        private void metodo(Clase clase) throws LexicalError, SyntaxError, SemanticException {
        		String mod = modMetodo();
        		Tipo tipo = tipoMetodo();
        		Token kid = match("id");
        		Metodo metodo = clase.addMetodo(mod, kid, tipo);
        		argsFormales(metodo);
        		Bloque bloque = bloque(metodo,clase); 
        		metodo.setBloque(bloque);
        }

        private void ctor(Clase clase) throws LexicalError, SyntaxError,SemanticException {
        		Token kid = match("id");
        		Metodo constructor = clase.setConstructor(kid);
        		argsFormales(constructor);
        		Bloque bloque = bloque(constructor,clase); 
        		constructor.setBloque(bloque);
        }

        private void argsFormales(Metodo metodo) throws LexicalError, SyntaxError,SemanticException {
                match("(");
                argsFormalesP(metodo);
        }

        private void argsFormalesP(Metodo metodo) throws LexicalError, SyntaxError,SemanticException {
                switch (lookAhead.getName()) {
                case ")":
                        match(")");
                        break;
                default:
                        listaArgsFormales(metodo, 0);
                        match(")");
                }
        }

        private void listaArgsFormales(Metodo metodo, int indice) throws LexicalError, SyntaxError, SemanticException {
        		retArgFormal ret1 = argFormal();
        		metodo.addArgFormal(ret1.k, ret1.tipo, indice);
        		listaArgsFormalesP(metodo, indice+1);
        }

        private void listaArgsFormalesP(Metodo metodo, int indice) throws LexicalError, SyntaxError, SemanticException {
                switch (lookAhead.getName()) {
                case ",":
                        match(",");
                        listaArgsFormales(metodo, indice);
                        break;
                }
        }

        private retArgFormal argFormal() throws LexicalError, SyntaxError {
        		Tipo tipo = tipo();
        		Token kid = match("id");
        		return new retArgFormal(kid, tipo);

        }

        private String modMetodo() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "static":
                        match("static");
                        return "static";
                case "dynamic":
                        match("dynamic");
                        return "dynamic";
                default:
                		throw new SyntaxError(lookAhead.getLine(), "", "{static, dynamic}",
        					lookAhead.getLexema());
                }

        }

        private Tipo tipoMetodo() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "void":
                        match("void");
                        return TipoVoid.instance();
                default:
                		return tipo();
                }

        }

        private Tipo tipo() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "id":
                		return new TipoClase(match("id"));
                default:
                		return tipoPrimitivo();
                }

        }

        private Tipo tipoPrimitivo() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "boolean":
                        match("boolean");
                        return TipoBool.instance();
                case "char":
                        match("char");
                        return TipoChar.instance();
                case "int":
                        match("int");
                        return TipoInt.instance();
                case "String":
                        match("String");
                        return TipoString.instance();
                default:
                        throw new SyntaxError(lookAhead.getLine(), "",
                                        "{boolean,char,int,String}", lookAhead.getLexema());
                }

        }

        private void listaDecVars(Tipo tipo, Clase clase, Metodo metodo,boolean esVarlocal) throws LexicalError, SyntaxError, SemanticException {
        		Token kid = match("id");
        		
        		if (!esVarlocal)
        			clase.addAtributoInst(kid, tipo);
        		else
        			metodo.addVarLocal(kid, tipo);

        		listaDecVarsP(tipo, clase, metodo,esVarlocal);
        }

        private void listaDecVarsP(Tipo tipo, Clase clase, Metodo metodo, boolean esVarlocal) throws LexicalError, SyntaxError, SemanticException {
                switch (lookAhead.getName()) {
                case ",":
                        match(",");
                        listaDecVars(tipo, clase, metodo,esVarlocal);
                }
        }

        private Bloque bloque(Metodo metodo, Clase clase) throws LexicalError, SyntaxError, SemanticException {
        		Token k=match("{");
        		Bloque bloque = new Bloque(k);
        		sentenciaL(bloque,metodo,clase);
                match("}");
                return bloque;
        }

        private void sentenciaL(Bloque bloque,Metodo metodo, Clase clase) throws LexicalError, SyntaxError, SemanticException {
        	switch (lookAhead.getName()) {
    		case ";":
    		case "id":
    		case "(":
    		case "if":
    		case "while":
    		case "for":
    		case "{":
    		case "return":
    		case "varlocal":
    			NodoSentencia sent = null;
    			sent=sentencia(metodo,clase);
    			if(sent!=null)
    				bloque.addSent(sent);
    			sentenciaL(bloque,metodo,clase);
    		}
        }

        private NodoSentencia sentencia(Metodo metodo,Clase clase) throws LexicalError, SyntaxError, SemanticException {
        		NodoSentencia sent, sentP;
        		NodoExpresion expr;
        		NodoAsignacion asig, asig1;
        		Token k;
                switch (lookAhead.getName()) {
                case ";":
                		k =match(";");
                		return new NodoSenDelim(k);
                case "id":
                		sent = asignacion();
                		match(";");
                		return sent;
                case "(":
                		sent = sentenciaSimple();
                		match(";");
                		return sent;
                case "if":
                		match("if");
                		match("(");
                		expr = expresion();
                		match(")");
                		sent = sentencia(metodo,clase);
                		sentP = sentenciaP(metodo,clase);
                		if (sentP != null)
                			return new NodoIf(expr, sent, sentP);
                		else
                			return new NodoIf(expr, sent);
                case "while":
                		match("while");
                		match("(");
                		expr = expresion();
                		match(")");
                		sent = sentencia(metodo,clase);
                		return new NodoWhile(expr, sent);
                case "varlocal": 
                        match("varlocal");
                        Tipo t=tipo();
                        listaDecVars(t,clase,metodo,true); 
                        match(";");
                        break; 
                case "for":
                        match("for");
                        match("(");
                        asig = asignacion();
                        match(";");
                        expr = expresion();
                        match(";");
                        asig1 = asignacion();
                        match(")");
                        sent = sentencia(metodo,clase);
                        return new NodoFor(asig, expr, asig1, sent);
                case "{":
                	return new NodoBloque(bloque(metodo,clase));
                case "return":
                		match("return");
                		return sentenciaPP();
                default:
                	throw new SyntaxError(lookAhead.getLine(), "",
            				"{}", lookAhead.getLexema());
                }
				return null;
        }

        private NodoSentencia sentenciaP(Metodo metodo,Clase clase) throws LexicalError, SyntaxError, SemanticException {
        		switch (lookAhead.getName()) {
        		case "else":
        			match("else");
        			return sentencia(metodo,clase);
        		}
        		return null;
        }

        private NodoSentencia sentenciaPP() throws LexicalError, SyntaxError {
        		Token k;
                switch (lookAhead.getName()) {
                case ";":
                		k=match(";");
                		return new NodoReturn(k);
                default:
                		NodoExpresion expr = expresion();
                		k=match(";");
                		return new NodoReturn(k,expr);
                }
        }

               
        private NodoAsignacion asignacion() throws LexicalError, SyntaxError, SemanticException {
                NodoId nodo=ladoIzquierdo(); 
                match("=");
                NodoExpresion expr = expresion();
        		return new NodoAsignacion(nodo, expr); 
        }

        private NodoId ladoIzquierdo() throws LexicalError, SyntaxError { 
                Token t=match("id");
                NodoId nodo= new NodoId(t);
                NodoIdEncadenado nodoEncadenado=idEncadenado();
                	if (nodoEncadenado!=null) 
                		nodo.setEncadenado(nodoEncadenado);
                return nodo;
        }
        
        private NodoIdEncadenado idEncadenado() throws LexicalError, SyntaxError{
        	switch (lookAhead.getName()) {
            case ".":
            	match(".");
            	Token t= match("id");
            	NodoIdEncadenado n =new NodoIdEncadenado(t);
            	NodoIdEncadenado n2=idEncadenado();
            	n.setEncadenado(n2);
     
            	return n;
            	
            }
        	return null; 
       	
        }

        private NodoSentencia sentenciaSimple() throws LexicalError, SyntaxError {
    		match("(");
    		NodoExpresion expr = expresion();
    		match(")");
    		return new NodoSenSimple(expr);
    	}

        private NodoExpresion expresion() throws LexicalError, SyntaxError {
    		NodoExpresion exprH = expr5();
    		return exprP(exprH);
    	}

        private NodoExpresion exprP(NodoExpresion exprH) throws LexicalError,
			SyntaxError {
        	switch (lookAhead.getName()) {
        	case "||":
        		Token k = match("||");
        		NodoExpresion expr = expr5();
        		NodoExpresion exprH1 = new NodoExpresionBinaria(exprH, expr, k);
        		return exprP(exprH1);
        	default:
        		return exprH;
        	}
        }

        private NodoExpresion expr5() throws LexicalError, SyntaxError {
    		NodoExpresion exprH = expr4();
    		return expr5P(exprH);
    	}

        private NodoExpresion expr5P(NodoExpresion exprH) throws LexicalError,
			SyntaxError {
        	switch (lookAhead.getName()) {
        	case "&&":
        		Token k = match("&&");
        		NodoExpresion expr = expr4();
        		NodoExpresion exprH1 = new NodoExpresionBinaria(exprH, expr, k);
        		return expr5P(exprH1);
        	default:
        		return exprH;
        	}

        }

        private NodoExpresion expr4() throws LexicalError, SyntaxError {
    		NodoExpresion exprH = expr3();
    		return expr4P(exprH);
    	}

        private NodoExpresion expr4P(NodoExpresion exprH) throws LexicalError,
			SyntaxError {
        	NodoExpresion expr, exprH1;
        	Token k ;
        	switch (lookAhead.getName()) {
        	case "!=":
        		k = match("!=");
        		expr = expr3();
        		exprH1 = new NodoExpresionBinaria(exprH, expr, k);
        		return expr4P(exprH1);
        	case "==":
        		k=match("==");
        		expr = expr3();
        		exprH1 = new NodoExpresionBinaria(exprH, expr, k);
        		return expr4P(exprH1);
        	default:
        		return exprH;
        	}
        }

        private NodoExpresion expr3() throws LexicalError, SyntaxError {
    		NodoExpresion exprH = expr2();
    		return expr3P(exprH);
    	}

        private NodoExpresion expr3P(NodoExpresion exprH) throws LexicalError,
			SyntaxError {
        	NodoExpresion expr;
        	Token k;
        	switch (lookAhead.getName()) {
        	case ">=":
        		k=match(">=");
        		expr = expr2();
        		return new NodoExpresionBinaria(exprH, expr, k);
        	case "<=":
        		k=match("<=");
        		expr = expr2();
        		return new NodoExpresionBinaria(exprH, expr, k);
        	case ">":
        		k=match(">");
        		expr = expr2();
        		return new NodoExpresionBinaria(exprH, expr, k);
        	case "<":
        		k=match("<");
        		expr = expr2();
        		return new NodoExpresionBinaria(exprH, expr, k);
        	default:
        		return exprH;
        	}
        }

        private NodoExpresion expr2() throws LexicalError, SyntaxError {
    		NodoExpresion exprH = expr1();
    		return expr2P(exprH);
    	}

        private NodoExpresion expr2P(NodoExpresion exprH) throws LexicalError,
			SyntaxError {
        	NodoExpresion expr, exprH1;
        	Token k;
        	switch (lookAhead.getName()) {
        	case "-":
   	     		k=match("-");
   	     		expr = expr1();
   	     		exprH1 = new NodoExpresionBinaria(exprH, expr, k);
   	     		return expr2P(exprH1);
        	case "+":
        		k=match("+");
        		expr = expr1();
        		exprH1 = new NodoExpresionBinaria(exprH, expr, k);
        		return expr2P(exprH1);
        	default:
        		return exprH;
        	}

        }

        private NodoExpresion expr1() throws LexicalError, SyntaxError {
    		NodoExpresion exprH = exprUnaria();
    		return expr1P(exprH);
    	}
        
        private NodoExpresion expr1P(NodoExpresion exprH) throws LexicalError,
			SyntaxError {
        	NodoExpresion expr, exprH1;
        	Token k;
        	switch (lookAhead.getName()) {
        	case "*":
        		k=match("*");
        		expr = exprUnaria();
        		exprH1 = new NodoExpresionBinaria(exprH, expr, k);
        		return expr1P(exprH1);
        	case "/":
        		k=match("/");
        		expr = exprUnaria();
        		exprH1 = new NodoExpresionBinaria(exprH, expr, k);
        		return expr1P(exprH1);
        	case "%":
        		k=match("%");
        		expr = exprUnaria();
        		exprH1 = new NodoExpresionBinaria(exprH, expr, k);
        		return expr1P(exprH1);
        	default:
        		return exprH;
        	}
        }

        private NodoExpresion exprUnaria() throws LexicalError, SyntaxError {
    		NodoExpresion expr;
    		Token k;
    		switch (lookAhead.getName()) {
    		case "+":
    			k=match("+");
    			expr = exprUnaria();
    			return new NodoExpUnaria(expr, k);
    		case "-":
    			k=match("-");
    			expr = exprUnaria();
    			return new NodoExpUnaria(expr, k);
    		case "!":
    			k=match("!");
    			expr = exprUnaria();
    			return new NodoExpUnaria(expr, k);
    		default:
    			return primario();
    		}
    	}

        

        private NodoExpPrimario primario() throws LexicalError, SyntaxError {
    		
        	
        	Encadenado llamadas;
    		NodoExpresion expr;
    		List<NodoExpresion> args;
    		Token kid;
    		switch (lookAhead.getName()) {
    		case "this":
    			Token k=match("this");
    			
    			return new NodoPrimThis(k);
    		case "null":
    		case "true":
    		case "false":
    		case "intLiteral":
    		case "charLiteral":
    		case "stringLiteral":
    			
    			return literal();
    		case "(":
    			match("(");
    			expr = expresion();
    			match(")");
    			llamadas = llamadaL();
    			
    			return new NodoPrimParentizado(expr,llamadas);
    		case "new":
    			match("new");
    			kid = match("id");
    			args = argsActuales();
    			llamadas = llamadaL();
    			
    			return new NodoPrimNew(kid, args, llamadas);
    		case "id":
    			kid = match("id");
    			
    			return primarioP(kid);
    		default:
    			throw new SyntaxError(lookAhead.getLine(), "",
    					"{this, literal, (, new, id}", lookAhead.getLexema());
    		}

    	}

       
        private NodoExpPrimario primarioP(Token k) throws LexicalError,
		SyntaxError {
        	
        	Encadenado llamadas;
        	List<NodoExpresion> args;
        	switch (lookAhead.getName()) {
        	case "(":
        		args = argsActuales();
        		llamadas = llamadaL();
        		return new NodoLlamadaDirecta(k,args,llamadas);
        		
        	default:
        		llamadas = llamadaL();
        		return new NodoIdDirecto(k,llamadas);
        		
        	}

        }
        
        
        private Encadenado llamadaL() throws LexicalError, SyntaxError {
    		switch (lookAhead.getName()) {
    		case ".":
    			
    			 Encadenado encadenado = llamada();
    			
    			 Encadenado en2= llamadaL();
    			
    			encadenado.setEncadenado(en2); 
    			
    			return encadenado;
    		default:
    			
    			return null; 
    		}
    	}
        
        private Encadenado llamada() throws LexicalError, SyntaxError {
                match(".");
                Token kid = match("id");
                List<NodoExpresion> args =argsOpcionales();
               if (args== null) 
            	   return new NodoIDEncadenadoDer(kid,null);
                else 
                	return new LlamadaEncadenada(kid,null,args);
                
        }

        private List<NodoExpresion> argsOpcionales() throws LexicalError, SyntaxError {
        	 switch (lookAhead.getName()) {
             case "(":
                     return argsActuales();
			
		}
			return null; 
        }

        private NodoExpPrimario literal() throws LexicalError, SyntaxError {
    		switch (lookAhead.getName()) {
    		case "null":
    			return new NodoPrimLiteral(match("null"), TipoNull.instance());
    		case "true":
    			return new NodoPrimLiteral(match("true"), TipoBool.instance());
    		case "false":
    			return new NodoPrimLiteral(match("false"), TipoBool.instance());
    		case "intLiteral":
    			return new NodoPrimLiteral(match("intLiteral"), TipoInt.instance());
    		case "charLiteral":
    			return new NodoPrimLiteral(match("charLiteral"), TipoChar.instance());
    		case "stringLiteral":
    			return new NodoPrimLiteral(match("stringLiteral"), TipoString.instance());
    		default:
    			throw new SyntaxError(lookAhead.getLine(), "",
    					"{null,true,false,number,character,String}",
    					lookAhead.getLexema());
    		}
    		
    	}

        private List<NodoExpresion> argsActuales() throws LexicalError, SyntaxError {
    		match("(");
    		return argsActualesP();
    	}

        private List<NodoExpresion> argsActualesP() throws LexicalError,
			SyntaxError {
        	switch (lookAhead.getName()) {
        	case ")":
        		match(")");
        		return new LinkedList<NodoExpresion>();
        	default:
        		List<NodoExpresion> args = listaExps();
        		match(")");
        		return args;
        	}
        }

        private List<NodoExpresion> listaExps() throws LexicalError, SyntaxError {
    		NodoExpresion expr = expresion();
    		List<NodoExpresion> args = listaExpsP();
    		args.add(0,expr);
    		return args;
    	}

    	private List<NodoExpresion> listaExpsP() throws LexicalError, SyntaxError {
    		switch (lookAhead.getName()) {
    		case ",":
    			match(",");
    			return listaExps();
    		default:
    			return new LinkedList<NodoExpresion>();
    		}
    	}

    	/* RETORNOS */
    	private class retArgFormal {
    		public Token k;
    		public Tipo tipo;

    		public retArgFormal(Token k, Tipo tipo) {
    			this.k = k;
    			this.tipo = tipo;
    		}
    	}

}
