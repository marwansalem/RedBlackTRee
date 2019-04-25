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
public class Node {//make your node generic < >  
    Node parent;
    Node left;
    Node right;
    static int count;
    COLOR Color;
    String data;
    public static Node ROOT;
    static {
        ROOT = null;
        count = 0;
    }
    
    public enum COLOR{ RED,BLACK};
    public Node(String data,COLOR Col) {
        this.parent = null;
        this.left = null;
        this.right = null;
        this.Color=Col;
        this.data = data;
    }
    public Node(String it){
        this.parent = null;
        this.left = null;
        this.right = null;
        this.data=it;
        this.Color=COLOR.RED;
    }
    private boolean rightIsBlack(){
        if(this.right == null)
            return true;
        if(this.right.Color == COLOR.BLACK)
            return true;
        return false;
    }
    private boolean leftisBlack(){
       
        if(this.left == null)
            return true;
        if(this.left.Color == COLOR.BLACK)
            return true;
        return false;
    }
    public static void Inorder(Node node){
        
        if(node == null)
            return ;
        Inorder(node.left);
        if(node.Color== COLOR.RED)
            System.out.print("R:");
        else
            System.out.print("B:");
        if(Node.ROOT==node)
            System.out.print("ROOT:");
        if(node.isRight())
            System.out.print("RIGHT");
        else if(node.isLeft())
            System.out.print("LEFT");
        System.out.print(node.data+" ");
        
        Inorder(node.right);
    }
    public static int getTreeHeight(Node node){// number of nodes from root to nil // nill not counted;
        if(node == null)
            return 0; // to count edges returns -1;
        int leftHeight  = getTreeHeight(node.left);
        int rightHeight = getTreeHeight(node.right);
        int max = leftHeight>rightHeight?leftHeight:rightHeight;
        return 1+max;
        
    }
    
    public boolean isRoot(){
        return Node.ROOT == this;
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
        if(Node.ROOT==this || this.parent == null)
            return false;
        
        
        return parent.right==this;
        
    }

    
    public boolean isLeft(){
        if(Node.ROOT==this || this.parent == null)
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
        if(parent!=null)
            return parent;
        return null;
    }
    public Node getGrandParent(){
        return getParent().getParent();
    }

    public String getData() {
        return data;
    }
    
    public Node findSuccessor(){
        Node node = this.right;//find left most root in the right subtree
        while(node.left !=null){
            node = node.left;
        }
        return node;
    }
    
    public static void setROOT(Node ROOT) {
        if(ROOT == null)
            return;
        Node.ROOT = ROOT;
        if(Node.ROOT.parent!=null){
            if(Node.ROOT.isLeft())
                Node.ROOT.parent.left=null;
            if(Node.ROOT.isRight())
                Node.ROOT.parent.right =null;
        }
        Node.ROOT.parent = null;
        
        Node.ROOT.Color = COLOR.BLACK;
    }
    
