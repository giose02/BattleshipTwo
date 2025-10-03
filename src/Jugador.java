import java.util.List;

public class Jugador {
    private String nombre;
    private Tablero tablero;
    private List<Barco> barcos;
    private List<PowerUp> powerUps;

    public Jugador(String nombre, Tablero tablero, List barco, List powerUps){
        this.nombre=nombre;
        this.tablero=tablero;
        this.barcos=barcos;
        this.powerUps=powerUps;
    }

    public Jugador(){}

    //metodos disparar y usarPowerUps


}
