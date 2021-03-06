package TablaSimbolos.Tipos;

import minijava.Token;



public class TipoChar extends TipoPrimitivo {
        private static TipoChar instance;

        public static TipoChar instance() {
                if (instance == null)
                        return new TipoChar();

                return instance;
        }

        private TipoChar() {
        }

        public boolean conforma(Tipo c) {
                return c instanceof TipoChar;
        }

        public String toString() {
                return "char";
        }

        @Override
        public void gen(Token k) {
        }

        @Override
        public boolean equals(Tipo tipo) {
                return tipo instanceof TipoChar;
        }

        private String getChar(char c) {
                if (c == '\n')
                        return "\\n";

                if (c == '\t')
                        return "\\t";

                return "" + c;

        }

        @Override
        public boolean esTipoClase() {
                // TODO Auto-generated method stub
                return false;
        }
}