    public static Node BSTdelete(String target){
        Node current = Node.ROOT;
        while(current!=null){
            if(target.compareTo(current.data) < 0){
                current = current.left;
            }else if(target.compareTo(current.data) > 0){
                current = current.left;
            }else{
                break;//current contains target data
            }
        }
        if(current == null)
            return null;
        if(current!=null)
        {
            if(current.left!=null &&current.right !=null)
            {
                Node successor = current.findSuccessor();
                current.data  = successor.data;
                if(successor.Color == COLOR.RED){
                    successor.parent.left = null;
                    successor.parent = null;
                }else{//Successor is black
                    boolean condition =false;//successor black and has only 1 red child
                    if(successor.left==null){
                        if(successor.right.Color == COLOR.RED){
                            condition = true;
                            String temp = successor.data;
                            successor.data = successor.right.data;
                            successor.right.data =temp;
                            
                        }
                        
                    }
                    
                    
                }
            }
        }        
        Node temp = null;
        return temp;//just to remove error
        
    }
    private static void reFix(Node x){
        if(x.parent == null)
            return;
        if(x.Color == COLOR.RED && x.parent.isBlack() )
            return;
        if(x.parent == Node.ROOT)
            return;
        boolean UncleisBlack =false;
        boolean UncleisRed = false;
        if(x.getUncle() == null)
            UncleisBlack = true;
        else if(x.getUncle().isBlack())
            UncleisBlack = true;
        else if((x.getUncle().Color == COLOR.RED))
            UncleisRed = true;
        int icase = 0;
        if(UncleisRed)
            icase = 1;
        if(UncleisBlack && x.inSameLineAsParent())
            icase = 2;
        if(UncleisBlack && !x.inSameLineAsParent())
            icase = 3;
        boolean done = false;
        Node node =x;
        while(!done){
            switch(icase){
                case 1:{
                    if(node == Node.ROOT){
                        node.Color = COLOR.BLACK;
                        done = true;
                    }else{
                    node.parent.Color = COLOR.BLACK;
                    node.getUncle().recolor();
                    node.parent.parent.Color = COLOR.RED;
                    node = node.parent.parent;
                    }
                    node = node.parent.parent;

                    break;
                }
                case 2:{
                    Node ref = node.parent;
                    if(node.isLeft()){
                        node.parent.rightRotate();
                    }else if(node.isRight()){
                        node.parent.leftRotate();
                    }else{//when should this happen?
                        done = true;
                        break;
                    }
                    node = ref;
                    icase = 3;
                    break;
                }case 3:{
                    if(node.parent.parent != null){
                    node.parent.parent.recolor();
                    node.parent.recolor();
                    }
                    if(node.isLeft()){
                        if(node.parent.parent!=null)
                            node.parent.parent.rightRotate();
                    }else if(node.isRight()){
                            if(node.parent.parent!=null)
                                node.parent.parent.leftRotate();
                    }else{
                        done = true;
                        break;
                    }

                    done = true;
                    break;
                }
                default:{
                    done = true;
                    break;
                }
            }
        }
        
        System.out.println("FIXED");
    }
    
