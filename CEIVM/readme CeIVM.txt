Máquina Virtual de Compiladores e Intérpretes


El ejecutable que acompaña esta distribución es una implementación de la
Máquina Virtual de Compiladores e Intérpretes (CeIVM), un intérprete y 
máquina virtual para el lenguaje de código intermedio CeIASM
que se encuentra definido en el apunte provisto.

La CeIVM se compone de una API (ar.edu.uns.cs.cei.ceivmapi.CeIVMAPI) que 
permite utilizar la máquina virtual de manera programática (existiendo la 
posibilidad de controlar más detalladamente las diversas etapas del
intérprete, de combinar múltiples archivos de código intermedio en un mismo
ambiente de tiempo de ejecución, y de implementar herramientas basadas en 
la API tales como debuggers, desensambladores, etc).

Adicionalmente se provee un front-end simple (ar.edu.uns.cs.cei.ceivm.CeIVM)
listo para poder ejecutar la máquina virtual directamente desde la línea 
de comandos. El modo de uso del front-end es el siguiente:


>java -jar CeIVM-cei2011.jar <archivo CeIASM>


La máquina virtual provee una funcionalidad adicional que permite realizar
un listado de la información de ensamblado, linkeo y carga que puede ser
útil al momento de corroborar que la configuración del ambiente de tiempo
de ejecución y la generación de código es correcta. Además, brinda
información de carga esencial para ubicar una instrucción a partir del valor
del pc (errores de tiempo de ejecución). Para generar este archivo el modo
de uso es alguno de los siguientes:

>java -jar CeIVM-cei2011.jar <archivo CeIASM> -v
>java -jar CeIVM-cei2011.jar <archivo CeIASM> -v <archivoListado>

La primer alternativa genera el listado en un archivo cuyo nombre resulta de
añadir una extensión al nombre del archivo CeIASM. La segunda alternativa
permite especificar un nombre para el archivo de listado.



Esta distribución incluye:
- El intérprete en un .JAR (CeIVM-cei2011.jar) que contiene todas las 
  referencias a librerías necesarias para ejecutarlo.
- Este archivo readme.
- Un archivo de texto plano (rutinasHeap.txt) con las rutinas simples 
  de manejo de heap.



Cualquier problema, duda, o inconveniente reportarlo a sg@cs.uns.edu.ar.
