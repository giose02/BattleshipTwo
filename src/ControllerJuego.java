import javax.swing.*;
import java.awt.*;

// Esta bien esto? (preguntar al profe)
//public class ControllerJuego {
//    private Juego juego;
//    private BattleShipGUI ventana;
//    private boolean turnoJugador1;
//
//    public ControllerJuego(Juego juego, BattleShipGUI ventana) {
//        this.juego = juego;
//        this.ventana = ventana;
//        this.turnoJugador1 = true;
//        inicializarJuego();
//    }
//    public void inicializarJuego(){
//        for (int i = 0; i < 10; i++) {
//            for (int j=0; j<10; j++){
//                int fila = i;
//                int columna = j;
//
//                ventana.getBotonesJugador1()[i][j].addActionListener(e -> {
//                    if(turnoJugador1){
//                        disparar(tableroJugador2, fila, columna);
//                        turnoJugador1=false;
//                    }
//                    else{return}
//                })
//            }
//        }
//    }
//    private void disparar(Tablero tablero, int fila, int col) {
//        Casilla celda = tablero.getCasilla(fila, col);
//        celda.disparar();
//
//        JButton boton = turnoJugador1 ?
//                ventana.getBotonJugador2(fila, col) :
//                ventana.getBotonJugador1(fila, col);
//
//        if (celda.getTieneBarco()) {
//            boton.setBackground(Color.RED);
//        } else {
//            boton.setBackground(Color.BLUE);
//        }
//        boton.setEnabled(false);
//
//    }
//
//}
