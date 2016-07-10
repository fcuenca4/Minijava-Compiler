/*
	Check: que una clase extienda a otra que ya exista.
	Expected: Error semantico en la linea: 15. La clase D no ha sido declarada.
*/

class A{
	static void main(){
	}
}

class B{

}

class C extends D{

}