    private static void fix(Node node){
       if(node.parent == null || Node.ROOT == node)
           return;
       boolean done = true; 
       if(node.parent != null && Node.ROOT != node)
           if(node.parent.isRed())
               done = false;
       while(!done ){
           Node uncle = node.getUncle();
           boolean uncleB = false;
           if(uncle == null)
               uncleB = true;
           else if(uncle.isBlack())
               uncleB = true;
           
           //if parent is left
            if(node.parent == node.parent.parent.left){
                if(!uncleB){//red uncle
                    node.parent.Color = COLOR.BLACK;
                    if(uncle !=null)
                        uncle.Color = COLOR.BLACK;
                    node.parent.parent.Color = COLOR.RED;
                    node = node.parent.parent;
                }else{//uncle black
                    if(node ==  node.parent.right){
                        node = node.parent;
                        node.leftRotate();
                        
                    }
                    node.parent.Color = COLOR.BLACK;
                    node.parent.parent.Color = COLOR.RED;
                    node.parent.parent.rightRotate();


                }
           }else{
                if(!uncleB){
                    node.parent.Color = COLOR.BLACK;  
                    if(uncle !=null)
                        uncle.Color = COLOR.BLACK;
                    node.parent.parent.Color = COLOR.RED;
                    node = node.parent.parent;
                }else{
                    if(node == node.parent.left){
                        node = node.parent;
                        node.rightRotate();
                    }
                    node.parent.Color = COLOR.BLACK;
                    node.parent.parent.Color = COLOR.RED;
                    node.parent.parent.leftRotate();
                }
            }
           
            if(node.parent != null && Node.ROOT != node)
                if(node.parent.isRed())
                    done = false;
                else done =true;
            else done = true;
       }
       Node.ROOT.Color = COLOR.BLACK; 
       
    }
    
    
    public static Node getRoot(Node node){
        
        while(node!=null){
            if(node.isRoot())
                return node;
            node=node.getParent();
        }
        return null;
    }
    public static Node insertDeprecated(Node node, String data){
        if(Node.ROOT == null){
            node = new Node(data);
            Node.setROOT(node);
            return Node.ROOT;
        }
        if(node == null)
        {
            return new Node(data);
        }
        
        
       Node nd=null;  
        if(data.compareTo(node.data) >0 ){
            node.right = insertDeprecated(node.right, data);
            if(node.right!=null){
                node.right.parent = node;
                nd=node.right;
                fixTree(nd);
            }
        
        }
        else if(data.compareTo(node.data)<0){
            node.left = insertDeprecated(node.left,data);
            if(node.left!=null){
                node.left.parent = node;
                nd=node.left;
                fixTree(nd);
            }

        }else //equal
            return node;//Means Equal data
        
        
        return node;
    }
    public static void insert(String data ) { 
        Node node = Node.ROOT;
        if(node == null){
            node = new Node(data);
            setROOT(node);
            count++;
            return;
        }
        Node last=null;
        while(node!=null && node!=last){
            last = node;
            if(data.compareTo(node.data)<0){
                node=node.left;
                if(node == null){
                    node = new Node(data);
                    
                    node.parent = last;
                    if(last != null)
                        last.left = node;
                    if(Node.ROOT == null)
                        Node.setROOT(node);
                    fix(node);
                    return ;
                }
            }
            else if(data.compareTo(node.data)>0){
                node = node.right;
                if(node == null){
                    node = new Node(data);
                    node.parent = last;
                    if(last!=null)
                        last.right = node;
                    if(Node.ROOT == null)
                        Node.setROOT(node);
                    fix(node);
                    return;
                }
            }
            else return;
            
                
        }
    }
//    private static Node getRoot(Node Nod){
//        Node curr= Nod;
//        if(ROOT== null){
//            while(curr.parent!=null){
//                curr = curr.getParent();
//            }
//            return ROOT = curr;
//        }
//        return ROOT; 
//    }
//    
    
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
        Node node = null;
        if(x.getParent().isRed() && x.getUncle().isRed()){
            x.getParent().recolor();
            if(x.getUncle() !=null)
                x.getUncle().recolor();
            x.getParent().getParent().recolor();
            node= x.parent.parent;
            System.out.println(x.data+" Case 1");
            caseI(node);
        }
        if(node == Node.ROOT || node.parent == null){
            System.out.println(x.data+" Case 1 ROOT");
            node.Color=COLOR.BLACK;
            Node.ROOT = node;
        }
            
    }
    private static Node createRoot(String data){
        Node.ROOT = new Node(data, COLOR.BLACK);
        Node.ROOT.parent = null;
        Node.ROOT.left = null;
        Node.ROOT.right = null;
        return Node.ROOT;
    }
    private boolean inSameLineAsParent(){
        if(this.parent==null) return false;
        if(this.isRight() == this.getParent().isRight())//if the are r
            return true;
        return false;
        
    }
    private Node rotateLeftMyParent(){//left rotate my parent
        Node Parent = this.getParent();
        boolean newRoot= false;
        if(Parent.parent == null ||Parent ==Node.ROOT)
            newRoot=true;
        else
            this.getGrandParent().left = this;
        
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
        else
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
     if(x.parent == null ||x == Node.ROOT){
         Node.setROOT(y);
         Node.ROOT = y;
         y.parent = null;
         y.Color = COLOR.BLACK;
         
     }
     else if(x == x.parent.left){
         x.parent.left= y;
     }
     else{
         x.parent.right = y;
     }
     y.left = x;
     x.parent = y;
     return x;
    }

    private Node rightRotate(){//
       Node x= this;
       if(this == Node.ROOT)
         System.out.println("Rotating : " + x.data   + "Parent:"+x.parent);      
       Node y = x.left;
       if(y==null)
           return x;
       System.out.println(y);
       if(y!=null){
       x.left= y.right;
       if(y.right != null){
           y.right.parent = x;
       }
       }
       else x.left = null;
       
       if(y!=null)
       y.parent = x.parent;
       if(x.parent == null || x == Node.ROOT){
            Node.setROOT(y);
            Node.ROOT = y;
            y.parent = null;
            y.Color = COLOR.BLACK;
       }
       else if(x == x.parent.right){
           x.parent.right = y;
       }
       else{
           x.parent.left= y;
       }
       if(y!=null)
        y.right= x;
       x.parent = y;
       
       //you must return Parent node as new x to reoclor it... so get it later
       return x;
       
   }
    
    
    
    
   private static void fixTree(Node x){//Handle insertion cases
        if(x.Color == COLOR.BLACK)
            return;
        if(x.getParent().isBlack())
            return;
        boolean UncleisBlack =false;
        if( x== Node.ROOT || x.parent == null )//comparison done better
        {
            System.out.println(x.data+" Fixed");
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
        Node case2to3 = x;
        boolean case2_3 = false;
        if(x.parent.isRed() &&UncleisBlack){
            if(x.inSameLineAsParent()==false){ // case II not in same line
                if(x.isRight()){
                    System.out.println(x.data+" Case II x RIGHT");
                    x.parent.leftRotate();
                    case2_3 = true;
                    x =case2to3 = x.left;
                }else if(x.isLeft()){
                    System.out.println(x.data+" Case II x left");
                    x.parent.rightRotate();
                    case2_3 = true;
                    x=case2to3 = x.right;
                    
                }
                
            }
            
            UncleisBlack = false;
            if(x.getUncle()==null)
                UncleisBlack = true;
            else if(x.getUncle().isBlack())
                UncleisBlack = true;
            if((x.inSameLineAsParent() == true &&UncleisBlack) || case2_3){
                
                if(x.getGrandParent()!=null)
                    x.parent.parent.recolor();
                x.parent.recolor();
                if(x.isLeft()){ // Left Left case
                    System.out.println(x.data+" Case III LL");
                   //x.parent.rotateRightMyParent();
                    x.parent.parent.rightRotate();
                }else if(x.isRight()){ //Right Right Case
                   // x.parent.rotateLeftMyParent();
                    System.out.println(x.data+" Case III RR");
                    x.parent.parent.leftRotate();
                }
            }
        }
 
    }
   
    public static void transplant(Node a , Node b) {
        if(a.parent == null || a.parent == Node.ROOT) {
            setROOT(b);
        }else if(a.isLeft()){
            a.parent.left = b;
        }else{
            a.parent.right = b;
        }
        b.parent = a.parent;
    }
    public static Node search(String target){
        Node current = Node.ROOT;
        while(current!=null){
            if(current.data.equals(target))
                break;
            else if(target.compareTo(current.data)<0)
                current = current.left;
            else
                current = current.right;
        }
        return current;
    }
    public static void delete(String target){
        Node current = search(target);
        if(current == null) return;
        Node y = current;
        COLOR original = y.Color;
        Node x=null;
        if(current.left ==null){
            x = current.right;
            transplant(current, current.right);
        }else if(current.right == null){
            x = current.left;
            transplant(current,current.left);
        }else y=current.findSuccessor();
        original = y.Color;
        x = y.right;
        if(y.parent == current){
            x.parent = y;
        }else{
            transplant(y, y.right);
            y.right.parent = y;
        }
        transplant(current,y);
        y.left = current.left;
        y.left.parent = y;
        y.Color = current.Color;
        if(original == COLOR.BLACK)
            deleteFix(x);
    }
    private static void deleteFix(Node x){
        while(x!=Node.ROOT && x.Color == COLOR.BLACK)   
        {
            Node w = null;
            if(x==x.parent.left){
                w = x.parent.right;
                if(w.Color==COLOR.RED)
                {
                    w.Color = COLOR.BLACK;
                    x.parent.Color = COLOR.RED;
                    x.parent.leftRotate();
                    w = x.parent.right;
                }
                if(w.left.Color == COLOR.BLACK && w.right.Color == COLOR.BLACK){
                    w.Color = COLOR.RED;
                    x = x.parent;
                }else if(w.right.Color == COLOR.BLACK){
                    w.left.Color = COLOR.BLACK;
                    w.Color = COLOR.RED;
                    w.rightRotate();
                    w = x.parent.right;
                }
                w.Color = x.parent.Color;
                x.parent.Color = COLOR.BLACK;
                w.right.Color = COLOR.BLACK;
                x.parent.leftRotate();
                x=Node.ROOT;
            }else{
                w = x.parent.left;
                if(w.Color==COLOR.RED)
                {
                    w.Color = COLOR.BLACK;
                    x.parent.Color = COLOR.RED;
                    x.parent.rightRotate();
                    w = x.parent.left;
                }
                if(w.right.Color == COLOR.BLACK && w.left.Color == COLOR.BLACK){
                    w.Color = COLOR.RED;
                    x = x.parent;
                }else if(w.left.Color == COLOR.BLACK){
                    w.right.Color = COLOR.BLACK;
                    w.Color = COLOR.RED;
                    w.leftRotate();
                    w = x.parent.left;
                }
                w.Color = x.parent.Color;
                x.parent.Color = COLOR.BLACK;
                w.right.Color = COLOR.BLACK;
                x.parent.rightRotate();
                x=Node.ROOT;
            }
        } 
        x.Color = COLOR.BLACK;
    }
   
}
