class Math
{
	varinst int _a, _b, _c, _d, _e;
	
	Math(int a, int b, int c, int d, int e)
	{
		_a = a;
		_b = b;
		_c = c;
		_d = d;
		_e = e;
	}
	
	static Math create(int a, int b, int c, int d, int e) 
	{
		return new Math(a, b, c, d, e);
	}
	
	// calcula (a * b + c ^ d)/e
	dynamic int get()
	{
		return div(Math.add(mul(_a,_b), Math.pow(_c, _d)), _e);
	}
		
	static int succ(int a)
	{
		return a + 1;
	}
	
	static int mul(int a, int b)
	{
		if(b == 0)
			return 0;
		else
			return add(a, mul(a, b - 1)); 
	}
	
	static int add(int a, int b)
	{
		if(b == 0)
			return a;
		else
			return succ(add(a, b - 1));
	}

	static int pow(int a, int b)
	{
		if(b == 0)
			return 1;
		else
			return a * pow(a, b - 1);
	}
	
	static int sub(int a, int b)
	{
		return a - b;
	}
	
	static int div(int a, int b)
	{
		return a / b;
	}

}

class Main extends Math
{
	static void main()
	{
		(System.printS("(7 * 5 + 5 ^ 2)/6 = "));
		(System.printIln(Math.create(7,5,5,2,6).get()));
	}

}

