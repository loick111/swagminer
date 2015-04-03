/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.assoc;

import java.util.Set;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AssocPattern<T> {
    final private Set<T> data;
    final private double freq;

    public AssocPattern(Set<T> data, double freq) {
        this.data = data;
        this.freq = freq;
    }

    public Set<T> getData() {
        return data;
    }

    public double getFreq() {
        return freq;
    }

}
