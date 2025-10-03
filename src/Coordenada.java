public class Coordenada {
    private int fila;
    private char columna;

    public Coordenada(int fila, char columna) {
        this.fila = fila;
        this.columna = columna;
    }
    public Coordenada() {}

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
