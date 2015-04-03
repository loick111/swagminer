/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.assoc;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Assoc<T> {
    final private AssocPattern<T> x;
    final private AssocPattern<T> y;
    final private Set<T> rigth;

    public Assoc(AssocPattern<T> x, AssocPattern<T> y) {
        this.x = x;
        this.y = y;
        rigth = new HashSet<>(y.getData());
        rigth.removeAll(x.getData());
    }
    
    public double getFreq(){
        return y.getFreq();
    }
    
    public double getTrust(){
        return y.getFreq()/ x.getFreq();
    }
    
    public Set<T> getLeft(){
        return x.getData();
    }
    
    public Set<T> getRigth(){
        return rigth;
    }
    
    @Override
    public String toString(){
        return getLeft() + " -> " + getRigth();
    }
}
