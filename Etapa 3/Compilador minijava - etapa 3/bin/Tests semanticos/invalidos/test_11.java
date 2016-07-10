/*
	Check: Llamadas sean correctas.
	Expected: Error semantico en la linea: 13. Se esperaba que el metodo m3 de la clase C sea dinamico, y se encontro que es estatico.

*/

class A{
	static void main()
	{
		varlocal A a;
		varlocal int i;
		a = new A();
		i = a.m1(1).m2('1').m3();
	}
	
	dynamic B m1(int a){
		return new B(a);
	}
	
}
class B{
	varinst int aa;
	B(int a){
		aa=a;
	}
	
	dynamic C m2(char c){
		return new C(c);
	}
	
}
class C{
	varinst char cc;
	C(char c){
		cc = c;
	}
	
	static int m3(){
		return 1;
	}
}