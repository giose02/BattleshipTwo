public class Tablero {
    private int filas;
    private char columnas;
    private Casilla[][] casilla;

    public Tablero(int filas, char columnas, Casilla[][] casilla) {
        this.filas = filas;
        this.columnas = columnas;
        this.casilla = casilla;
    }

    public Tablero(){}

    //Metodos recibirDisparo , colocarBarco y mostrarTablero
}
