/*
La intencion de dicho test es probar el correcto funcionamiento del while y el if.
Salida esperada:
*if true: Met1: 10
*else true: Met1: 10
*if-else false: Met1: 10
*/
class A{
	static int met1(int i){
		if(i > 10) while(i > 10) i = i-1;
		else {
			if(i < 10) while(i < 10) i = i+1;		
		}		
		return i;
	}

	static void main()
		
	{	varlocal int a;	
		(System.printS("* if true: Met1: "));
		a = met1(15);
		(System.printIln(a));
		(System.printS("* else true: Met1: "));
		a = met1(5);
		(System.printIln(a));
		(System.printS("* if-else false: Met1: "));		
		a = met1(10);		
		(System.printIln(a));
	}
}
