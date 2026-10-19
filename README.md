# RogueLike Java

Juego de rol por consola desarrollado en Java. El proyecto incluye un heroe, enemigos y combate por turnos, con guardado y carga de partidas mediante serializacion.

## Funcionalidades previstas

- Crear un heroe y conservar sus estadisticas.
- Combatir contra enemigos de uno en uno.
- Atacar, defenderse y usar el escudo para absorber dano.
- Recuperar vida tras derrotar a un enemigo.
- Guardar y continuar una partida con el archivo `partida.ser`.
- Consultar las estadisticas de la partida.

## Requisitos

- Java JDK instalado.
- Un IDE compatible con Java, como Eclipse, IntelliJ IDEA o Visual Studio Code con las extensiones de Java.

## Ejecutar el proyecto

1. Clona este repositorio.
2. Abre el proyecto en tu IDE.
3. Ejecuta la clase `Main` del paquete `org.cuatrovientos.dam.psp.versionRogueLike`.

Si el proyecto usa una estructura Java convencional, los archivos fuente deben estar dentro de `src/org/cuatrovientos/dam/psp/versionRogueLike/` o en la carpeta de fuentes configurada por el IDE.

## Guardado de partidas

El juego utiliza el archivo `partida.ser` para guardar el heroe, los enemigos pendientes y el estado del turno. Este archivo se genera al ejecutar el programa y no debe subirse al repositorio.

## Estado del proyecto

El repositorio contiene la documentacion inicial. Anade los archivos `.java` del proyecto para que otras personas puedan compilarlo y ejecutarlo.