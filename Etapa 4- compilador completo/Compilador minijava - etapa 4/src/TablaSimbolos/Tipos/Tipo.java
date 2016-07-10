package TablaSimbolos.Tipos;

import exceptions.SemanticException;
import minijava.Token;

public abstract class Tipo{

        public abstract boolean conforma(Tipo c) throws SemanticException;

        public abstract void gen(Token k);
        
        public abstract boolean equals(Tipo tipo);
        
        public abstract String toString();
        
        public abstract boolean esTipoClase();

}
