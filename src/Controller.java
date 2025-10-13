import javax.swing.*;
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

        if (juego.getTurnoActual()) {
            if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada()
                    && !juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                juego.getTableroJugador1().getCasilla(fila, col).disparar();
                String[] imagenes = { "src/resources/agua2.1.jpg", "src/resources/agua2.2.jpg"};
                vista.mostrarSecuenciaImagenes(vista.getTableroj2()[fila][col], imagenes, 400);


            } else if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                vista.getTableroj2()[fila][col].setBackground(Color.RED);
                juego.getTableroJugador1().getCasilla(fila, col).disparar();
            }
            juego.cambiarTurno();
        } else {
            if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada()
                    && !juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                vista.getTableroj1()[fila][col].setBackground(Color.BLUE);
                juego.getTableroJugador2().getCasilla(fila, col).disparar();

            } else if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                vista.getTableroj1()[fila][col].setBackground(Color.RED);
                juego.getTableroJugador2().getCasilla(fila, col).disparar();
                vista.getTableroj1()[fila][col].setEnabled(false);
            }
            juego.cambiarTurno();
        }
    }
}
