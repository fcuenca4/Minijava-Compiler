
class L{
varinst N a;
L(N n){
a=n;
}

dynamic N devolverN(){
	return a;
}



static void main(){
	varlocal N na;
	na= new N();
	varlocal L li;
	li= new L(na);
	(li.devolverN());
	varlocal String toRet;
	toRet= li.devolverN().devolverString();
	(System.printSln("Devuelvo el string asociado a N dentro de L --con metodos anidados--"));
	(System.printSln(li.devolverN().devolverString()));
	(System.println());
	(System.printSln("Devuelvo el string asociado a N dentro de L --asignandolo a un string--"));
	(System.printSln(toRet));
	(System.println());
	(System.printSln("Devuelvo el string asociado a N dentro de L --con ids anidados--"));
	(System.printSln(li.a.devolverString()));
	(System.println());
	
	
	varlocal N primera;
	varlocal N segunda;
	varlocal N tercera;
	varlocal N cuarta;
	
	(System.printSln("Creo la primera"));
	primera= new N();
	(System.printSln("Creo la segunda"));
	segunda= new N();
	(System.printSln("Creo la tercera"));
	tercera= new N();
	(System.printSln("Creo la cuarta"));
	cuarta= new N();
	(System.println());
	
	(System.printSln("Anido primera->segunda->tercera->cuarta"));
	(primera.asigN(segunda));
	(segunda.asigN(tercera));
	(tercera.asigN(cuarta));
	(cuarta.asigN(null));
	(System.println());
	
	(primera.devN().devN().devN());
	(System.printSln("Devuelvo el string asociado a la cuarta --metodos anidados--"));
	(System.printSln(primera.devN().devN().devN().devolverString()));
	
	/***********************************
	varlocal N otroN;
	otroN =new N();
	(otroN.asigN(new N()));
	(otroN.devN());
	(System.printSln(otroN.devN().devolverString()));
	***/
}	

}

class N{
varinst String j;
varinst N ene;

N(){
j="perro";
}

dynamic String devolverString(){
	return j;
}
dynamic N devN(){
	return ene;
}
dynamic void asigN(N e){
	ene=e;
}



}