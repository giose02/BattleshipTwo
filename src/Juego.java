public class Juego {
    private Jugador jugador1;
    private Jugador jugador2;
    private Tablero tablero;
    private int turnoActual;
    private boolean juegoTerminado;

    public Juego(Jugador jugador1, Jugador jugador2, Tablero tablero) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.tablero = tablero;
    }

    public Juego(){}

    public Jugador getJugador1() {
        return jugador1;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

    public void iniciarJuego(){
        //Configurar tableros y barcos aca
    }

    public void jugarTurno(){
        //Se juega un turno
    }

    public boolean verificarGanador(){
        return false;
        //Se fija si alguno gano el juego
    }
}
