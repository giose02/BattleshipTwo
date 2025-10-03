import java.util.List;

public class Barco {
    private boolean hundido;
    private int tamano;
    private List<Coordenada> posicion;//ver como se pone xq es una coordenada
    private int vidas;

    public Barco(boolean hundido, int tamano, List<Coordenada> posicion, int vidas) {
        this.hundido = hundido;
        this.tamano = tamano;
        this.posicion = posicion;
        this.vidas = vidas;
    }

    public Barco() {}

    public boolean isHundido() {
        return hundido;
    }

    public void setHundido(boolean hundido) {
        this.hundido = hundido;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public boolean estaHundido(){ //falta hacer
        return hundido;
    }

    public boolean recibirImpacto(){ //falta hacer
        return true;
    }
}
