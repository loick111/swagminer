/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.fetch;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Collection;

/**
 * @author q13000412
 */
public interface TwitterImporter {
    public Collection<Status> importStatus(Twitter twitter) throws TwitterException;
}
