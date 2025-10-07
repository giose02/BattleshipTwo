import javax.swing.*;
import java.awt.*;

public class BattleShipGUI extends JFrame {

    public BattleShipGUI() {
        setTitle("BattleShip");
        setBounds(500,500,500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20)); //Gap entre paneles internos


        //---Ventana Main---
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setOpaque(true);
        JButton battleShip = new JButton("Jugar");
        battleShip.setPreferredSize(new Dimension(100, 50));
        panelPrincipal.add(battleShip, BorderLayout.CENTER);

        battleShip.addActionListener(e -> {
            switchPanel();
            remove(panelPrincipal);
        });

        add(panelPrincipal, BorderLayout.CENTER);
        setVisible(true);
    }

    // ---Creo Tablero---
    private JPanel crearTablero() {
        JPanel tableroGUI = new JPanel(new GridLayout(10, 10));
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                JButton botonCelda = new JButton("");
                botonCelda.setPreferredSize(new Dimension(30, 30));
                tableroGUI.add(botonCelda);
            }
        }
        return tableroGUI;
    }

    // ---Creo Panel PowerUps
    private JPanel crearPanelPowerUps(String... nombres) { // con el String... puedo poner tantos PwUps como quiera
        JPanel panel = new JPanel();
        for (String nombre : nombres) { // Itero en cada String parametro
            JButton boton = new JButton(nombre);
            panel.add(boton);
        }
        return panel;
    }

    //---Cambio Pantalla Juego---

    public void switchPanel() {
        //----Jugador 1----
        JPanel panelTablero1 = crearTablero();
        JPanel panelPowerUps1 = crearPanelPowerUps("pwu1", "pwu2", "pwu3");
        JPanel panelJugador1 = new JPanel(new BorderLayout());
        panelJugador1.add(panelTablero1, BorderLayout.NORTH);
        panelJugador1.add(panelPowerUps1, BorderLayout.SOUTH);

        //----Jugador 2----
        JPanel panelTablero2 = crearTablero();
        JPanel panelPowerUps2 = crearPanelPowerUps("pwu4", "pwu5", "pwu6");
        JPanel panelJugador2 = new JPanel(new BorderLayout());
        panelJugador2.add(panelTablero2, BorderLayout.NORTH);
        panelJugador2.add(panelPowerUps2, BorderLayout.SOUTH);

        // ---- Agregar paneles a la ventana ----
        add(panelJugador1, BorderLayout.WEST);
        add(panelJugador2, BorderLayout.EAST);
        pack();
    }
}
