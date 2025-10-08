import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;


public class BattleShipGUI extends JFrame {

    public BattleShipGUI() {
        setTitle("BattleShip");
        setBounds(500,500,500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20)); //Gap entre paneles internos


        //---Ventana Main---
        JPanel panelPrincipal = new JPanel( new BorderLayout() );

        BackgroundPanel bgPanel = new BackgroundPanel("resources/battleships-pictures-1920-x-1080-nxrdvs7dmyq3jtlq.jpg");
        setContentPane(bgPanel);
        panelPrincipal.setLayout(new BorderLayout());

        JButton battleShip = new JButton("Jugar");
        battleShip.setSize(25,25);
        panelPrincipal.add(battleShip,BorderLayout.NORTH);
        panelPrincipal.setOpaque(false);

        battleShip.addActionListener(e -> {
            switchPanel();
            remove(panelPrincipal);
            bgPanel.paintComponent(null);
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

    //---Clase Auxiliar BG ---
    private static class BackgroundPanel extends JPanel {
        private BufferedImage background;

        public BackgroundPanel(String path) {
            // intentamos cargar desde disco
            try {
                File f = new File(path);
                if (f.exists()) {
                    background = ImageIO.read(f);
                } else {
                    // intentamos cargar como recurso en classpath
                    URL res = BackgroundPanel.class.getResource("/" + path);
                    if (res != null) background = ImageIO.read(res);
                }
            } catch (IOException e) {
                // si falla, dejamos background en null y seguimos
                background = null;
            }
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
