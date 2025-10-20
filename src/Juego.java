public class Juego {
    private Tablero tableroJugador1;
    private Tablero tableroJugador2;
    private boolean turnoActual;
    private boolean juegoTerminado;

    public Juego() {
        this.turnoActual = true;
        this.tableroJugador1 = new Tablero();
        this.tableroJugador2 = new Tablero();
    };

    public boolean getTurnoActual() {
        return turnoActual;
    }

    public void cambiarTurno() {
        this.turnoActual = !this.turnoActual;
    }

    public boolean verificarGanador(int Jugador) {
        switch (Jugador) {
            case 1:
                return comprobarBarosHundidos(tableroJugador2);
            case 2:
                return comprobarBarosHundidos(tableroJugador1);
        }
        return false;
    }

    public Tablero getTableroJugador1() {
        return tableroJugador1;
    }

    public Tablero getTableroJugador2() {
        return tableroJugador2;
    }

    private boolean comprobarBarosHundidos(Tablero tablero) {
        int cont = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero.getCasilla(i, j).getTieneBarco() && tablero.getCasilla(i, j).getFueDisparada()) {
                    cont++;
                }
            }
        }
        return cont == 17;

    }
}
