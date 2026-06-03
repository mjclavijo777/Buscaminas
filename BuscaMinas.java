/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package buscamin;

/**
 *
 * @author manue
 */
public class BuscaMinas implements IJuego, ITablero {

    private static final int MINA = -1;

    private int[][] tablero;
    private boolean[][] descubierto;
    private int filas;
    private int columnas;
    private int totalMinas;
    private boolean juegoTerminado;

    public BuscaMinas(int filas, int columnas, int totalMinas) {
        this.filas        = filas;
        this.columnas     = columnas;
        this.totalMinas   = totalMinas;
        this.tablero      = new int[filas][columnas];
        this.descubierto  = new boolean[filas][columnas];
        this.juegoTerminado = false;
    }

    @Override
    public void ubicarMina(int fila, int columna) {
        if (!validarMovimiento(fila, columna)) {
            System.out.println("Posición inválida para ubicar mina.");
            return;
        }
        if (tablero[fila][columna] == MINA) {
            System.out.println("Ya existe una mina en esa posición.");
            return;
        }

        tablero[fila][columna] = MINA;

        int[] dx = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dy = {-1,  0,  1, -1, 1, -1, 0, 1};

        for (int d = 0; d < 8; d++) {
            int nx = fila    + dx[d];
            int ny = columna + dy[d];
            if (nx >= 0 && nx < filas && ny >= 0 && ny < columnas) {
                if (tablero[nx][ny] != MINA) {
                    tablero[nx][ny]++;
                }
            }
        }
        System.out.println("Mina ubicada en (" + fila + ", " + columna + ")");
    }

    @Override
    public void realizarJugada(int fila, int columna) {
        if (juegoTerminado) {
            System.out.println("El juego ya terminó.");
            return;
        }
        if (!validarMovimiento(fila, columna)) {
            System.out.println("Movimiento inválido: posición fuera del tablero.");
            return;
        }
        if (descubierto[fila][columna]) {
            System.out.println("Esa celda ya fue descubierta.");
            return;
        }

        descubierto[fila][columna] = true;

        if (tablero[fila][columna] == MINA) {
            System.out.println("¡BOOM! Pisaste una mina en (" + fila + ", " + columna + "). Perdiste.");
            juegoTerminado = true;
        } else {
            System.out.println("Celda descubierta en (" + fila + ", " + columna
                    + "). Minas adyacentes: " + tablero[fila][columna]);
            verificarVictoria();
        }
    }

    @Override
    public boolean validarMovimiento(int fila, int columna) {
        return fila >= 0 && fila < filas
            && columna >= 0 && columna < columnas;
    }

    private void verificarVictoria() {
        int celdasSinMina = filas * columnas - totalMinas;
        int celdasDescubiertas = 0;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (descubierto[i][j] && tablero[i][j] != MINA) {
                    celdasDescubiertas++;
                }
            }
        }
        if (celdasDescubiertas == celdasSinMina) {
            System.out.println("¡Ganaste! Descubriste todas las celdas seguras.");
            juegoTerminado = true;
        }
    }

    public void mostrarTablero() {
        System.out.println("\n--- TABLERO ---");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!descubierto[i][j]) {
                    System.out.print("[?] ");
                } else if (tablero[i][j] == MINA) {
                    System.out.print("[X] ");
                } else {
                    System.out.print("[" + tablero[i][j] + "] ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        BuscaMinas juego = new BuscaMinas(4, 4, 3);

        juego.ubicarMina(0, 1);
        juego.ubicarMina(2, 3);
        juego.ubicarMina(3, 0);

        juego.mostrarTablero();

        juego.realizarJugada(1, 1);
        juego.realizarJugada(0, 1);
        juego.realizarJugada(-1, 5);
    }
}