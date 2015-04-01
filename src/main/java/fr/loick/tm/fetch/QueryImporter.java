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
    final private String[] queries;
    private int current = 0;
    private Query query;

    public QueryImporter(String[] queries) {
        this.queries = queries;
        this.query = new Query(queries[current]);
    }

    @Override
    public Collection<Status> importStatus(Twitter twitter) throws TwitterException {
        if(query == null){
            query = new Query(queries[++current]);
        }
        
        query.setCount(100);
        QueryResult result = twitter.search(query);
        Collection<Status> c = result.getTweets();
        query = result.nextQuery();
        return c;
    }
}
