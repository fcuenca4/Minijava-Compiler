/*
La intencion de dicho test es probar el correcto funcionamiento del for
*/
class A{
	static void met1(int j)
	{	varlocal int i;	
		for(i = 0; i < j; i=i+1){
			(System.printI(i));		
			(System.printS("-"));
		}
		(System.printI(j));
	}

	static void main()
			
	{	varlocal int a;
		(System.printSln("FOR:"));
		(met1(5));
	}
}
