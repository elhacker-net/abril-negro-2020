# Bomba fork en lenguaje BASIC.
[[Presentación Original]](https://foro.elhacker.net/abril_negro/abril_negro_2020_malwaretipo_bomba_fork-t503780.0.html)

> **Atención**: Este repositiorio, además del código del programa, contiene un ejecutable. Debido a la naturaleza del concurso, este ejecutable **debe ser ejecutado UNICA Y EXCLUSIVAMENTE en entornos virtualizados** o de prueba debido a los daños que puedan ocasionar. Elhacker.NET NO se hace responsable de las perdidas o daños que pueda ocasionar. Por su seguridad, el ejecutable tiene la extensión cambiada de `.exe` a `.exe_` y debe ser cambiada para poder ser ejecutado.


- **Autor:** crazykenny ([Perfil](https://foro.elhacker.net/profiles/crazykenny-u386824.html)).
- **Categoria:** Malware
- **Nombre de la herramienta/píldora**: Bomba fork en lenguaje BASIC.
- **Lenguaje(s) en los que esta diseñado:** Blitz 3D.
- **Descripción del trabajo:**

    Básicamente, la idea de este trabajo es crear un programa "similar" a una Bomba Fork para ser ejecutada en sistemas operativos Windows "desprotegidos" y realizando las siguientes acciones:

    - Copiarse a si mismo y a todos los ejecutables que estén dentro de la carpeta de la aplicación en la siguiente carpeta: `C:/ProgramData\Microsoft\Windows\Start Menu\Programs\Startup`. Por supuesto, esta acción tiene dos objetivos:

        1. Poder realizar copias del archivo .exe en la misma carpeta para luego ejecutarlas pero con un nombre diferente para luego ejecutarlas.
        2. Intentar mantener el programa y sus "copias" en ejecución después de reiniciar el sistema

    - Hacer varias copias de si mismo veces en otras unidades de almacenamiento del sistema para luego ejecutarlas. Por motivos obvios, se han descartado las unidades de disquete ( `A:\` y `B:\` ).

    - Ejecutarse despues de realizar una copia en las siguientes carpetas y unidades antes menciondadas.

- **Captura de pantalla (en el caso de tener interfaz):**
- **Codigo original del programa:** https://foro.elhacker.net/analisis_y_diseno_de_malware/ejemplo_bombas_fork-t500373.0.html
