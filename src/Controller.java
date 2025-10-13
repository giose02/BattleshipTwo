/*import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private BattleShipGUI vista;
    private Juego juego;

    public Controller() {
        vista = new BattleShipGUI();
        juego = new Juego();
        JPanel panelInicio = vista.getPanelInicio();
        vista.add(panelInicio);
        comenzarJuego();

    }
    private void comenzarJuego() {
        vista.getStartButton().addActionListener(e -> {
            vista.remove(vista.getPanelInicio());
            vista.add(vista.switchPanel());
            vista.revalidate();
            vista.repaint();
            vista.addTableroListener(this);

        });
    }
    public void actionPerformed(ActionEvent e) {
        String[] partes = e.getActionCommand().split(",");
        int fila = Integer.parseInt(partes[0]);
        int col = Integer.parseInt(partes[1]);
        switch (juego.getTurnoActual()){
            case true:
                if(!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada() && !juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()){
                    vista.getTableroj2()[fila][col].setBackground(Color.BLUE);
                    juego.getTableroJugador1().getCasilla(fila, col).disparar();

                } else if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada() && juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                    vista.getTableroj2()[fila][col].setBackground(Color.RED);
                    juego.getTableroJugador1().getCasilla(fila, col).disparar();
                }
                juego.cambiarTurno();
                break;
            case false:
                if(!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada() && !juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()){
                    vista.getTableroj1()[fila][col].setBackground(Color.BLUE);
                    juego.getTableroJugador2().getCasilla(fila, col).disparar();

                } else if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada() && juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                    vista.getTableroj1()[fila][col].setBackground(Color.RED);
                    juego.getTableroJugador2().getCasilla(fila, col).disparar();
                }
                juego.cambiarTurno();
                break;
        }
    }
}*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private BattleShipGUI vista;
    private Juego juego; // si no lo tenés implementado, podés dejarlo null por ahora
    private boolean turnoJugador1 = true; // true = J1, false = J2

    public Controller() {
        vista = new BattleShipGUI();
        juego = null; // si tenés la clase Juego, pon: new Juego();

        // configurar botón inicio
        vista.getStartButton().addActionListener(e -> {
            // cuando se presiona JUGAR mostramos el tablero 1 y registramos listeners
            turnoJugador1 = true;
            vista.mostrarTableroJugador1();

            // addTableroListener solo necesita llamarse una vez para conectar a los botones
            vista.addTableroListener(this);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // parsea coordenadas
        String[] partes = e.getActionCommand().split(",");
        int fila = Integer.parseInt(partes[0]);
        int col = Integer.parseInt(partes[1]);

        if (turnoJugador1) {
            // jugador 1 acaba de disparar a (fila,col)
            // marcamos en su propio tablero dónde disparó (para que no se olvide)
            JButton miBoton = vista.getTableroJ1()[fila][col];
            miBoton.setBackground(Color.LIGHT_GRAY);
            miBoton.setEnabled(false);

            // también marcamos en el tablero del oponente (aunque esté oculto ahora)
            // si tu modelo Juego sabe si hay barco, usalo; aquí simulamos: alterna acierto/agua por ejemplo
            // ej simple: hacemos rojo si fila+col es par (simulación), azul si impar.
            boolean acierto = ((fila + col) % 2 == 0);
            JButton enemigoBoton = vista.getTableroJ2()[fila][col];
            enemigoBoton.setBackground(acierto ? Color.RED : Color.BLUE);
            enemigoBoton.setEnabled(false);

            // cambiar turno
            turnoJugador1 = false;
            JOptionPane.showMessageDialog(vista, "Turno del Jugador 2");
            vista.mostrarTableroJugador2();

        } else {
            // jugador 2 acaba de disparar
            JButton miBoton = vista.getTableroJ2()[fila][col];
            miBoton.setBackground(Color.LIGHT_GRAY);
            miBoton.setEnabled(false);

            // marcar en tablero del oponente
            boolean acierto = ((fila + col) % 2 == 0);
            JButton enemigoBoton = vista.getTableroJ1()[fila][col];
            enemigoBoton.setBackground(acierto ? Color.RED : Color.BLUE);
            enemigoBoton.setEnabled(false);

            // cambiar turno
            turnoJugador1 = true;
            JOptionPane.showMessageDialog(vista, "Turno del Jugador 1");
            vista.mostrarTableroJugador1();
        }
    }

    // main para arrancar
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }
}
