
class A{ 


	static void main(){
	varlocal int a;
	varlocal int b;
	a=2;
	b=6;
	(System.printSln("Ingrese un num:"));
	a = System.read();
	(System.read());
	(System.printSln("Ingrese otro num:"));
	(System.read());
	b = System.read();
	
	(System.printSln("El primer numero ingresado es:"));
	(System.printIln(a-48)); //
	(System.printSln("El segundo numero ingresado es:"));
	(System.printIln(b-48)); //esto no lo hace bien
	
	(System.printSln("Imprimo el nro del metodo ma():"));
	(System.printIln(ma()));
	}
	static int ma(){
		return 4;
	} 
}
