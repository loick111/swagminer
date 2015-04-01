/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.loick.tm.fetch;

import twitter4j.*;

import java.util.Collection;

/**
 * @author q13000412
 */
public class QueryImporter implements TwitterImporter {
    final private String[] queries;
    private int current = 0;
    private Query query;

    public QueryImporter(String[] queries) {
        this.queries = queries;
        this.query = new Query(queries[current]);
    }

    @Override
    public Collection<Status> importStatus(Twitter twitter) throws TwitterException {
        if (query == null) {
            query = new Query(queries[++current]);
        }

        query.setCount(100);
        QueryResult result = twitter.search(query);
        Collection<Status> c = result.getTweets();
        query = result.nextQuery();
        return c;
    }
}
