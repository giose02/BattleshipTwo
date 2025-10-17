import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private BattleShipGUI vista;
    private Juego juego = new Juego();

    public Controller() {
        vista = new BattleShipGUI();
        JPanel panelInicio = vista.getPanelInicio();
        vista.add(panelInicio);
        comenzarJuego();

    }

    private void comenzarJuego() {
        vista.getStartButton().addActionListener(e -> {
            vista.remove(vista.getPanelInicio());
            vista.construtorPanelPrincipal();
            vista.add(vista.switchJugador2());
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
                String[] imagenes = { "src/resources/anim1.jpg", "src/resources/anim2.jpg","src/resources/anim3.jpg" };
                vista.mostrarSecuenciaImagenes(vista.getTableroj2()[fila][col], imagenes, 600);


            } else if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                vista.getTableroj2()[fila][col].setBackground(Color.RED);
                juego.getTableroJugador1().getCasilla(fila, col).disparar();
            }
            juego.cambiarTurno();

            //---Cambio de Tablero---
            vista.remove(vista.switchJugador2());
            vista.add(vista.switchJugador1());
            vista.repaint();
            vista.revalidate();



        } else {
            if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada()
                    && !juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                String[] imagenes = { "src/resources/anim1.jpg", "src/resources/anim2.jpg","src/resources/anim3.jpg" };
                vista.mostrarSecuenciaImagenes(vista.getTableroj1()[fila][col], imagenes, 700);
                juego.getTableroJugador2().getCasilla(fila, col).disparar();

            } else if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                vista.getTableroj1()[fila][col].setBackground(Color.RED);
                juego.getTableroJugador2().getCasilla(fila, col).disparar();
                vista.getTableroj1()[fila][col].setEnabled(false);
            }
            juego.cambiarTurno();

            //---Cambio de Tablero---
            vista.remove(vista.switchJugador1());
            vista.add(vista.switchJugador2());
            vista.repaint();
            vista.revalidate();

        }
    }
}
