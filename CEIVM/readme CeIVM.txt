M�quina Virtual de Compiladores e Int�rpretes


El ejecutable que acompa�a esta distribuci�n es una implementaci�n de la
M�quina Virtual de Compiladores e Int�rpretes (CeIVM), un int�rprete y 
m�quina virtual para el lenguaje de c�digo intermedio CeIASM
que se encuentra definido en el apunte provisto.

La CeIVM se compone de una API (ar.edu.uns.cs.cei.ceivmapi.CeIVMAPI) que 
permite utilizar la m�quina virtual de manera program�tica (existiendo la 
posibilidad de controlar m�s detalladamente las diversas etapas del
int�rprete, de combinar m�ltiples archivos de c�digo intermedio en un mismo
ambiente de tiempo de ejecuci�n, y de implementar herramientas basadas en 
la API tales como debuggers, desensambladores, etc).

Adicionalmente se provee un front-end simple (ar.edu.uns.cs.cei.ceivm.CeIVM)
listo para poder ejecutar la m�quina virtual directamente desde la l�nea 
de comandos. El modo de uso del front-end es el siguiente:


>java -jar CeIVM-cei2011.jar <archivo CeIASM>


La m�quina virtual provee una funcionalidad adicional que permite realizar
un listado de la informaci�n de ensamblado, linkeo y carga que puede ser
�til al momento de corroborar que la configuraci�n del ambiente de tiempo
de ejecuci�n y la generaci�n de c�digo es correcta. Adem�s, brinda
informaci�n de carga esencial para ubicar una instrucci�n a partir del valor
del pc (errores de tiempo de ejecuci�n). Para generar este archivo el modo
de uso es alguno de los siguientes:

>java -jar CeIVM-cei2011.jar <archivo CeIASM> -v
>java -jar CeIVM-cei2011.jar <archivo CeIASM> -v <archivoListado>

La primer alternativa genera el listado en un archivo cuyo nombre resulta de
a�adir una extensi�n al nombre del archivo CeIASM. La segunda alternativa
permite especificar un nombre para el archivo de listado.



Esta distribuci�n incluye:
- El int�rprete en un .JAR (CeIVM-cei2011.jar) que contiene todas las 
  referencias a librer�as necesarias para ejecutarlo.
- Este archivo readme.
- Un archivo de texto plano (rutinasHeap.txt) con las rutinas simples 
  de manejo de heap.



Cualquier problema, duda, o inconveniente reportarlo a sg@cs.uns.edu.ar.
