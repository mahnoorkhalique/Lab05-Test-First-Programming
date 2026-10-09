
package twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract information from a list of tweets.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     *
     * @param tweets list of tweets with distinct IDs
     * @return minimum-length time interval containing every tweet timestamp
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            throw new IllegalArgumentException("Tweet list must not be empty");
        }

        java.time.Instant earliest = tweets.get(0).getTimestamp();
        java.time.Instant latest = tweets.get(0).getTimestamp();

        for (Tweet tweet : tweets) {
            java.time.Instant time = tweet.getTimestamp();

            if (time.isBefore(earliest)) {
                earliest = time;
            }

            if (time.isAfter(latest)) {
                latest = time;
            }
        }

        return new Timespan(earliest, latest);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     *
     * Mentions are case-insensitive and returned in lowercase.
     * Email addresses are not treated as mentions.
     *
     * @param tweets list of tweets with distinct IDs
     * @return set of mentioned usernames
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();

        Pattern mentionPattern = Pattern.compile(
            "(?<![A-Za-z0-9_@])@([A-Za-z0-9_]+)(?![A-Za-z0-9_])"
        );

        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            Matcher matcher = mentionPattern.matcher(text);

            while (matcher.find()) {
                mentionedUsers.add(matcher.group(1).toLowerCase());
            }
        }

        return mentionedUsers;
    }
}
