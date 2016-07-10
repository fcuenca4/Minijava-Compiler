/*
	Check: Codigo inalcanzable.
	Expected: Error semantico en la linea: 21. Codigo inalcanzable.

*/

class A{
	static void main(){
		
		
	}
	
	static int m1()
	{
		varlocal int i;
		if (true){
			return 1;
		}else
			return 2;
		
		i = i+1;
		
	}
}