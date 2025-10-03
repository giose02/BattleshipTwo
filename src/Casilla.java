public class Casilla {
    private boolean tieneBarco;
    private boolean fueDisparada;
    private Barco barco;

    public Casilla(boolean tieneBarco, boolean fueDisparada, Barco barco){
        this.tieneBarco=tieneBarco;
        this.fueDisparada= fueDisparada;
        this.barco=barco;
    }

    public Casilla(){}

}
