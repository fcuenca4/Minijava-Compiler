/*
Test8
Objetivo: Detectar un error en la declaracion de un metodo. 
La declaracion de variables locales es incorrecta. Falta ;

*/

class UnaClase extends Hola {
	varinst TipoVariable n;
	
	UnaClase (){
	
	}
	
	static void Metodo() {
	varlocal String a
	}
	
}