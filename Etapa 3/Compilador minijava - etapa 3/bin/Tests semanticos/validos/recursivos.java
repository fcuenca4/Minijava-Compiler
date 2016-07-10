class recursivos
{
	static int fibonacci(int n)
	{
		if(n > 0 && n < 3)
			return 1;
	
		return fibonacci(n - 1) +  fibonacci (n - 2);
	}
	
	static int sumatoria(int n)
	{
		if(n == 0)
			return 0;
		
		return n + sumatoria(n - 1);
	}

	static int factorial(int n)
	{
		if(n == 0)
			return 1;
		
		return n * factorial(n - 1);
	}

	static void main(){
	varlocal int numero;
	varlocal char opcion;
	
		numero = 7;
		
		(System.printIln(fibonacci(numero)));
		(System.printIln(sumatoria(numero)));
		(System.printIln(factorial(numero)));
	}
}

