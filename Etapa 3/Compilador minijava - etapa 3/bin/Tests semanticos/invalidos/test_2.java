/*
	Check: que una clase no se extienda a si misma.
	Expected: Error semantico en la linea: 15. El ancestro C es invalido.
*/

class A{
	static void main(){
	}
}

class B{

}

class C extends C{

}