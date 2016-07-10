package minijava;

import exceptions.LexicalError;
import exceptions.SyntaxError;

public class SyntacticalAnalyzer {

        private Token lookAhead;
        private LexicalAnalyzer lexAnalyzer;

        public SyntacticalAnalyzer(LexicalAnalyzer lexAnalyzer) {
                this.lexAnalyzer = lexAnalyzer;
        }

        private void match(String name) throws LexicalError, SyntaxError,
                        SyntaxError {
                if (lookAhead == null)
                        throw new SyntaxError("", name, "EOF");
                if (lookAhead.getName().equals(name))
                        lookAhead = lexAnalyzer.getToken();
                else
                        throw new SyntaxError(lookAhead.getLine(), "", name,
                                        lookAhead.getLexema());

        }

        public void analyze() {
                try {
                        lookAhead = lexAnalyzer.getToken();
                        inicial();
                        System.out
                                        .println("The Syntax Analysis has finished successfully.");
                } catch (LexicalError e) {
                        System.out.println(e.getMessage());
                } catch (SyntaxError e2) {
                        System.out.println(e2.getMessage());
                }
        }

        private void inicial() throws LexicalError, SyntaxError {
                clase();
                inicialP();
        }

        private void inicialP() throws LexicalError, SyntaxError {
                if (lookAhead != null && !lookAhead.getName().equals("EOF"))
                        inicial();
        }

        private void clase() throws LexicalError, SyntaxError {
                match("class");
                match("id");
                claseP();

        }

