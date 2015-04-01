/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.fetch;

import java.util.ArrayList;
import java.util.Collection;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 *
 * @author q13000412
 */
public class QueryImporter implements TwitterImporter{
    private Query query;

    public QueryImporter(String query) {
        this.query = new Query(query);
    }

    @Override
    public Collection<Status> importStatus(Twitter twitter) throws TwitterException {
        query.setCount(100);
        QueryResult result = twitter.search(query);
        Collection<Status> c = result.getTweets();
        query = result.nextQuery();
        return c;
    }
    
}
