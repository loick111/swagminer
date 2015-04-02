/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.apriori;

import java.util.Set;

/**
 *
 * @author q13000412
 */
public interface APrioriExporter<T> {
    public void export(Set<T> row, int effective);
}
