import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;


public class BattleShipGUI extends JFrame {
    private JButton startButton;
    private JButton[][] tableroj1= new JButton[10][10];
    private JButton[][] tableroj2= new JButton[10][10];

    public BattleShipGUI() {};

    public JPanel getPanelInicio(){
        setTitle("BattleShip");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20)); //Gap entre paneles internos


        //---Ventana Main---
        JPanel panelPrincipal = new JPanel( new BorderLayout() );

        BackgroundPanel bgPanel = new BackgroundPanel("resources/battleships-pictures-1920-x-1080-nxrdvs7dmyq3jtlq.jpg");
        setContentPane(bgPanel);

        JButton battleShip = new JButton("Jugar");
        this.startButton = battleShip;
        battleShip.setSize(25,25);
        panelPrincipal.add(battleShip,BorderLayout.NORTH);
        panelPrincipal.setOpaque(false);


        setVisible(true);
        return panelPrincipal;
    };

    // ---Creo Tablero---
    private JPanel crearTablero(JButton[][] a) {
        JPanel tableroGUI = new JPanel(new GridLayout(10, 10, 5, 5));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (a[i][j] == null) { // Creamos el botón solo si no existe
                    JButton botonCelda = new JButton("");
                    botonCelda.setPreferredSize(new Dimension(10,10));
                    a[i][j] = botonCelda;
                }
                tableroGUI.add(a[i][j]);
            }
        }
        return tableroGUI;
    }
    public void addTableroListener(ActionListener listener) {
        for (int i = 0; i < tableroj1.length; i++) {
            for (int j = 0; j < tableroj1[i].length; j++) {
                JButton b = tableroj1[i][j];
                b.setActionCommand(i + "," + j);
                b.addActionListener(listener);
            }
        }
        for (int i = 0; i < tableroj2.length; i++) {
            for (int j = 0; j < tableroj2[i].length; j++) {
                JButton b = tableroj2[i][j];
                b.setActionCommand(i + "," + j);
                b.addActionListener(listener);
            }
        }
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

    public JPanel switchPanel() {
        JPanel panelJuego = new JPanel(new GridLayout(1, 2, 5,5)); // Panel principal horizontal

        //----Jugador 1----
        JPanel panelTablero1 = crearTablero(tableroj1);
        JPanel panelPowerUps1 = crearPanelPowerUps("pwu1", "pwu2", "pwu3");
        JPanel panelJugador1 = new JPanel(new BorderLayout());
        panelJugador1.add(panelTablero1, BorderLayout.CENTER);
        panelJugador1.add(panelPowerUps1, BorderLayout.SOUTH);
        panelJugador1.setOpaque(false);

        //----Jugador 2----
        JPanel panelTablero2 = crearTablero(tableroj2);
        JPanel panelPowerUps2 = crearPanelPowerUps("pwu1", "pwu2", "pwu3");
        JPanel panelJugador2 = new JPanel(new BorderLayout());
        panelJugador2.add(panelTablero2, BorderLayout.CENTER);
        panelJugador2.add(panelPowerUps2, BorderLayout.SOUTH);
        panelJugador2.setOpaque(false);

        // ---- Agregar paneles a la ventana ----
        panelJuego.add(panelJugador1);
        panelJuego.add(panelJugador2);
        panelJuego.setOpaque(false);

        return panelJuego;
    }
    public JButton getStartButton() {
        return startButton;
    }
    public JButton[][] getTableroj1() {
        return tableroj1;
    }
    public JButton[][] getTableroj2() {
        return tableroj2;
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
