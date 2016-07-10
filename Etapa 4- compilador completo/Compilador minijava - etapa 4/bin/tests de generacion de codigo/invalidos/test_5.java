/*
	Check: Conformidad de tipos.
	Expected: Error semantico en la linea: 11. El tipo Clase<A> no conforma con el tipo Clase<C>.

*/

class A{
	static void main()
	{	
		varlocal C a1;
		a1 = new A();
	}
}

class B{

}

class C extends A
{
	
}