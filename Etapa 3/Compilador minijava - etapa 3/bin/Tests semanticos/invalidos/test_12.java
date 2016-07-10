/*
	Check: que la llamada a THIS no se produzca dentro de un metodo estatico.
	Expected: Error semantico en la linea: 13. No es posible hacer referencia a this en un metodo estatico.

*/

class A{
	static void main()
	{
		varlocal A a;
		varlocal int i;
		a = new A();
		i = (((new A()))).m1(1).m2(this).m3();
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
	
	dynamic C m2(A c){
		return new C(c);
	}
	
}
class C{
	varinst A cc;
	C(A c){
		cc = c;
	}
	
	dynamic int m3(){
		return 1;
	}
}