package fr.loick.tm.fetch;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Collection;

/**
 * Created by loick on 01/04/15.
 */
public class HomeTimelineImporter implements TwitterImporter{
    final int nbPages;

    public HomeTimelineImporter(int nbPages) {
        this.nbPages = nbPages;
    }

    @Override
    public Collection<Status> importStatus(Twitter twitter) throws TwitterException {
        return twitter.getHomeTimeline(new Paging(nbPages));
    }
}
