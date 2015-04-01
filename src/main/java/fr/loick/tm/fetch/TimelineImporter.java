/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.fetch;

import java.util.Collection;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 *
 * @author q13000412
 */
public class TimelineImporter implements TwitterImporter{

    @Override
    public Collection<Status> importStatus(Twitter twitter) throws TwitterException {
        return twitter.getUserTimeline();
    }
    
}
