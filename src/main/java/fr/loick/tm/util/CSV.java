package fr.loick.tm.util;

import twitter4j.Status;

import java.util.Set;

/**
 * Created by loick on 01/04/15.
 */
final public class CSV {
    private CSV() {
    }

    public static String stringify(Status s) {
        return CSV.stringify(new TweetData(
                String.valueOf(s.getCreatedAt()).trim(),
                s.getUser().getScreenName().trim(),
                s.getUser().getLocation().trim(),
                Strings.getWords(s.getText())
        ));
    }

    public static String stringify(TweetData td) {
        return Strings.join(new String[]{
                td.getDate(),
                td.getUser(),
                td.getLocation(),
                Strings.join(td.getWords(), ",")
        }, ";");
    }

    static public TweetData parse(String csv) {
        String[] data = Strings.split(csv, ";");
        return new TweetData(
                data[0],
                data[1],
                data[2],
                Strings.split(data[3], ",")
        );
    }
    
    static public String stringify(Set item){
        StringBuilder sb = new StringBuilder(item.size() * 5);
        boolean first = true;
        for (Object i : item) {
            if(!first){
                sb.append(',');
            }else{
                first = false;
            }
            sb.append(i);
        }
        return sb.toString();
    }

    static public class TweetData {
        final private String date;
        final private String user;
        final private String location;
        final private String[] words;

        public TweetData(String date, String user, String location, String[] words) {
            this.date = date;
            this.user = user;
            this.location = location;
            this.words = words;
        }

        public String getDate() {
            return date;
        }

        public String getUser() {
            return user;
        }

        public String getLocation() {
            return location;
        }

        public String[] getWords() {
            return words;
        }
    }
}
