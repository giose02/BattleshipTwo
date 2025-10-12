public class Juego {
    private Tablero tableroJugador1;
    private Tablero tableroJugador2;
    private boolean turnoActual;
    private boolean juegoTerminado;

    public Juego(){
        this.turnoActual=true;
        this.tableroJugador1=new Tablero();
        this.tableroJugador2=new Tablero();
    };


    public boolean getTurnoActual(){
        return turnoActual;
    }
    public void cambiarTurno(){
        this.turnoActual=!this.turnoActual;
    }

    public boolean verificarGanador(){
        return false;
        //Se fija si alguno gano el juego
    }
    public Tablero getTableroJugador1() {
        return tableroJugador1;
    }
    public Tablero getTableroJugador2() {
        return tableroJugador2;
    }
}
