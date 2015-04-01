package fr.loick.tm.util;

import twitter4j.Status;

/**
 * Created by loick on 01/04/15.
 */
final public class CSV {
    private CSV() {}

    public static String stringify(Status s) {
        return Strings.join(new String[] {
                String.valueOf(s.getCreatedAt()),
                s.getUser().getScreenName(),
                s.getUser().getLocation(),
                Strings.join(Strings.getWords(s.getText()), ",")
        }, ";");
    }
}
