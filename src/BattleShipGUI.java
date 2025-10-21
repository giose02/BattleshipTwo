import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class BattleShipGUI extends JFrame {
    private JButton startButton;
    private JButton[][] tableroj1 = new JButton[10][10];
    private JButton[][] tableroj2 = new JButton[10][10];
    private JButton[][] miniTableroj1 = new JButton[10][10]; // Mini tablero jugador 1
    private JButton[][] miniTableroj2 = new JButton[10][10]; // Mini tablero jugador 2
    private JButton[] j1PowerUps = new JButton[3];
    private JButton[] j2PowerUps = new JButton[3];
    private JLabel labelTurno; // Nuevo: etiqueta para mostrar el turno
    private JLabel labelBarcosEnemigo; // Label para mostrar barcos restantes del enemigo
    private JPanel panelJugador1Cache; // Cache para los paneles
    private JPanel panelJugador2Cache;
    private JLabel infoBarcos; // Label para info de colocación
    private JButton botonConfirmar; // Botón para confirmar barcos
    private JButton botonRotar; // Botón para rotar barco
    private JButton botonEliminar; // Botón para eliminar último barco
    private boolean orientacionHorizontal = true; // Orientación del barco a colocar

    // Barcos a colocar: [tamaño del barco, cantidad colocada]
    private int[] barcosRestantes = { 5, 4, 3, 3, 2 }; // Tamaños de barcos
    private int indiceBarcActual = 0;

    public BattleShipGUI() {
    };

    private JRadioButton modo1Jugador;
    private JRadioButton modo2Jugadores;

    public JPanel getPanelInicio() {
        setTitle("BattleShip");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20)); // Gap entre paneles internos

        // ---Ventana Main---
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        BackgroundPanel bgPanel = new BackgroundPanel("resources/bgPrin1.jpg");
        setContentPane(bgPanel);

        // Panel central para opciones
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.setOpaque(false);

        // Radio buttons para seleccionar modo
        modo1Jugador = new JRadioButton("1 Jugador (vs Bot)");
        modo2Jugadores = new JRadioButton("2 Jugadores");
        modo1Jugador.setOpaque(false);
        modo2Jugadores.setOpaque(false);
        modo1Jugador.setFont(new Font("Arial", Font.BOLD, 20));
        modo2Jugadores.setFont(new Font("Arial", Font.BOLD, 20));
        modo1Jugador.setForeground(Color.WHITE);
        modo2Jugadores.setForeground(Color.WHITE);

        ButtonGroup grupoModos = new ButtonGroup();
        grupoModos.add(modo1Jugador);
        grupoModos.add(modo2Jugadores);
        modo2Jugadores.setSelected(true); // Por defecto 2 jugadores

        panelOpciones.add(Box.createVerticalStrut(20));
        panelOpciones.add(modo1Jugador);
        panelOpciones.add(Box.createVerticalStrut(10));
        panelOpciones.add(modo2Jugadores);
        panelOpciones.add(Box.createVerticalStrut(30));

        JButton battleShip = new JButton("Jugar");
        this.startButton = battleShip;
        battleShip.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelOpciones.add(battleShip);

        panelPrincipal.add(panelOpciones, BorderLayout.NORTH);
        panelPrincipal.setOpaque(false);

        setVisible(true);
        return panelPrincipal;
    }

    public boolean isModo1Jugador() {
        return modo1Jugador.isSelected();
    }

    // ---Creo Tablero--- Cambiar para que sea void
    private JPanel crearTablero(JButton[][] a) {
        JPanel tableroGUI = new JPanel(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (a[i][j] == null) { // Creamos el botón solo si no existe
                    JButton botonCelda = new JButton("");
                    botonCelda.setPreferredSize(new Dimension(50, 50));
                    botonCelda.setBackground(new Color(135, 206, 250)); // Azul cielo (agua)
                    botonCelda.setOpaque(true);
                    botonCelda.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
                    botonCelda.setFocusPainted(false);
                    a[i][j] = botonCelda;
                }
                tableroGUI.add(a[i][j]);
            }
        }
        return tableroGUI;
    }

    // ---Creo Mini Tablero---
    private JPanel crearMiniTablero(JButton[][] a) {
        JPanel tableroGUI = new JPanel(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (a[i][j] == null) {
                    JButton botonCelda = new JButton("");
                    botonCelda.setPreferredSize(new Dimension(20, 20));
                    botonCelda.setBackground(new Color(135, 206, 250));
                    botonCelda.setOpaque(true);
                    botonCelda.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
                    botonCelda.setFocusPainted(false);
                    botonCelda.setEnabled(false); // Los mini tableros no son clickeables
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
        for (int i = 0; i < 3; i++) {
            JButton boton = new JButton();
            boton.setPreferredSize(new Dimension(30, 30));
            a[i] = boton;
            panel.add(boton);
        }
        return panel;
    }

    // ---getTablero---
    private JPanel getTablero(JButton[][] a) {
        JPanel panelTablero = new JPanel(new GridLayout(10, 10, 4, 4));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                panelTablero.add(a[i][j]);
            }
        }
        return panelTablero;
    }

    // ---getPowerUps---
    private JPanel getPowerUps(JButton[] a) {
        JPanel panelPowerUps = new JPanel(new GridLayout(1, 3));
        for (int i = 0; i < 3; i++) {
            panelPowerUps.add(a[i]);
        }
        return panelPowerUps;
    }

    // --- Mostrar un tablero solo---
    // Este panel es para el JUGADOR 2 disparando al tablero del JUGADOR 1
    public JPanel switchJugador1() {
        if (panelJugador1Cache != null) {
            actualizarLabelTurno("JUGADOR 2");
            return panelJugador1Cache;
        }

        JPanel panelTotal = new JPanel(new BorderLayout(10, 10));
        panelTotal.setPreferredSize(new Dimension(900, 700));

        // Panel superior con título del jugador
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        JLabel tituloJugador = new JLabel("ATACANDO AL JUGADOR 1", SwingConstants.CENTER);
        tituloJugador.setFont(new Font("Arial", Font.BOLD, 28));
        tituloJugador.setForeground(new Color(255, 69, 0));
        tituloJugador.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelSuperior.add(tituloJugador, BorderLayout.NORTH);

        // Label de turno
        labelTurno = new JLabel("TURNO: JUGADOR 2", SwingConstants.CENTER);
        labelTurno.setFont(new Font("Arial", Font.BOLD, 24));
        labelTurno.setForeground(new Color(255, 69, 0));
        labelTurno.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 69, 0), 3),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        labelTurno.setOpaque(true);
        labelTurno.setBackground(new Color(255, 255, 200));
        panelSuperior.add(labelTurno, BorderLayout.CENTER);

        // Label para contador de barcos enemigo
        labelBarcosEnemigo = new JLabel("BARCOS ENEMIGOS: --", SwingConstants.CENTER);
        labelBarcosEnemigo.setFont(new Font("Arial", Font.BOLD, 18));
        labelBarcosEnemigo.setForeground(new Color(255, 255, 255));
        labelBarcosEnemigo.setOpaque(true);
        labelBarcosEnemigo.setBackground(new Color(255, 69, 0));
        labelBarcosEnemigo.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        panelSuperior.add(labelBarcosEnemigo, BorderLayout.SOUTH);

        // Panel central que contendrá el tablero principal y el mini tablero
        JPanel panelCentral = new JPanel(new BorderLayout(20, 20));
        panelCentral.setOpaque(false);

        JPanel panelJuego1 = new JPanel(new BorderLayout(30, 30));
        panelJuego1.setPreferredSize(new Dimension(600, 600));

        JPanel panelTablero1 = getTablero(tableroj1);
        panelTablero1.setOpaque(false);
        panelTablero1.setBorder(BorderFactory.createLineBorder(new Color(255, 69, 0), 3));

        JPanel panelPowerUps1 = getPowerUps(j1PowerUps);
        panelPowerUps1.setOpaque(false);

        panelJuego1.add(panelTablero1, BorderLayout.CENTER);
        panelJuego1.add(panelPowerUps1, BorderLayout.SOUTH);
        panelJuego1.setOpaque(false);

        // Mini tablero propio del Jugador 2
        JPanel panelMini = new JPanel(new BorderLayout(5, 5));
        panelMini.setOpaque(false);
        JLabel labelMini = new JLabel("TUS BARCOS (J2)", SwingConstants.CENTER);
        labelMini.setFont(new Font("Arial", Font.BOLD, 14));
        labelMini.setForeground(new Color(255, 69, 0));
        panelMini.add(labelMini, BorderLayout.NORTH);

        crearMiniTablero(miniTableroj2);
        JPanel miniTablero = new JPanel(new GridLayout(10, 10, 1, 1));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                miniTablero.add(miniTableroj2[i][j]);
            }
        }
        miniTablero.setBorder(BorderFactory.createLineBorder(new Color(255, 69, 0), 2));
        panelMini.add(miniTablero, BorderLayout.CENTER);

        panelCentral.add(panelJuego1, BorderLayout.CENTER);
        panelCentral.add(panelMini, BorderLayout.EAST);

        panelTotal.add(panelSuperior, BorderLayout.NORTH);
        panelTotal.add(panelCentral, BorderLayout.CENTER);
        panelTotal.setOpaque(false);

        panelJugador1Cache = panelTotal;
        return panelTotal;
    }

    public JPanel switchJugador2() {
        // Este panel es para el JUGADOR 1 disparando al tablero del JUGADOR 2
        if (panelJugador2Cache != null) {
            actualizarLabelTurno("JUGADOR 1");
            return panelJugador2Cache;
        }

        JPanel panelTotal2 = new JPanel(new BorderLayout(10, 10));
        panelTotal2.setPreferredSize(new Dimension(900, 700));

        // Panel superior con título del jugador
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        JLabel tituloJugador = new JLabel("ATACANDO AL JUGADOR 2", SwingConstants.CENTER);
        tituloJugador.setFont(new Font("Arial", Font.BOLD, 28));
        tituloJugador.setForeground(new Color(0, 100, 200)); // Azul para J1
        tituloJugador.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelSuperior.add(tituloJugador, BorderLayout.NORTH);

        // Label de turno
        labelTurno = new JLabel("TURNO: JUGADOR 1", SwingConstants.CENTER);
        labelTurno.setFont(new Font("Arial", Font.BOLD, 24));
        labelTurno.setForeground(new Color(0, 100, 200));
        labelTurno.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 100, 200), 3),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        labelTurno.setOpaque(true);
        labelTurno.setBackground(new Color(255, 255, 200));
        panelSuperior.add(labelTurno, BorderLayout.CENTER);

        // Label para contador de barcos enemigo
        labelBarcosEnemigo = new JLabel("BARCOS ENEMIGOS: --", SwingConstants.CENTER);
        labelBarcosEnemigo.setFont(new Font("Arial", Font.BOLD, 18));
        labelBarcosEnemigo.setForeground(new Color(255, 255, 255));
        labelBarcosEnemigo.setOpaque(true);
        labelBarcosEnemigo.setBackground(new Color(0, 100, 200));
        labelBarcosEnemigo.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        panelSuperior.add(labelBarcosEnemigo, BorderLayout.SOUTH);

        // Panel central que contendrá el tablero principal y el mini tablero
        JPanel panelCentral = new JPanel(new BorderLayout(20, 20));
        panelCentral.setOpaque(false);

        JPanel panelJuego2 = new JPanel(new BorderLayout(30, 30));
        panelJuego2.setPreferredSize(new Dimension(600, 600));

        JPanel panelTablero2 = getTablero(tableroj2);
        panelTablero2.setOpaque(false);
        panelTablero2.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 200), 3));

        JPanel panelPowerUps2 = getPowerUps(j2PowerUps);
        panelPowerUps2.setOpaque(false);

        panelJuego2.add(panelTablero2, BorderLayout.CENTER);
        panelJuego2.add(panelPowerUps2, BorderLayout.SOUTH);
        panelJuego2.setOpaque(false);

        // Mini tablero mostrando tus propios barcos (Jugador 1)
        JPanel panelMini = new JPanel(new BorderLayout(5, 5));
        panelMini.setOpaque(false);
        JLabel labelMini = new JLabel("TUS BARCOS (J1)", SwingConstants.CENTER);
        labelMini.setFont(new Font("Arial", Font.BOLD, 14));
        labelMini.setForeground(new Color(0, 100, 200));
        panelMini.add(labelMini, BorderLayout.NORTH);

        crearMiniTablero(miniTableroj1);
        JPanel miniTablero = new JPanel(new GridLayout(10, 10, 1, 1));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                miniTablero.add(miniTableroj1[i][j]);
            }
        }
        miniTablero.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 200), 2));
        panelMini.add(miniTablero, BorderLayout.CENTER);

        panelCentral.add(panelJuego2, BorderLayout.CENTER);
        panelCentral.add(panelMini, BorderLayout.EAST);

        panelTotal2.add(panelSuperior, BorderLayout.NORTH);
        panelTotal2.add(panelCentral, BorderLayout.CENTER);
        panelTotal2.setOpaque(false);

        panelJugador2Cache = panelTotal2;
        return panelTotal2;
    };

    // ---Cambio Pantalla Juego---

    public void construtorPanelPrincipal() {
        // ----Jugador 1----
        crearTablero(tableroj1);
        crearPanelPowerUps(j1PowerUps);

        // ----Jugador 2----
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

    // Método para actualizar el label de turno
    public void actualizarLabelTurno(String jugador) {
        if (labelTurno != null) {
            labelTurno.setText("TURNO: " + jugador);
            if (jugador.equals("JUGADOR 1")) {
                labelTurno.setForeground(new Color(0, 100, 200));
                labelTurno.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 100, 200), 3),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)));
            } else {
                labelTurno.setForeground(new Color(255, 69, 0));
                labelTurno.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(255, 69, 0), 3),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)));
            }
        }
    }

    // Método para actualizar el contador de barcos enemigos
    public void actualizarContadorBarcos(int cantidad) {
        if (labelBarcosEnemigo != null) {
            labelBarcosEnemigo.setText("BARCOS ENEMIGOS: " + cantidad + " casillas");
        }
    }

    // Método para sincronizar el mini tablero con el tablero principal
    public void actualizarMiniTablero(int jugador, int fila, int col, boolean tieneBarco, boolean fueDisparada) {
        JButton[][] mini = (jugador == 1) ? miniTableroj1 : miniTableroj2;

        if (mini[fila][col] != null) {
            if (tieneBarco && !fueDisparada) {
                // Mostrar barco propio en gris
                mini[fila][col].setBackground(new Color(100, 100, 100));
            } else if (fueDisparada && tieneBarco) {
                // Impacto
                mini[fila][col].setBackground(Color.RED);
                mini[fila][col].setText("X");
                mini[fila][col].setFont(new Font("Arial", Font.BOLD, 10));
            } else if (fueDisparada && !tieneBarco) {
                // Agua
                mini[fila][col].setBackground(new Color(30, 144, 255));
                mini[fila][col].setText("~");
            }
        }
    }

    // Getters para los mini tableros
    public JButton[][] getMiniTableroj1() {
        return miniTableroj1;
    }

    public JButton[][] getMiniTableroj2() {
        return miniTableroj2;
    }

    // Panel de colocación de barcos
    public JPanel getPanelColocacionBarcos(int jugador, ActionListener colocacionListener,
            ActionListener confirmarListener) {
        JPanel panelTotal = new JPanel(new BorderLayout(10, 10));
        panelTotal.setOpaque(false);

        // Título
        JLabel titulo = new JLabel("JUGADOR " + jugador + " - COLOCA TUS BARCOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(jugador == 1 ? new Color(0, 100, 200) : new Color(200, 0, 100));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Panel de información
        infoBarcos = new JLabel("Coloca tu barco de tamaño: 5", SwingConstants.CENTER);
        infoBarcos.setFont(new Font("Arial", Font.BOLD, 18));
        infoBarcos.setForeground(Color.BLACK);
        infoBarcos.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Panel central con tablero y controles
        JPanel panelCentral = new JPanel(new BorderLayout(20, 20));
        panelCentral.setOpaque(false);

        // Tablero
        JButton[][] tableroActual = (jugador == 1) ? tableroj1 : tableroj2;
        JPanel tablero = getTablero(tableroActual);
        tablero.setBorder(
                BorderFactory.createLineBorder(jugador == 1 ? new Color(0, 100, 200) : new Color(200, 0, 100), 3));

        // Agregar listeners al tablero
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tableroActual[i][j].setActionCommand(i + "," + j);
                tableroActual[i][j].addActionListener(colocacionListener);
            }
        }

        // Panel de controles
        JPanel panelControles = new JPanel(new GridLayout(4, 1, 10, 10));
        panelControles.setOpaque(false);

        botonRotar = new JButton("Rotar Barco (Horizontal)");
        botonRotar.setFont(new Font("Arial", Font.BOLD, 16));
        botonRotar.setBackground(new Color(100, 150, 250));
        botonRotar.setForeground(Color.WHITE);
        botonRotar.setFocusPainted(false);
        botonRotar.addActionListener(e -> {
            orientacionHorizontal = !orientacionHorizontal;
            botonRotar.setText("Rotar Barco (" + (orientacionHorizontal ? "Horizontal" : "Vertical") + ")");
        });

        botonEliminar = new JButton("Eliminar Último Barco");
        botonEliminar.setFont(new Font("Arial", Font.BOLD, 16));
        botonEliminar.setBackground(new Color(250, 100, 100));
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.setFocusPainted(false);
        botonEliminar.setEnabled(false);

        botonConfirmar = new JButton("Confirmar Barcos");
        botonConfirmar.setFont(new Font("Arial", Font.BOLD, 16));
        botonConfirmar.setBackground(new Color(50, 200, 50));
        botonConfirmar.setForeground(Color.WHITE);
        botonConfirmar.setFocusPainted(false);
        botonConfirmar.setEnabled(false);
        botonConfirmar.addActionListener(confirmarListener);

        JLabel instrucciones = new JLabel(
                "<html><center>Haz clic en el tablero para colocar tus barcos<br>Barcos: 1x5, 1x4, 2x3, 1x2</center></html>",
                SwingConstants.CENTER);
        instrucciones.setFont(new Font("Arial", Font.PLAIN, 14));

        panelControles.add(botonRotar);
        panelControles.add(botonEliminar);
        panelControles.add(botonConfirmar);
        panelControles.add(instrucciones);

        panelCentral.add(tablero, BorderLayout.CENTER);
        panelCentral.add(panelControles, BorderLayout.EAST);

        panelTotal.add(titulo, BorderLayout.NORTH);
        panelTotal.add(infoBarcos, BorderLayout.CENTER);
        panelTotal.add(panelCentral, BorderLayout.SOUTH);

        return panelTotal;
    }

    // Actualizar info de colocación
    public void actualizarInfoBarcos(String mensaje) {
        if (infoBarcos != null) {
            infoBarcos.setText(mensaje);
        }
    }

    // Habilitar botón confirmar
    public void habilitarBotonConfirmar(boolean enabled) {
        if (botonConfirmar != null) {
            botonConfirmar.setEnabled(enabled);
        }
    }

    // Habilitar botón eliminar
    public void habilitarBotonEliminar(boolean enabled) {
        if (botonEliminar != null) {
            botonEliminar.setEnabled(enabled);
        }
    }

    // Getter para el botón eliminar
    public JButton getBotonEliminar() {
        return botonEliminar;
    }

    // Getters para orientación y barcos
    public boolean getOrientacionHorizontal() {
        return orientacionHorizontal;
    }

    public int[] getBarcosRestantes() {
        return barcosRestantes;
    }

    public int getIndiceBarcActual() {
        return indiceBarcActual;
    }

    public void setIndiceBarcActual(int indice) {
        this.indiceBarcActual = indice;
    }

    public void resetColocacion() {
        indiceBarcActual = 0;
        orientacionHorizontal = true;
    }

    public void limpiarCache() {
        panelJugador1Cache = null;
        panelJugador2Cache = null;
    }

    // Pantalla de transición entre turnos
    public JPanel getPanelTransicion(int jugadorSiguiente, Runnable continuarCallback) {
        JPanel panelTransicion = new JPanel(new GridBagLayout());
        panelTransicion.setOpaque(true);
        panelTransicion.setBackground(new Color(50, 50, 50));

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);

        // Mensaje de cambio de turno
        JLabel mensajeCambio = new JLabel("CAMBIO DE TURNO");
        mensajeCambio.setFont(new Font("Arial", Font.BOLD, 36));
        mensajeCambio.setForeground(new Color(255, 200, 0));
        mensajeCambio.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título grande
        JLabel titulo = new JLabel("TURNO DEL JUGADOR " + jugadorSiguiente);
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(jugadorSiguiente == 1 ? new Color(100, 200, 255) : new Color(255, 100, 150));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        JLabel subtitulo = new JLabel("Prepárate para atacar...");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 24));
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Advertencia
        JLabel advertencia = new JLabel("¡Que el otro jugador no vea!");
        advertencia.setFont(new Font("Arial", Font.BOLD, 20));
        advertencia.setForeground(new Color(255, 100, 100));
        advertencia.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón continuar
        JButton botonContinuar = new JButton("¡ESTOY LISTO!");
        botonContinuar.setFont(new Font("Arial", Font.BOLD, 32));
        botonContinuar.setBackground(jugadorSiguiente == 1 ? new Color(0, 100, 200) : new Color(200, 0, 100));
        botonContinuar.setForeground(Color.WHITE);
        botonContinuar.setFocusPainted(false);
        botonContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonContinuar.setPreferredSize(new Dimension(300, 80));
        botonContinuar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        botonContinuar.addActionListener(e -> continuarCallback.run());

        // Espaciado
        contenido.add(Box.createVerticalGlue());
        contenido.add(mensajeCambio);
        contenido.add(Box.createRigidArea(new Dimension(0, 20)));
        contenido.add(titulo);
        contenido.add(Box.createRigidArea(new Dimension(0, 30)));
        contenido.add(subtitulo);
        contenido.add(Box.createRigidArea(new Dimension(0, 20)));
        contenido.add(advertencia);
        contenido.add(Box.createRigidArea(new Dimension(0, 50)));
        contenido.add(botonContinuar);
        contenido.add(Box.createVerticalGlue());

        panelTransicion.add(contenido);

        return panelTransicion;
    }

    // Mostrar mensaje de "¡IMPACTO! Sigue jugando"
    public void mostrarMensajeImpacto(int jugador) {
        JLabel mensaje = new JLabel("¡IMPACTO! ¡VUELVE A DISPARAR!");
        mensaje.setFont(new Font("Arial", Font.BOLD, 32));
        mensaje.setForeground(Color.WHITE);
        mensaje.setOpaque(true);
        mensaje.setBackground(new Color(255, 100, 0, 230)); // Naranja semi-transparente
        mensaje.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.YELLOW, 4),
                BorderFactory.createEmptyBorder(20, 40, 20, 40)));
        mensaje.setHorizontalAlignment(SwingConstants.CENTER);

        // Posicionar en el centro de la ventana
        mensaje.setBounds(
                (getWidth() - 600) / 2,
                (getHeight() - 100) / 2,
                600,
                100);

        // Agregar al glasspane para superposición
        JPanel glassPane = new JPanel(null);
        glassPane.setOpaque(false);
        glassPane.add(mensaje);
        setGlassPane(glassPane);
        glassPane.setVisible(true);

        // Animación de desvanecimiento
        Timer timer = new Timer(50, null);
        final int[] contador = { 0 };
        timer.addActionListener(e -> {
            if (contador[0] < 30) { // Mostrar por 1.5 segundos
                contador[0]++;
            } else {
                glassPane.setVisible(false);
                timer.stop();
            }
        });
        timer.start();
    }

    // ---Clase Auxiliar BG ---
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
                    if (res != null)
                        background = ImageIO.read(res);
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
        final int[] index = { 0 }; // contador interno

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

    // Mostrar animación de impacto (explosión)
    public void mostrarAnimacionImpacto(JButton boton) {
        Timer timer = new Timer(100, null);
        final int[] contador = { 0 };

        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (contador[0] < 6) {
                    // Alternar entre rojo intenso y naranja para efecto de explosión
                    if (contador[0] % 2 == 0) {
                        boton.setBackground(new Color(255, 50, 0)); // Rojo intenso
                        boton.setText("💥");
                        boton.setFont(new Font("Arial", Font.BOLD, 30));
                    } else {
                        boton.setBackground(new Color(255, 150, 0)); // Naranja
                        boton.setText("🔥");
                    }
                    contador[0]++;
                } else {
                    // Estado final
                    boton.setBackground(Color.RED);
                    boton.setText("X");
                    boton.setFont(new Font("Arial", Font.BOLD, 20));
                    boton.setForeground(Color.WHITE);
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    // Método para mostrar pantalla de victoria o derrota
    public void mostrarPantallaFinal(String ganador, boolean esBot) {
        getContentPane().removeAll();

        JPanel panelFinal = new JPanel(new GridBagLayout());
        panelFinal.setBackground(ganador.equals("JUGADOR 1") ? new Color(0, 100, 200) : new Color(255, 69, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        // Título principal
        JLabel labelResultado = new JLabel();
        if (esBot) {
            // Modo bot: solo puede ganar o perder el jugador 1
            if (ganador.equals("JUGADOR 1")) {
                labelResultado.setText("¡VICTORIA! 🎉");
                labelResultado.setForeground(new Color(255, 215, 0)); // Dorado
            } else {
                labelResultado.setText("¡DERROTA! 💀");
                labelResultado.setForeground(new Color(200, 0, 0)); // Rojo oscuro
            }
        } else {
            // Modo 2 jugadores
            labelResultado.setText("¡" + ganador + " GANA! 🏆");
            labelResultado.setForeground(Color.WHITE);
        }
        labelResultado.setFont(new Font("Arial", Font.BOLD, 60));
        panelFinal.add(labelResultado, gbc);

        // Mensaje secundario
        gbc.gridy = 1;
        JLabel labelMensaje = new JLabel(ganador + " ha hundido toda la flota enemiga");
        labelMensaje.setFont(new Font("Arial", Font.PLAIN, 24));
        labelMensaje.setForeground(Color.WHITE);
        panelFinal.add(labelMensaje, gbc);

        // Animación de emojis
        gbc.gridy = 2;
        JLabel labelEmoji = new JLabel();
        labelEmoji.setFont(new Font("Arial", Font.PLAIN, 80));
        panelFinal.add(labelEmoji, gbc);

        // Animación alternando emojis
        Timer timerAnimacion = new Timer(500, null);
        final int[] contadorAnim = { 0 };
        String[] emojis = ganador.equals("JUGADOR 1") || !esBot ? new String[] { "🎊", "🎉", "⚓", "🚢" }
                : new String[] { "💣", "💥", "🌊", "⚓" };

        timerAnimacion.addActionListener(e -> {
            labelEmoji.setText(emojis[contadorAnim[0] % emojis.length]);
            contadorAnim[0]++;
        });
        timerAnimacion.start();

        add(panelFinal);
        revalidate();
        repaint();
    }
}
