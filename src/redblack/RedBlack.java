/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redblack;

/**
 *
 * @author MarwanSaad
 */
public class RedBlack {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Node ROOT = new Node(10);
        Node.setROOT(ROOT);
        ROOT.left = new Node(5);
        ROOT.right = new Node(20);
        ROOT.leftRotate();
        
        Node.Inorder(Node.ROOT);
    }
    
}
