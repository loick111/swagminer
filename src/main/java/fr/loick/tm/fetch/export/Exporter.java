/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.fetch.export;

import twitter4j.Status;

/**
 * @author q13000412
 */
public interface Exporter {
    public void export(Status status);

    public void endExport();
}
