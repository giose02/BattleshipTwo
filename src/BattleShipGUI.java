/*import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


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
        panelPrincipal.add(battleShip,BorderLayout.CENTER);
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
}*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BattleShipGUI extends JFrame {
    private JPanel panelInicio;
    private JButton startButton;

    private JPanel panelTableroJ1;
    private JPanel panelTableroJ2;

    private final int FILAS = 10;
    private final int COLUMNAS = 10;

    private JButton[][] tableroJ1;
    private JButton[][] tableroJ2;

    public BattleShipGUI() {
        setTitle("Batalla Naval");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        // crear los tableros y paneles una sola vez
        crearPanelInicio();
        crearTablerosYPanels();

        // mostrar pantalla inicio
        setContentPane(panelInicio);
        setVisible(true);
    }

    private void crearPanelInicio() {
        panelInicio = new JPanel() {
            private final Image fondo = new ImageIcon("fondo.jpg").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondo != null) g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelInicio.setLayout(new GridBagLayout());

        startButton = new JButton("JUGAR");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBackground(new Color(30, 144, 255));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(220, 70));

        panelInicio.add(startButton);
    }

    private void crearTablerosYPanels() {
        // crear matrices una sola vez
        tableroJ1 = new JButton[FILAS][COLUMNAS];
        tableroJ2 = new JButton[FILAS][COLUMNAS];

        // panel jugador 1
        panelTableroJ1 = new JPanel(new BorderLayout());
        JLabel t1 = new JLabel("Tablero - Jugador 1", SwingConstants.CENTER);
        t1.setFont(new Font("Arial", Font.BOLD, 20));
        panelTableroJ1.add(t1, BorderLayout.NORTH);
        panelTableroJ1.add(crearGridPanel(tableroJ1), BorderLayout.CENTER);

        // panel jugador 2
        panelTableroJ2 = new JPanel(new BorderLayout());
        JLabel t2 = new JLabel("Tablero - Jugador 2", SwingConstants.CENTER);
        t2.setFont(new Font("Arial", Font.BOLD, 20));
        panelTableroJ2.add(t2, BorderLayout.NORTH);
        panelTableroJ2.add(crearGridPanel(tableroJ2), BorderLayout.CENTER);
    }

    // crea un JPanel con GridLayout y rellena (y guarda) la matriz de botones dada
    private JPanel crearGridPanel(JButton[][] matriz) {
        JPanel grid = new JPanel(new GridLayout(FILAS, COLUMNAS, 3, 3));
        grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(50, 50));
                b.setActionCommand(i + "," + j);
                b.setBackground(new Color(173, 216, 230));
                matriz[i][j] = b;
                grid.add(b);
            }
        }
        return grid;
    }

    // Muestra el panel del jugador 1 (no recrea nada)
    public void mostrarTableroJugador1() {
        setContentPane(panelTableroJ1);
        revalidate();
        repaint();
    }

    // Muestra el panel del jugador 2 (no recrea nada)
    public void mostrarTableroJugador2() {
        setContentPane(panelTableroJ2);
        revalidate();
        repaint();
    }

    // getters y helpers
    public JButton getStartButton() { return startButton; }
    public JPanel getPanelInicio() { return panelInicio; }

    public JButton[][] getTableroJ1() { return tableroJ1; }
    public JButton[][] getTableroJ2() { return tableroJ2; }

    // agrega el listener a *todos* los botones de ambos tableros (lo hace una vez)
    public void addTableroListener(ActionListener listener) {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tableroJ1[i][j].addActionListener(listener);
                tableroJ2[i][j].addActionListener(listener);
            }
        }
    }
}
