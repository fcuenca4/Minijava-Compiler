/*
	Check: Si el tipo de un atributo de instancia es una clase, dicha clase debe estar declarada.
	Expected: Error semantico en la linea: 17. La clase D no ha sido declarada.
*/

class A{
	static void main(){
	}
}

class B{

}

class C extends A
{
	varinst D atributo;
}