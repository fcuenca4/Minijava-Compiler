/*
	Check: Conformidad de tipos.
	Expected: Error semantico en la linea: 11. El tipo Clase<C> no conforma con el tipo Clase<B>.

*/

class A{
	static void main()
	
	{	varlocal A a1;
		a1 = m1(2,'3', new C(), new C()); 
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
	