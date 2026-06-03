/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package buscamin;

/**
 *
 * @author manue
 */
public interface IJuego {
    void realizarJugada(int fila, int columna);
    boolean validarMovimiento(int fila, int columna);
}
