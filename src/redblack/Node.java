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
public class Node {
    Node parent;
    Node left;
    Node right;
    COLOR Color;
    int data;
    public static Node ROOT;
    boolean root=false;
    
    public enum COLOR{ RED,BLACK};
    public Node(int data,COLOR Col) {
        this.parent = null;
        this.Color=Col;
        this.data = data;
    }
    public Node(int it){
        this.parent = null;
        this.data=it;
        this.Color=COLOR.RED;
        this.root = false;
    }
    
    public static void Inorder(Node node){
        if(node == null)
            return ;
        Inorder(node.left);
        if(node.Color== COLOR.RED)
            System.out.print("R:");
        else
            System.out.print("B:");
        System.out.print(node.data+" ");
        Inorder(node.right);
    }
    
    public boolean isRoot(){
        return root;
    }
    public boolean isRed(){
        return Color==COLOR.RED;
    }
    
    public boolean isBlack(){
        return Color==COLOR.BLACK;
    }
    public void recolor(){
        if(isRed())
            Color=COLOR.BLACK;
        else
            Color = COLOR.RED;
    }
    
    public boolean isRight(){
        if(root==true)
            return false;
        
        return parent.right==this;
    }

    
    public boolean isLeft(){
        if(root=true)
            return false;
        return parent.left==this;
    }
    public Node getSibling(){
        if(isLeft())
            return parent.right;
        if(isRight())
            return parent.left;
        return null;
    }

    public static void setROOT(Node ROOT) {
        Node.ROOT = ROOT;
    }
    
    public Node getLeft(){
        return this.left;
    }
    public Node getRight(){
        return this.right;
    }
    public Node getUncle(){
        return parent.getSibling();
    }
    public Node getParent(){
        if(root==true)
            return null;
        return parent;
    }
    public Node getGrandParent(){
        return getParent().getParent();
    }

    public int getData() {
        return data;
    }
    
//    public static Node getRoot(Node node){
//        
//        while(node!=null){
//            if(node.isRoot())
//                return node;
//            node=node.getParent();
//        }
//        return null;
//    }
    public static Node insert(Node node, int data){
        if(node == null)
        {
            return new Node(data);
        }
        
        
       Node nd=null;  
        if(data > node.data ){
            nd= insert(node.right, data);
        
        }
        else if(data < node.data){
            nd = insert(node.left,data);

        }
        if(nd !=null)
                if(nd.parent == null)
                    nd.parent = node;
        Node ref = nd;
        fixTree(nd);
        return ref;
    }
    public static Node getRoot(Node Nod){
        Node curr= Nod;
        if(ROOT== null){
            while(curr.parent!=null){
                curr = curr.getParent();
            }
            return ROOT = curr;
        }
        return ROOT; 
    }
    
    
//    public void insert(Item IT){
//        Node newnode= new Node(IT);
//        Node current=getRoot(this);
//        boolean inserted=false;
//        while(!inserted){
//            if(current.data.isGreaterThan(IT))//I am greater than inserted
//            {
//                current = current.left;
//                
//            }else if(current.data.isLessThan(IT)){// I am less Than inserted
//                current = current.right;
//            }else{// By default I choose that equal data is to be inserted to the left Side
//                current = current.left;
//            }
//            if(current == null){
//                    current = newnode;
//                    inserted = true;
//            }
//            
//        }
//        fixTree(current);
//        
//        
//    }
    private static void caseI(Node x){
        if(x.getUncle()==null)//null means black
            return;
        if(x.getUncle().isBlack())
            return;
        
        if(x.getParent().isRed() && x.getUncle().isRed()){
            x.getParent().recolor();
            if(x.getUncle() !=null)
                x.getUncle().recolor();
            x.getParent().getParent().recolor();
            caseI(x.getGrandParent());
        }
        if(x == Node.ROOT)
            x.Color=COLOR.BLACK;
            
    }
    public static Node createRoot(int data){
        Node.ROOT = new Node(data, COLOR.BLACK);
        Node.ROOT.parent = null;
        Node.ROOT.left = null;
        Node.ROOT.right = null;
        return Node.ROOT;
    }
    private boolean inSameLineAsParent(){
        if(this==null) return false;
        
        if(this.isRight() == this.getParent().isRight())//if the are r
            return true;
        return false;
        
    }
    private Node rotateLeftMyParent(){//left rotate my parent
        Node Parent = this.getParent();
        this.getGrandParent().left = this;
        boolean newRoot= false;
        if(Parent.parent == null ||Parent ==Node.ROOT)
            newRoot=true;
       
        this.parent = this.getGrandParent();
        Parent.right=this.left;
        Parent.right.parent = Parent;
        this.left = Parent;
        Parent.parent = this;
        if(newRoot){
            Node.ROOT = this;
            this.Color = COLOR.BLACK;
            this.parent = null;
        }
        return Parent;
    }
    private Node rotateRightMyParent(){
        Node Parent = this.getParent();
        boolean newRoot= false;
        if(Parent.parent == null ||Parent ==Node.ROOT)
            newRoot=true;
        this.getGrandParent().right = this;
        
        
        this.parent = this.getGrandParent();
        Parent.left = this.right;
        Parent.left.parent = Parent;
        this.right = Parent;
        Parent.parent = Parent;
        if(newRoot){
            Node.ROOT = this;
            this.Color = COLOR.BLACK;
            this.parent = null;
        }
        return Parent;
        
    }
    
    private Node leftRotate( ){
     Node x = this;
     Node y = x.right;
     x.right = y.left;
     if(y.left!= null){
         y.left.parent = x;
     }
     y.parent = x.parent;
     if(x.parent == null)
         Node.ROOT = y;
     else if(x == x.parent.left){
         x.parent.left= y;
     }
     else{
         x.parent.right = y;
     }
     y.left = x;
     x.parent = y;
     return null;
    }

   private Node rightRotate(){
       Node x= this.parent;
       Node y = x.left;
       x.left= y.right;
       if(y.right != null){
           y.right.parent = x;
       }
       y.parent = x.parent;
       if(x.parent == null){
           Node.ROOT = y;
       }
       else if(x == x.parent.right){
           x.parent.right = y;
       }
       else{
           x.parent.left= y;
       }
       y.right= x;
       x.parent = y;
       
       //you must return Parent node as new x to reoclor it... so get it later
       return null;
       
   }
    
    
    
    private static void fixTree(Node x){//Handle insertion cases
        if(x.getParent().isBlack())
            return;
        boolean UncleisBlack =false;
        if( x== Node.getRoot(x) || x.root ==true)//comparison done better
        {
            x.root = true;
            Node.ROOT = x;
            x.Color = COLOR.BLACK;
            return;
        }
        if(x.getUncle()!= null)
            if(x.parent.isRed() && x.getUncle().isRed()){ //CASE I
                caseI(x);
                return;
            }
        if(x.getUncle()==null)
            UncleisBlack = true;
        else if(x.getUncle().isBlack())
            UncleisBlack = true;
        if(x.parent.isRed() &&UncleisBlack){
            if(x.inSameLineAsParent()==false){ // case II not in same line
                if(x.isRight()){
                    x=x.rotateLeftMyParent();
                }else if(x.isLeft()){
                    x = x.rotateLeftMyParent();
                }
                
            }
            if(x.inSameLineAsParent() == true){
                x.getGrandParent().recolor();
                x.parent.recolor();
                if(x.isLeft()){ // Left Left case
                    
                    x.parent.rotateRightMyParent();
                }else if(x.isRight()){ //Right Right Case
                    x.parent.rotateLeftMyParent();
                }
            }
        }
            
            
        
    }
}
