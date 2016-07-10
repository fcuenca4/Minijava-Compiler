/*
 * @ Test: programa_valido.java
 * 
 * @ Objetivo:
 *	Se espera que el analizador sintactico reconozca 
 * que esta clase es valida, es decir, que finalizara
 * su analisis sin la ocurrencia de un error lexico o
 * sintactico.
 */
class Clase 
{
	// Atributos
	varinst boolean b1;
	varinst char c1, c2;
	varinst int i1, i2, i3;
	varinst String s1;
	
	// Constructor
	Clase()
	
	{
		varlocal int l1, l2;
		b1 = (3 * 4) >= (2 * 6) || (3 != 8) && (7 <= 10);
		
		c1 = '1';
		c2 = '\2';
		
		i1 = 11 * 23 + (58/13) % 21;
		i2 = 4/8 + (15*(((16)/23)) * 42);
		i3 = (potencia (i1, i2));
		
		l1 = potencia(i1, 2);
		l2 = potencia(i2, 4);
		
		s1 = "Hola mundo";
	}
	
	// Metodos
	
	dynamic String exec(int x, int y) 
	
	{
		a = 0;
		b = 0;
		
		while( a < x ) {
			b = y;
			a = a + 1;
		}
		
		if(b >= potencia(x, y))
			return "Mayor que potencia";
		else
			return "Menor que potencia";
		
		return b;
	}
	
	
	
	static int potencia(int x, int y)
	
	{
		if(y == 0)
			return 1;
		
		if(x == 0)
			return 0;
		
		pot = 1;
		for(aux = 0; aux < y; aux = aux + 1)
			aux = aux * x;
		
		return x;
	}
	
}

