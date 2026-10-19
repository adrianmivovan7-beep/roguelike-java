# RogueLike Java

Juego de rol por consola desarrollado en Java. El proyecto usa Maven y permite combatir contra enemigos por turnos y guardar el estado de la partida.

## Funcionalidades

- Crear un heroe y conservar sus estadisticas.
- Combatir contra enemigos de uno en uno.
- Atacar, defenderse y utilizar un escudo que absorbe dano.
- Recuperar 5 puntos de vida despues de derrotar a un enemigo, hasta el maximo de vida.
- Guardar y continuar una partida mediante serializacion en `partida.ser`.
- Empezar una nueva run conservando las estadisticas del heroe.
- Consultar victorias, derrotas y enemigos derrotados.

## Requisitos

- JDK 17 o posterior.
- Maven.
- Un IDE compatible con Java, como Eclipse, IntelliJ IDEA o Visual Studio Code con las extensiones de Java.

## Estructura

```text
src/
  main/java/version/beta/RogueLikeProyectoPersonal/
    Character.java
    Enemy.java
    Friend.java
    Hero.java
    Main.java
  test/java/version/beta/RogueLikeProyectoPersonal/
    AppTest.java
pom.xml
```

## Ejecutar

Desde la carpeta donde se encuentra `pom.xml`, ejecuta:

```bash
mvn compile
```

Despues, ejecuta la clase `version.beta.RogueLikeProyectoPersonal.Main` desde tu IDE. Para ejecutar las pruebas:

```bash
mvn test
```

## Guardado de partidas

El programa crea el archivo `partida.ser` en el directorio de trabajo para guardar el heroe, los enemigos pendientes y el estado del turno. El archivo esta excluido del repositorio mediante `.gitignore`.