        private void claseP() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "extends":
                        herencia();
                        match("{");
                        miembroL();
                        match("}");
                        break;
                case "{":
                        match("{");
                        miembroL();
                        match("}");
                        break;
                default:
                        throw new SyntaxError(lookAhead.getLine(), "", "extends or { ",
                                        lookAhead.getLexema());
                }

        }

        private void miembroL() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "varinst":
                case "id":
                case "static":
                case "dynamic":
                        miembro();
                        miembroL();
                        break;
                }
        }

        private void herencia() throws LexicalError, SyntaxError {
                match("extends");
                match("id");
        }

        private void miembro() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "varinst":
                        atributo();
                        break;
                case "id":
                        ctor();
                        break;
                case "static":
                case "dynamic":
                        metodo();
                        break;
                default:
                        throw new SyntaxError(lookAhead.getLine(), "",
                                        "{varinst,id,static,dynamic}", lookAhead.getLexema());
                }

        }

        private void atributo() throws LexicalError, SyntaxError {
                match("varinst");
                tipo();
                listaDecVars();
                match(";");
        }

        private void metodo() throws LexicalError, SyntaxError {
                modMetodo();
                tipoMetodo();
                match("id");
                argsFormales();
                bloque();
        }

        private void ctor() throws LexicalError, SyntaxError {
                match("id");
                argsFormales();
                bloque();
        }

        private void argsFormales() throws LexicalError, SyntaxError {
                match("(");
                argsFormalesP();
        }

        private void argsFormalesP() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case ")":
                        match(")");
                        break;
                default:
                        listaArgsFormales();
                        match(")");
                }
        }

        private void listaArgsFormales() throws LexicalError, SyntaxError {
                argFormal();
                listaArgsFormalesP();
        }

        private void listaArgsFormalesP() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case ",":
                        match(",");
                        listaArgsFormales();
                        break;
                }
        }

        private void argFormal() throws LexicalError, SyntaxError {
                tipo();
                match("id");

        }

        private void modMetodo() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "static":
                        match("static");
                        break;
                case "dynamic":
                        match("dynamic");
                        break;
                default:
                        System.out.println("Error Sintactico");
                }

        }

        private void tipoMetodo() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "void":
                        match("void");
                        break;
                default:
                        tipo();
                }

        }

        private void tipo() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "id":
                        match("id");
                        break;
                default:
                        tipoPrimitivo();
                }

        }

        private void tipoPrimitivo() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "boolean":
                        match("boolean");
                        break;
                case "char":
                        match("char");
                        break;
                case "int":
                        match("int");
                        break;
                case "String":
                        match("String");
                        break;
                default:
                        throw new SyntaxError(lookAhead.getLine(), "",
                                        "{boolean,char,int,String}", lookAhead.getLexema());
                }

        }

        private void listaDecVars() throws LexicalError, SyntaxError {
                match("id");
                listaDecVarsP();
        }

        private void listaDecVarsP() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case ",":
                        match(",");
                        listaDecVars();
                }
        }

        private void bloque() throws LexicalError, SyntaxError {
                match("{");
                sentenciaL();
                match("}");
        }

        private void sentenciaL() throws LexicalError, SyntaxError {
                if (lookAhead != null) {
                        switch (lookAhead.getName()) {
                        case "}":
                                break;
                        default:
                                sentencia();
                                sentenciaL();
                        }
                }
        }

        private void sentencia() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case ";":
                        match(";");
                        break;
                case "id":
                        asignacion();
                        match(";");
                        break;
                case "(":
                        sentenciaSimple();
                        match(";");
                        break;
                case "if":
                        match("if");
                        match("(");
                        expresion();
                        match(")");
                        sentencia();
                        sentenciaP();
                        break;
                case "while":
                        match("while");
                        match("(");
                        expresion();
                        match(")");
                        sentencia();
                        break;
                case "varlocal":
                        match("varlocal");
                        tipo();
                        listaDecVars();
                        match(";");
                        break;
                case "for":
                        match("for");
                        match("(");
                        asignacion();
                        match(";");
                        expresion();
                        match(";");
                        asignacion();
                        match(")");
                        sentencia();
                        break;
                case "{":
                        bloque();
                        break;
                case "return":
                        match("return");
                        sentenciaPP();
                        break;
                default:
                        throw new SyntaxError(lookAhead.getLine(), "",
                                        "{;,id,(,if,while,for,{,return}", lookAhead.getLexema());
                }
        }

        private void sentenciaP() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "else":
                        match("else");
                        sentencia();
                }
        }

        private void sentenciaPP() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case ";":
                        match(";");
                        break;
                default:
                        expresion();
                        match(";");
                }
        }

        private void asignacion() throws LexicalError, SyntaxError {
                ladoIzquierdo();
                match("=");
                expresion();
        }

        private void ladoIzquierdo() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "id":
                        match("id");
                        if (lookAhead.getName().equals(".")) {
                                match(".");
                                ladoIzquierdo();
                        }
                        break;
                }

        }

        private void sentenciaSimple() throws LexicalError, SyntaxError {
                match("(");
                expresion();
                match(")");
        }

        private void expresion() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "+":
                        match("+");
                        primario();
                        exprP();
                        break;
                case "-":
                        match("-");
                        primario();
                        exprP();
                        break;
                case ("!"):
                        match("!");
                        primario();
                        exprP();
                        break;
                default:
                        expr5();
                        exprP();
                }
        }

        private void exprP() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "||":
                        match("||");
                        expr5();
                        exprP();
                }
        }

        private void expr5() throws LexicalError, SyntaxError {
                expr4();
                expr5P();
        }

        private void expr5P() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "&&":
                        match("&&");
                        expr4();
                        expr5P();
                }

        }

        private void expr4() throws LexicalError, SyntaxError {
                expr3();
                expr4P();
        }

        private void expr4P() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "!=":
                        match("!=");
                        expr3();
                        expr4P();
                        break;
                case "==":
                        match("==");
                        expr3();
                        expr4P();
                }
        }

        private void expr3() throws LexicalError, SyntaxError {
                expr2();
                expr3P();
        }

        private void expr3P() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "<":
                        match("<");
                        expr2();
                        break;
                case ">":
                        match(">");
                        expr2();
                        break;

                case "<=":
                        match("<=");
                        expr2();
                        break;
                case ">=":
                        match(">=");
                        expr2();
                        break;
                }
        }

        private void expr2() throws LexicalError, SyntaxError {
                expr1();
                expr2P();
        }

        private void expr2P() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "-":
                        match("-");
                        expr1();
                        expr2P();
                        break;
                case "+":
                        match("+");
                        expr1();
                        expr2P();
                }

        }

        private void expr1() throws LexicalError, SyntaxError {
                exprUnaria();
                expr1P();
        }

        private void exprUnaria() throws LexicalError, SyntaxError {
                switch (lookAhead.getLexema()) {
                case "-":
                        match("-");
                        exprUnaria();
                        break;
                case "+":
                        match("+");
                        exprUnaria();
                        break;
                case "!":
                        match("!");
                        exprUnaria();
                        break;
                default:
                        primario();
                }

        }

        private void expr1P() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "*":
                        match("*");
                        primario();
                        expr1P();
                        break;
                case "/":
                        match("/");
                        primario();
                        expr1P();
                        break;
                case "%":
                        match("%");
                        primario();
                        expr1P();
                }
        }

        private void primario() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "this":
                        match("this");
                        break;
                case "(":
                        match("(");
                        expresion();
                        match(")");
                        llamadaL();
                        break;
                case "id":
                        match("id");
                        primarioP();
                        break;
                case "new":
                        match("new");
                        match("id");
                        argsActuales();
                        llamadaL();
                        break;
                default:
                        literal();
                }

        }

        private void llamadaL() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case ".":
                        llamada();
                        llamadaL();
                }

        }

        private void primarioP() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "(":
                        argsActuales();
                        llamadaL();
                        break;
                default:
                        llamadaL();
                }

        }

        private void llamada() throws LexicalError, SyntaxError {
                match(".");
                match("id");
                argsActuales();
        }

        private void literal() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case "null":
                        match("null");
                        break;
                case "true":
                        match("true");
                        break;
                case "false":
                        match("false");
                        break;
                case "intLiteral":
                        match("intLiteral");
                        break;
                case "charLiteral":
                        match("charLiteral");

                        break;
                case "stringLiteral":
                        match("stringLiteral");
                        break;
                default:
                        throw new SyntaxError(lookAhead.getLine(), "",
                                        "{null,true,false,number,character,String}",
                                        lookAhead.getLexema());
                }
        }

        private void argsActuales() throws LexicalError, SyntaxError {
                match("(");
                argsActualesP();
        }

        private void argsActualesP() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case ")":
                        match(")");
                        break;
                default:
                        listaExps();
                        match(")");
                }
        }

        private void listaExps() throws LexicalError, SyntaxError {
                expresion();
                listaExpsP();
        }

        private void listaExpsP() throws LexicalError, SyntaxError {
                switch (lookAhead.getName()) {
                case ",":
                        match(",");
                        listaExps();
                }
        }

}

