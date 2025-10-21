public class Casilla {
    private boolean tieneBarco;
    private boolean fueDisparada;

    // ---Contructor---
    public Casilla() {
        this.tieneBarco = false;
        this.fueDisparada = false;
    }

    // ---Getters---
    public boolean getFueDisparada() {
        return fueDisparada;
    }

    public boolean getTieneBarco() {
        return tieneBarco;
    }

    // ---Setter---
    public void setFueDisparada(boolean fueDisparada) {
        this.fueDisparada = fueDisparada;
    }

    public void setTieneBarco(boolean tieneBarco) {
        this.tieneBarco = tieneBarco;
    }

    // ---Metodos---
    public void disparar() {
        this.fueDisparada = true;
    }
}
