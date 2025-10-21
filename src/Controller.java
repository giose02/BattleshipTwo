import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Controller implements ActionListener {
    private BattleShipGUI vista;
    private Juego juego = new Juego();
    private int jugadorColocando = 1; // 1 o 2
    private boolean faseColocacion = true;
    private boolean juegoIniciado = false;
    private boolean modoBot = false; // true si J2 es bot

    // Lista para rastrear posiciones de barcos colocados [jugador][barco][casillas]
    private List<List<int[]>> barcosColocadosJ1 = new ArrayList<>();
    private List<List<int[]>> barcosColocadosJ2 = new ArrayList<>();

    // Estado del bot: cola de objetivos cercanos y dirección actual
    private Deque<int[]> objetivosBot = new ArrayDeque<>();
    private Integer ultimoImpactoFila = null;
    private Integer ultimoImpactoCol = null;
    private Integer dirFila = null;
    private Integer dirCol = null;

    public Controller() {
        vista = new BattleShipGUI();
        JPanel panelInicio = vista.getPanelInicio();
        vista.add(panelInicio);

        // NO crear barcos automáticamente - los jugadores los colocarán

        comenzarJuego();
    }

    private void comenzarJuego() {
        vista.getStartButton().addActionListener(e -> {
            // Detectar modo de juego
            modoBot = vista.isModo1Jugador();

            vista.remove(vista.getPanelInicio());
            vista.construtorPanelPrincipal();

            // Si es modo bot, crear barcos del bot automáticamente
            if (modoBot) {
                juego.getTableroJugador2().crearBarcos();
            }

            // Iniciar fase de colocación para jugador 1
            mostrarPanelColocacion(1);

            vista.revalidate();
            vista.repaint();
        });
    }

    private void mostrarPanelColocacion(int jugador) {
        vista.resetColocacion();
        JPanel panelColocacion = vista.getPanelColocacionBarcos(jugador,
                new ColocacionListener(jugador),
                new ConfirmarListener(jugador));

        // Agregar listener al botón de eliminar
        vista.getBotonEliminar().addActionListener(new EliminarListener(jugador));

        vista.add(panelColocacion);
        vista.revalidate();
        vista.repaint();
    }

    // Listener para colocar barcos
    private class ColocacionListener implements ActionListener {
        private int jugador;

        public ColocacionListener(int jugador) {
            this.jugador = jugador;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] partes = e.getActionCommand().split(",");
            int fila = Integer.parseInt(partes[0]);
            int col = Integer.parseInt(partes[1]);

            Tablero tablero = (jugador == 1) ? juego.getTableroJugador1() : juego.getTableroJugador2();
            JButton[][] botonesTablero = (jugador == 1) ? vista.getTableroj1() : vista.getTableroj2();

            int[] barcosRestantes = vista.getBarcosRestantes();
            int indiceActual = vista.getIndiceBarcActual();

            if (indiceActual >= barcosRestantes.length) {
                return; // Ya se colocaron todos los barcos
            }

            int tamañoBarco = barcosRestantes[indiceActual];
            boolean horizontal = vista.getOrientacionHorizontal();

            // Verificar si se puede colocar el barco
            if (puedeColocarBarco(tablero, fila, col, tamañoBarco, horizontal)) {
                // Colocar el barco
                colocarBarco(tablero, botonesTablero, fila, col, tamañoBarco, horizontal);

                // Guardar las posiciones del barco colocado
                List<int[]> posicionesBarco = new ArrayList<>();
                for (int i = 0; i < tamañoBarco; i++) {
                    int f = horizontal ? fila : fila + i;
                    int c = horizontal ? col + i : col;
                    posicionesBarco.add(new int[] { f, c });
                }

                if (jugador == 1) {
                    barcosColocadosJ1.add(posicionesBarco);
                } else {
                    barcosColocadosJ2.add(posicionesBarco);
                }

                // Actualizar mini tablero
                for (int i = 0; i < tamañoBarco; i++) {
                    int f = horizontal ? fila : fila + i;
                    int c = horizontal ? col + i : col;
                    vista.actualizarMiniTablero(jugador, f, c, true, false);
                }

                // Avanzar al siguiente barco
                vista.setIndiceBarcActual(indiceActual + 1);
                vista.habilitarBotonEliminar(true); // Habilitar botón eliminar

                if (vista.getIndiceBarcActual() < barcosRestantes.length) {
                    vista.actualizarInfoBarcos(
                            "Coloca tu barco de tamaño: " + barcosRestantes[vista.getIndiceBarcActual()]);
                } else {
                    vista.actualizarInfoBarcos("¡Todos los barcos colocados! Haz clic en Confirmar");
                    vista.habilitarBotonConfirmar(true);
                }
            } else {
                JOptionPane.showMessageDialog(vista,
                        "No se puede colocar el barco ahí. Verifica el espacio y orientación.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Listener para eliminar el último barco colocado
    private class EliminarListener implements ActionListener {
        private int jugador;

        public EliminarListener(int jugador) {
            this.jugador = jugador;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<List<int[]>> barcosColocados = (jugador == 1) ? barcosColocadosJ1 : barcosColocadosJ2;

            if (barcosColocados.isEmpty()) {
                return; // No hay barcos para eliminar
            }

            // Obtener el último barco colocado
            List<int[]> ultimoBarco = barcosColocados.remove(barcosColocados.size() - 1);

            Tablero tablero = (jugador == 1) ? juego.getTableroJugador1() : juego.getTableroJugador2();
            JButton[][] botonesTablero = (jugador == 1) ? vista.getTableroj1() : vista.getTableroj2();

            // Eliminar el barco del tablero
            for (int[] pos : ultimoBarco) {
                int f = pos[0];
                int c = pos[1];
                tablero.getCasilla(f, c).setTieneBarco(false);
                botonesTablero[f][c].setBackground(new Color(135, 206, 250)); // Restaurar color agua
                vista.actualizarMiniTablero(jugador, f, c, false, false);
            }

            // Retroceder el índice del barco actual
            int nuevoIndice = barcosColocados.size();
            vista.setIndiceBarcActual(nuevoIndice);

            if (nuevoIndice < vista.getBarcosRestantes().length) {
                vista.actualizarInfoBarcos("Coloca tu barco de tamaño: " + vista.getBarcosRestantes()[nuevoIndice]);
                vista.habilitarBotonConfirmar(false);
            }

            // Deshabilitar botón eliminar si no hay más barcos
            if (barcosColocados.isEmpty()) {
                vista.habilitarBotonEliminar(false);
            }
        }
    }

    // Listener para confirmar barcos
    private class ConfirmarListener implements ActionListener {
        private int jugador;

        public ConfirmarListener(int jugador) {
            this.jugador = jugador;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            vista.getContentPane().removeAll();

            if (jugador == 1) {
                // Limpiar lista de barcos del jugador 1 (ya confirmados)
                if (modoBot) {
                    // Si es modo bot, saltar al inicio del juego directamente
                    iniciarPartida();
                } else {
                    // Pasar al jugador 2
                    mostrarPanelColocacion(2);
                }
            } else {
                // Limpiar lista de barcos del jugador 2 (ya confirmados)
                // Ambos jugadores terminaron, iniciar partida
                iniciarPartida();
            }
        }
    }

    private void iniciarPartida() {
        faseColocacion = false;
        juegoIniciado = true;

        // OCULTAR barcos en los tableros principales (volver a color agua)
        ocultarBarcos(juego.getTableroJugador1(), vista.getTableroj1());
        ocultarBarcos(juego.getTableroJugador2(), vista.getTableroj2());

        // Mostrar siempre el tablero correspondiente al turno actual de forma
        // consistente
        mostrarPantallaTurnoActual();
        vista.addTableroListener(this);
    }

    // Método para ocultar barcos (volver a color agua) en los tableros principales
    private void ocultarBarcos(Tablero tablero, JButton[][] botones) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero.getCasilla(i, j).getTieneBarco()) {
                    botones[i][j].setBackground(new Color(135, 206, 250)); // Color agua
                }
            }
        }
    }

    private boolean puedeColocarBarco(Tablero tablero, int fila, int col, int tamaño, boolean horizontal) {
        // Verificar límites
        if (horizontal) {
            if (col + tamaño > 10)
                return false;
        } else {
            if (fila + tamaño > 10)
                return false;
        }

        // Verificar que las casillas estén libres
        for (int i = 0; i < tamaño; i++) {
            int f = horizontal ? fila : fila + i;
            int c = horizontal ? col + i : col;
            if (tablero.getCasilla(f, c).getTieneBarco()) {
                return false;
            }
        }

        return true;
    }

    private void colocarBarco(Tablero tablero, JButton[][] botones, int fila, int col, int tamaño, boolean horizontal) {
        for (int i = 0; i < tamaño; i++) {
            int f = horizontal ? fila : fila + i;
            int c = horizontal ? col + i : col;
            tablero.getCasilla(f, c).setTieneBarco(true);
            botones[f][c].setBackground(new Color(100, 100, 100)); // Gris para barco
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (!juegoIniciado)
            return;

        String[] partes = e.getActionCommand().split(",");
        int fila = Integer.parseInt(partes[0]);
        int col = Integer.parseInt(partes[1]);

        if (juego.getTurnoActual()) {
            // Turno Jugador 1 - dispara al tablero del Jugador 2
            if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada()
                    && !juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                // Agua - no hay barco - CAMBIAR TURNO
                juego.getTableroJugador2().getCasilla(fila, col).disparar();
                vista.getTableroj2()[fila][col].setBackground(new Color(30, 144, 255));
                vista.getTableroj2()[fila][col].setText("~");
                vista.getTableroj2()[fila][col].setEnabled(false);

                // Actualizar mini tablero del jugador 2
                vista.actualizarMiniTablero(2, fila, col, false, true);

                String[] imagenes = { "src/resources/anim1.jpg", "src/resources/anim2.jpg", "src/resources/anim3.jpg" };
                vista.mostrarSecuenciaImagenes(vista.getTableroj2()[fila][col], imagenes, 600);

                // Cambiar turno después de un fallo
                juego.cambiarTurno();

                // Mostrar pantalla de transición después de un breve delay
                Timer timer = new Timer(1500, ev -> mostrarTransicion());
                timer.setRepeats(false);
                timer.start();

            } else if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                // Impacto - hay barco - SEGUIR JUGANDO
                juego.getTableroJugador2().getCasilla(fila, col).disparar();
                vista.getTableroj2()[fila][col].setEnabled(false);

                // Mostrar animación de impacto
                vista.mostrarAnimacionImpacto(vista.getTableroj2()[fila][col]);

                // Actualizar mini tablero del jugador 2
                vista.actualizarMiniTablero(2, fila, col, true, true);

                // Actualizar contador de barcos
                int barcosRestantes = juego.getTableroJugador2().contarBarcosRestantes();
                vista.actualizarContadorBarcos(barcosRestantes);

                // Verificar si ganó
                if (barcosRestantes == 0) {
                    Timer timerVictoria = new Timer(2000, ev -> {
                        ((Timer) ev.getSource()).stop();
                        vista.mostrarPantallaFinal("JUGADOR 1", modoBot);
                    });
                    timerVictoria.setRepeats(false);
                    timerVictoria.start();
                    return;
                }

                // Mostrar mensaje de impacto
                vista.mostrarMensajeImpacto(1);

                // NO cambiar turno - el jugador sigue jugando
            }

        } else {
            // Turno Jugador 2 - dispara al tablero del Jugador 1
            if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada()
                    && !juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                // Agua - no hay barco - CAMBIAR TURNO
                juego.getTableroJugador1().getCasilla(fila, col).disparar();
                vista.getTableroj1()[fila][col].setBackground(new Color(30, 144, 255));
                vista.getTableroj1()[fila][col].setText("~");
                vista.getTableroj1()[fila][col].setEnabled(false);

                // Actualizar mini tablero del jugador 1
                vista.actualizarMiniTablero(1, fila, col, false, true);

                String[] imagenes = { "src/resources/anim1.jpg", "src/resources/anim2.jpg", "src/resources/anim3.jpg" };
                vista.mostrarSecuenciaImagenes(vista.getTableroj1()[fila][col], imagenes, 600);

                // Cambiar turno después de un fallo
                juego.cambiarTurno();

                // Mostrar pantalla de transición después de un breve delay
                Timer timer = new Timer(1500, ev -> mostrarTransicion());
                timer.setRepeats(false);
                timer.start();

            } else if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                // Impacto - hay barco - SEGUIR JUGANDO
                juego.getTableroJugador1().getCasilla(fila, col).disparar();
                vista.getTableroj1()[fila][col].setEnabled(false);

                // Mostrar animación de impacto
                vista.mostrarAnimacionImpacto(vista.getTableroj1()[fila][col]);

                // Actualizar mini tablero del jugador 1
                vista.actualizarMiniTablero(1, fila, col, true, true);

                // Actualizar contador de barcos
                int barcosRestantes = juego.getTableroJugador1().contarBarcosRestantes();
                vista.actualizarContadorBarcos(barcosRestantes);

                // Verificar si ganó
                if (barcosRestantes == 0) {
                    Timer timerVictoria = new Timer(2000, ev -> {
                        ((Timer) ev.getSource()).stop();
                        vista.mostrarPantallaFinal("JUGADOR 2", modoBot);
                    });
                    timerVictoria.setRepeats(false);
                    timerVictoria.start();
                    return;
                }

                // Mostrar mensaje de impacto
                vista.mostrarMensajeImpacto(2);

                // NO cambiar turno - el jugador sigue jugando
            }
        }
    } // Método para mostrar la pantalla de transición

    private void mostrarTransicion() {
        int jugadorSiguiente = juego.getTurnoActual() ? 1 : 2;

        // Si es modo bot y el turno es del bot (jugador 2), disparar automáticamente
        if (modoBot && jugadorSiguiente == 2) {
            Timer timerBot = new Timer(1000, ev -> {
                ((Timer) ev.getSource()).stop();
                ejecutarDisparoBot();
            });
            timerBot.setRepeats(false);
            timerBot.start();
            return; // No mostrar pantalla de transición para el bot
        }

        vista.getContentPane().removeAll();

        JPanel panelTransicion = vista.getPanelTransicion(jugadorSiguiente, () -> {
            // Al confirmar el cambio de turno, mostramos siempre el tablero correcto
            mostrarPantallaTurnoActual();
        });

        vista.add(panelTransicion);
        vista.revalidate();
        vista.repaint();
    }

    // Método para ejecutar el disparo del bot
    private void ejecutarDisparoBot() {
        // Elegir objetivo: priorizar celdas cercanas a impactos anteriores
        int fila, col;

        int[] objetivo = obtenerSiguienteObjetivoBot();
        if (objetivo != null) {
            fila = objetivo[0];
            col = objetivo[1];
        } else {
            // Buscar una casilla aleatoria que no haya sido disparada
            do {
                fila = (int) (Math.random() * 10);
                col = (int) (Math.random() * 10);
            } while (juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada());
        }

        // Marcar como disparada
        juego.getTableroJugador1().getCasilla(fila, col).disparar();

        boolean esBarco = juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco();

        if (esBarco) {
            // El bot acertó
            vista.getTableroj1()[fila][col].setBackground(Color.RED);
            vista.getTableroj1()[fila][col].setText("X");
            vista.actualizarMiniTablero(1, fila, col, true, true);
            vista.mostrarAnimacionImpacto(vista.getTableroj1()[fila][col]);

            // Actualizar contador de barcos
            int barcosRestantes = juego.getTableroJugador1().contarBarcosRestantes();
            vista.actualizarContadorBarcos(barcosRestantes);

            // Verificar si el bot ganó
            if (barcosRestantes == 0) {
                Timer timerVictoria = new Timer(2000, ev -> {
                    ((Timer) ev.getSource()).stop();
                    vista.mostrarPantallaFinal("JUGADOR 2", modoBot);
                });
                timerVictoria.setRepeats(false);
                timerVictoria.start();
                return;
            }

            // Estrategia: tras un impacto, priorizar celdas adyacentes y dirección
            actualizarEstrategiaBotTrasImpacto(fila, col);

            // Mostrar mensaje que el bot sigue jugando
            Timer timerMensaje = new Timer(2000, ev -> {
                ((Timer) ev.getSource()).stop();
                // El bot sigue jugando
                ejecutarDisparoBot();
            });
            timerMensaje.setRepeats(false);
            timerMensaje.start();
        } else {
            // El bot falló - agua
            vista.getTableroj1()[fila][col].setBackground(new Color(30, 144, 255));
            vista.getTableroj1()[fila][col].setText("O");
            vista.actualizarMiniTablero(1, fila, col, false, true);

            // Cambiar turno después de un delay y volver a mostrar el tablero del jugador 1
            juego.cambiarTurno();
            Timer timerCambio = new Timer(1500, ev -> {
                ((Timer) ev.getSource()).stop();
                // Mostrar tablero consistente con el turno actual
                mostrarPantallaTurnoActual();
            });
            timerCambio.setRepeats(false);
            timerCambio.start();
        }
    }

    // Mostrar tablero consistente con el turno actual y actualizar contador
    private void mostrarPantallaTurnoActual() {
        vista.getContentPane().removeAll();
        vista.limpiarCache();
        if (juego.getTurnoActual()) {
            // Turno J1: dispara al tablero del J2
            vista.add(vista.switchJugador2());
            vista.actualizarContadorBarcos(juego.getTableroJugador2().contarBarcosRestantes());
        } else {
            // Turno J2: dispara al tablero del J1
            vista.add(vista.switchJugador1());
            vista.actualizarContadorBarcos(juego.getTableroJugador1().contarBarcosRestantes());
        }
        vista.revalidate();
        vista.repaint();
    }

    // Obtener siguiente celda objetivo del bot (prioriza cola de objetivos válidos)
    private int[] obtenerSiguienteObjetivoBot() {
        // Limpiar de la cola cualquier celda ya disparada
        while (!objetivosBot.isEmpty()) {
            int[] p = objetivosBot.peekFirst();
            if (!juego.getTableroJugador1().getCasilla(p[0], p[1]).getFueDisparada()) {
                return objetivosBot.pollFirst();
            } else {
                objetivosBot.pollFirst();
            }
        }
        // Si se vació la cola, resetear dirección si no hay más candidatos
        if (objetivosBot.isEmpty()) {
            dirFila = null;
            dirCol = null;
            // No borramos ultimoImpacto para permitir reintentos alrededor si aparece otro
            // impacto cercano
        }
        return null;
    }

    private void actualizarEstrategiaBotTrasImpacto(int fila, int col) {
        // Si no hay último impacto, este es el pivot inicial
        if (ultimoImpactoFila == null) {
            ultimoImpactoFila = fila;
            ultimoImpactoCol = col;
            // Encolar vecinos inmediatos
            encolarSiValido(fila - 1, col);
            encolarSiValido(fila + 1, col);
            encolarSiValido(fila, col - 1);
            encolarSiValido(fila, col + 1);
            return;
        }

        // Si ya había un impacto previo, intentar determinar dirección
        // (horizontal/vertical)
        if (dirFila == null && dirCol == null) {
            int df = fila - ultimoImpactoFila;
            int dc = col - ultimoImpactoCol;
            if (Math.abs(df) + Math.abs(dc) == 1) { // adyacente ortogonal
                dirFila = df;
                dirCol = dc;
            }
        }

        // Encolar la siguiente celda en la misma dirección, si la hay
        if (dirFila != null && dirCol != null) {
            int nf = fila + dirFila;
            int nc = col + dirCol;
            encolarSiValido(nf, nc);
        }

        // Actualizar último impacto
        ultimoImpactoFila = fila;
        ultimoImpactoCol = col;
    }

    private void encolarSiValido(int f, int c) {
        if (enTablero(f, c) && !juego.getTableroJugador1().getCasilla(f, c).getFueDisparada()) {
            objetivosBot.addLast(new int[] { f, c });
        }
    }

    private boolean enTablero(int f, int c) {
        return f >= 0 && f < 10 && c >= 0 && c < 10;
    }
}
