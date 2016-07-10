/*
 * @ Test: 18
 * 
 * @ Objetivo:
 *	Se espera que el analizador sintactico reconozca 
 * que falta la expresion a ser evaluada como condicion
 * de corte en el while.
 */
class clase
{
	dynamic void metodo(){
	varlocal int x;
	x = 0;
		while(/* expresion */)
			x = x + 1;
	}
	
}
