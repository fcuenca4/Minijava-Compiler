class invertir_numero
{
	 
	static int contarDigitos(int numero)
	
	{	varlocal int cuenta;
		cuenta = 0;
		while(numero > 0) {
			numero = numero / 10;
			cuenta = cuenta + 1;
		}
		
		return cuenta;	
	}

	static int invertir(int numero, int digitos){
	varlocal int cuenta, aux;
	
		cuenta = 0;
		for(aux = 0; aux < digitos; aux = aux + 1) {
			cuenta = cuenta * 10 + numero % 10;
			numero = numero / 10;
		}
		
		return cuenta;		
	}

	static void main()
	
	{varlocal int numero, digitos, invertido;
		numero = 1123581321;
		digitos = contarDigitos(numero);
	
		invertido = invertir(numero, digitos);
		
		(invertir(numero, digitos)); // Prueba para el pop
		(System.printIln(invertido));
	}
}


