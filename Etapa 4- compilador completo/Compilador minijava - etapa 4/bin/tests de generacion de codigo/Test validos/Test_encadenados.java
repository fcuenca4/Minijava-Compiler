class A{
varinst int m;

dynamic int j(){
	return 5;
}
}
class B {
 varinst A a1;
 
 static int a(){
	return 4;
 }
 
 
 
}

class C{
 
 
 static void main(){
	varlocal B b1;
	b1= new  B();
	b1.a1 = new A();
	varlocal int k;
	(System.printSln("Resultado llamada dinamica:"));
	(System.printIln(b1.a1.j())); //llamada dinamica
	(System.printSln("Resultado llamada estatica:"));
	(System.printIln(B.a())); //llamada estatica
	
	
 }
 

 
 
}