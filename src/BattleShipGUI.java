import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;



public class BattleShipGUI extends JFrame {
    private JButton startButton;
    private JButton[][] tableroj1= new JButton[10][10];
    private JButton[][] tableroj2= new JButton[10][10];
    private JButton[] j1PowerUps= new JButton[3];
    private JButton[] j2PowerUps= new JButton[3];

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
        JPanel tableroGUI = new JPanel(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (a[i][j] == null) { // Creamos el botón solo si no existe
                    JButton botonCelda = new JButton("");
                    botonCelda.setPreferredSize(new Dimension(30, 30));
                    botonCelda.setContentAreaFilled(false);
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
    private JPanel crearPanelPowerUps(JButton[] a) {
        JPanel panel = new JPanel();
        for (int i=0;i<3; i++ ) {
            JButton boton = new JButton();
            boton.setPreferredSize(new Dimension(30, 30));
            a[i]=boton;
            panel.add(boton);
        }
        return panel;
    }

    //---getTablero---
    private JPanel getTablero(JButton[][] a) {
        JPanel panelTablero = new JPanel(new GridLayout(10, 10,4,4));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                panelTablero.add(a[i][j]);
            }
        }
        return panelTablero;
    }

    //---getPowerUps---
    private JPanel getPowerUps(JButton[] a) {
        JPanel panelPowerUps = new JPanel(new GridLayout(1, 3));
        for (int i = 0; i < 3; i++) {
            panelPowerUps.add(a[i]);
        }
        return panelPowerUps;
    }
    //--- Mostrar un tablero solo---
    public JPanel switchJugador1() {
        JPanel panelTotal = new JPanel(new GridBagLayout()); // Cambiamos el layout a GridBagLayout
        panelTotal.setPreferredSize(new Dimension(500, 500));

        JPanel panelJuego1 = new JPanel(new BorderLayout(30, 30));
        panelJuego1.setPreferredSize(new Dimension(500, 500));

        JPanel panelTablero1 = getTablero(tableroj1);
        panelTablero1.setOpaque(false);
        JPanel panelPowerUps1 = getPowerUps(j1PowerUps);
        panelPowerUps1.setOpaque(false);


        panelJuego1.add(panelTablero1, BorderLayout.CENTER);
        panelJuego1.add(panelPowerUps1, BorderLayout.SOUTH);

        panelJuego1.setOpaque(false);
        panelTotal.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Centrar horizontalmente
        gbc.gridy = 0; // Centrar verticalmente
        gbc.anchor = GridBagConstraints.CENTER; // Aseguramos que se centre
        panelTotal.add(panelJuego1, gbc);

        return panelTotal;
    }

    public JPanel switchJugador2() {
        JPanel panelJuego2 = new JPanel(new GridLayout(1, 2, 20, 0));

        JPanel panelTablero2 = getTablero(tableroj2);
        JPanel panelPowerUps2 = getPowerUps(j2PowerUps);
        panelJuego2.add(panelTablero2);
        panelJuego2.add(panelPowerUps2);
        panelJuego2.setOpaque(false);

        return panelJuego2;
    };

    //---Cambio Pantalla Juego---

    public void construtorPanelPrincipal() {
        //----Jugador 1----
        crearTablero(tableroj1);
        crearPanelPowerUps(j1PowerUps);

        //----Jugador 2----
        crearTablero(tableroj2);
        crearPanelPowerUps(j2PowerUps);

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
    public void mostrarSecuenciaImagenes(JComponent componente, String[] imagenes, int delay) {
        final int[] index = {0}; // contador interno

        Timer timer = new Timer(delay, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (index[0] < imagenes.length) {
                    ImageIcon icono = new ImageIcon(imagenes[index[0]]);
                    if (componente instanceof JButton)
                        ((JButton) componente).setIcon(icono);
                    else if (componente instanceof JLabel)
                        ((JLabel) componente).setIcon(icono);
                    index[0]++;
                } else {
                    timer.stop(); // fin de la secuencia
                }
            }
        });
        timer.start();
    }
}
