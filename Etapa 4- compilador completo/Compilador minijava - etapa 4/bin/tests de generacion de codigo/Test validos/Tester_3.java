
class A{ //clase actual A y metodo actual m1

	
	dynamic void m1(int p1){
		varlocal B a1;
		varlocal A m;
		a1= new B();
		m= new A();
		(System.printSln("Imprimo el parametro:"));
		(System.printIln(p1));
		a1.a2=(4+5); 
		(System.printSln("Imprimo la asignacion al encadenado:"));
		(System.printIln(a1.a2));
		(System.printSln("Imprimo la llamada al metodo:"));
		p1=m4();
		(System.printIln(p1));
		(System.printSln("Imprimo la llamada encadenada:"));
		a1.a2=m.m4();
		(System.printIln(a1.a2)); 
	}
	
	dynamic int m4(){
		return 5;
	}
	
	
	static void main(){
	varlocal B b;
		b= new B();
		(b.m1(3));
	
	}
	
}

class B extends A{
	varinst int a2;
	
	B(){
	a2=0;
	}
	
}