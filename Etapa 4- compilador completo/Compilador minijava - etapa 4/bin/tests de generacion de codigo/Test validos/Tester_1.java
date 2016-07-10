
class A{ 


	static void main(){
	
	varlocal B a1;
	
	a1 = new B();
	a1.a2= new C();
	a1.a2.i=4; 
	(System.printSln("Imprimiendo el valor del encadenado:"));
	(System.printIln(a1.a2.i)); 
 
	}
	
}

class B extends A{
	varinst C a2;
	
	
	
	
}
class C {
	varinst int i;
	
}