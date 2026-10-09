
/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Filter {

    /**
     * Find tweets written by a particular user.
     *
     * @param tweets list of tweets with distinct IDs
     * @param username Twitter username
     * @return tweets written by the specified user, in input order
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> result = new ArrayList<>();

        for (Tweet tweet : tweets) {
            if (tweet.getAuthor().equals(username)) {
                result.add(tweet);
            }
        }

        return result;
    }

    /**
     * Find tweets sent during a particular timespan.
     *
     * @param tweets list of tweets with distinct IDs
     * @param timespan time interval
     * @return tweets within the timespan, in input order
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        List<Tweet> result = new ArrayList<>();

        for (Tweet tweet : tweets) {
            Instant time = tweet.getTimestamp();

            if (!time.isBefore(timespan.getStart())
                    && !time.isAfter(timespan.getEnd())) {
                result.add(tweet);
            }
        }

        return result;
    }

    /**
     * Find tweets containing certain words.
     * Matching is case-insensitive and recognizes complete words,
     * including words followed or preceded by punctuation.
     *
     * @param tweets list of tweets with distinct IDs
     * @param words words to search for in the tweets
     * @return matching tweets, in input order
     */
    public static List<Tweet> containing(
            List<Tweet> tweets, List<String> words) {

        List<Tweet> result = new ArrayList<>();

        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            boolean matches = false;

            for (String word : words) {
                String regex = "(?i)(?<![A-Za-z0-9_])"
                        + Pattern.quote(word)
                        + "(?![A-Za-z0-9_])";

                if (Pattern.compile(regex).matcher(text).find()) {
                    matches = true;
                    break;
                }
            }

            if (matches) {
                result.add(tweet);
            }
        }

        return result;
    }
}
