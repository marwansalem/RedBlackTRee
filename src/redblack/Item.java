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
public class Item {
    int value;

   

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.value != other.value) {
            return false;
        }
        return true;
    }
    public boolean isGreaterThan(Item other){
        if(this.value>other.value)
            return true;
        return false;
    }
    public boolean isLessThan(Item Other){
        if(this.value < Other.value)
            return true;
        return false;
    }
    
    
}
