/*
	Check: Que detecte si un metodo no ha sido declarado.
	Expected: Error semantico en la linea: 11. El metodo m2 no esta declarado en la clase A.

*/

class A{
	static void main()
	{	
		varlocal A a1;
		a1 = m2(2,'3', new C(), new C()); 
	}
	
	static A m1(int d1, char d2, B d3, C d4){
		
		return new A();
	}
}

class B{

}

class C extends A
{
	
}
