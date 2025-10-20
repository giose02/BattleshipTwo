# BattleshipTwo

Juego de Battleship (Batalla Naval) en Java con interfaz gráfica Swing.

## Características

- Modo 1 jugador (vs Bot)
- Modo 2 jugadores
- Colocación manual de barcos
- Animaciones de disparo
- Mini tablero para ver tus propios barcos
- Contador de barcos enemigos

## Estructura del proyecto

```
BattleshipTwo/
├── src/
│   ├── Main.java
│   ├── Casilla.java
│   ├── Juego.java
│   ├── Tablero.java
│   ├── Controller.java
│   ├── BattleShipGUI.java
│   └── resources/
│       ├── bgPrin1.jpg (imagen de fondo)
│       ├── anim1.jpg
│       ├── anim2.jpg
│       └── anim3.jpg
└── README.md
```

## Cómo ejecutar

1. Compilar: `javac -d bin src/*.java`
2. Ejecutar: `java -cp bin Main`

## Nota

Recuerda copiar las imágenes de la carpeta `resources` del proyecto original a `BattleshipTwo/src/resources/`.
