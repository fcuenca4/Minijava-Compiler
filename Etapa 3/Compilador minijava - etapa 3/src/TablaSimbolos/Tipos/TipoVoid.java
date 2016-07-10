package TablaSimbolos.Tipos;

import minijava.Token;

public class TipoVoid extends Tipo {
        private static TipoVoid instance;

        public static TipoVoid instance() {
                if (instance == null)
                        return new TipoVoid();

                return instance;
        }

        private TipoVoid() {
        }

        public boolean conforma(Tipo c) {
                return c instanceof TipoVoid;
        }

        public String toString() {
                return "void";
        }

        @Override
        public void gen(Token k) {

        }

        public boolean equals(Tipo tipo) {
                return tipo instanceof TipoVoid;

        }

        @Override
        public boolean esTipoClase() {
                // TODO Auto-generated method stub
                return false;
        }
}