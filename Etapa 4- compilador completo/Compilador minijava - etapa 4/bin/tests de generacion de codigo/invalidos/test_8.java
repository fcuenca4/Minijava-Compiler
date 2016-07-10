/*
	Check: Codigo inalcanzable.
	Expected: Error semantico en la linea: 18. Codigo inalcanzable.

*/

class A{
	static void main(){
		
		
	}
	
	static int m1()
	{
		varlocal int i;
		while (i<10){
			return 1;
			i=i+1;
		}
	}
}