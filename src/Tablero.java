import java.util.Random;

public class Tablero {
    protected Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[10][10]; // <-- inicializo la matriz

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                casillas[i][j] = new Casilla(); // ahora sí se puede
            }
        }
    }

    // Getter para acceder a una casilla
    public Casilla getCasilla(int fila, int col) {
        return casillas[fila][col];
    }
    public void crearBarcos(){
        //----Llamo a la matriz del trablero y hago un num random entre 0 y 9-----

        Casilla[][] a = casillas;
        //int random = (int) (Math.random() * 10);
        int random = 0;

        //----Recorro la matriz poniendo los barcos en false---
        for(int i =0; i<=9; i ++){
            for(int j = 0; j<=9; j++){
                a[i][j].setTieneBarco(false);
            }
        }

        switch (random) {
            case 0 -> {
                for (int j = 0; j < 5; j++) a[0][j].setTieneBarco(true);
                for (int i = 2; i < 6; i++) a[i][9].setTieneBarco(true);
                for (int j = 4; j < 7; j++) a[5][j].setTieneBarco(true); // horizontal
                for (int i = 7; i < 10; i++) a[i][2].setTieneBarco(true); // vertical

                // Barco de 2 (diagonal)
                a[8][7].setTieneBarco(true);
                a[9][8].setTieneBarco(true);
            }
            case 1 -> {
                for (int i = 0; i < 5; i++) a[i][0].setTieneBarco(true); // vertical
                for (int j = 5; j < 9; j++) a[4][j].setTieneBarco(true); // horizontal
                for (int j = 2; j < 5; j++) a[6][j].setTieneBarco(true);
                for (int i = 7; i < 10; i++) a[i][9 - i].setTieneBarco(true); // diagonal invertida
                a[9][0].setTieneBarco(true);
                a[9][1].setTieneBarco(true);
            }
            case 2 -> {
                for (int j = 2; j < 7; j++) a[0][j].setTieneBarco(true);
                for (int i = 3; i < 7; i++) a[i][2].setTieneBarco(true);
                for (int j = 6; j < 9; j++) a[6][j].setTieneBarco(true);
                for (int i = 7; i < 10; i++) a[i][7].setTieneBarco(true);
                a[4][8].setTieneBarco(true);
                a[5][9].setTieneBarco(true);
            }
            case 3 -> {
                for (int i = 5; i < 10; i++) a[i][0].setTieneBarco(true);
                for (int j = 4; j < 8; j++) a[1][j].setTieneBarco(true);
                for (int i = 2; i < 5; i++) a[i][5].setTieneBarco(true);
                for (int j = 6; j < 9; j++) a[9][j].setTieneBarco(true);
                a[3][0].setTieneBarco(true);
                a[4][1].setTieneBarco(true);
            }
            case 4 -> {
                for (int j = 5; j < 10; j++) a[9][j].setTieneBarco(true);
                for (int i = 0; i < 4; i++) a[i][0].setTieneBarco(true);
                for (int j = 3; j < 6; j++) a[5][j].setTieneBarco(true);
                for (int i = 3; i < 6; i++) a[i][8].setTieneBarco(true);
                a[2][2].setTieneBarco(true);
                a[3][3].setTieneBarco(true);
            }
            case 5 -> {
                for (int j = 1; j < 6; j++) a[2][j].setTieneBarco(true);
                for (int i = 4; i < 8; i++) a[i][9].setTieneBarco(true);
                for (int j = 0; j < 3; j++) a[7][j].setTieneBarco(true);
                for (int i = 5; i < 8; i++) a[i][5].setTieneBarco(true);
                a[9][3].setTieneBarco(true);
                a[8][4].setTieneBarco(true);
            }
            case 6 -> {
                for (int i = 0; i < 5; i++) a[i][4].setTieneBarco(true);
                for (int j = 0; j < 4; j++) a[9][j].setTieneBarco(true);
                for (int j = 3; j < 6; j++) a[3][j].setTieneBarco(true);
                for (int i = 6; i < 9; i++) a[i][8].setTieneBarco(true);
                a[5][5].setTieneBarco(true);
                a[6][6].setTieneBarco(true);
            }
            case 7 -> {
                for (int j = 0; j < 5; j++) a[9][j].setTieneBarco(true);
                for (int i = 0; i < 4; i++) a[i][6].setTieneBarco(true);
                for (int j = 6; j < 9; j++) a[5][j].setTieneBarco(true);
                for (int i = 6; i < 9; i++) a[i][3].setTieneBarco(true);
                a[0][9].setTieneBarco(true);
                a[1][8].setTieneBarco(true);
            }
            case 8 -> {
                for (int j = 2; j < 7; j++) a[3][j].setTieneBarco(true);
                for (int i = 6; i < 10; i++) a[i][0].setTieneBarco(true);
                for (int j = 5; j < 8; j++) a[8][j].setTieneBarco(true);
                for (int i = 0; i < 3; i++) a[i][9 - i].setTieneBarco(true);
                a[5][5].setTieneBarco(true);
                a[6][6].setTieneBarco(true);
            }
            case 9 -> {
                for (int i = 0; i < 5; i++) a[i][9].setTieneBarco(true);
                for (int j = 3; j < 7; j++) a[4][j].setTieneBarco(true);
                for (int i = 7; i < 10; i++) a[i][4].setTieneBarco(true);
                for (int j = 0; j < 3; j++) a[0][j].setTieneBarco(true);
                a[9][7].setTieneBarco(true);
                a[8][8].setTieneBarco(true);
            }
        }


    }

}

