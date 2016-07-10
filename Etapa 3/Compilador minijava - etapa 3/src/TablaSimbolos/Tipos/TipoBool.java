package TablaSimbolos.Tipos;

import minijava.Token;



public class TipoBool extends TipoPrimitivo {
        private static TipoBool instance;

        public static TipoBool instance() {
                if (instance == null)
                        return new TipoBool();

                return instance;
        }

        private TipoBool() {
        }

        public boolean conforma(Tipo c) {
                return c instanceof TipoBool;
        }

        public String toString() {
                return "boolean";
        }

        @Override
        public void gen(Token k) {
                int t = k.getLexema().equals("true") ? 1 : 0;
        }

        @Override
        public boolean equals(Tipo tipo) {
                return tipo instanceof TipoBool;
        }

        @Override
        public boolean esTipoClase() {
                // TODO Auto-generated method stub
                return false;
        }
}