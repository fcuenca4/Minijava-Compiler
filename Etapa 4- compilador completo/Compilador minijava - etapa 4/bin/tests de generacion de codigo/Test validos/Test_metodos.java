
class A{ //clase actual A y metodo actual m1

	varinst B a1;
	A(){
	a1= new B();
	}
	dynamic void m1(int p1){
		varlocal int a;
		a=getB().m2(p1);
		(System.printIln(a));
	}
	

	
	dynamic B getB(){
		return a1;
	}
	
	static void main(){
		varlocal A a;
		a= new A();
		(a.m1(1));
	}
	
	static A metodo(A par){
		return par;
	}
	
}

class B extends A{
	varinst int a2;
	
	B(){
	a2=1;
	}
	
	dynamic int m2(int j){
	return j;
	}
}