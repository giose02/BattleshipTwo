import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private BattleShipGUI vista;

    public Controller() {
        vista = new BattleShipGUI();
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

        vista.getTableroj1()[fila][col].setBackground(Color.BLUE);
    }
}
