public class Tablero {
    private Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[10][10]; // <-- inicializo la matriz

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                casillas[i][j] = new Casilla(); // ahora sí se puede
            }
        }
    }

    // Getter para acceder a una casilla
    public Casilla getCasilla(int fila, int col) {
        return casillas[fila][col];
    }
}

