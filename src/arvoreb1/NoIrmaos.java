/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arvoreb1;

/**
 *
 * @author miyuk
 */
public class NoIrmaos {
    No principal; //aqui armazena o nó principal, ou seja, o nó pelo qual estou procurando por seus irmãos.
    No irmao; //aqui armazena o irmao que irá ceder a chave
    int quelado; //se for -1, o irmão está na esquerda. se for 1 está na direita.
    int indiceprin; //armazena o índice no pai que o #principal# ocupa. Assim fica mais fácil ter uma referência e 
                    //fazer a redistribuição das chaves. Se for -1, significa que está no campo #esquerda#. Se for
                    //0, está no índice 0, e assim por diante.
    
    public NoIrmaos() {
        principal = null;
        irmao = null;
        quelado = 0;
        indiceprin = -2;
    }
}
