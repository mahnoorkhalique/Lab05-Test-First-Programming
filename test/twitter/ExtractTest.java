
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    private static final Instant d1 =
            Instant.parse("2016-02-17T10:00:00Z");

    private static final Instant d2 =
            Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 =
            new Tweet(1, "alyssa",
                    "is it reasonable to talk about rivest so much?", d1);

    private static final Tweet tweet2 =
            new Tweet(2, "bbitdiddle",
                    "rivest talk in 30 minutes #hype", d2);

    @Test
    public void testAssertionsEnabled() {
        assertTrue(true);
    }
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan =
                Extract.getTimespan(Arrays.asList(tweet1, tweet2));

        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers =
                Extract.getMentionedUsers(Arrays.asList(tweet1));

        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsers() {
        Tweet tweet = new Tweet(
                3,
                "alice",
                "Hello @Bob and @Charlie",
                d1
        );

        Set<String> mentionedUsers =
                Extract.getMentionedUsers(Arrays.asList(tweet));

        Set<String> expectedUsers =
                new HashSet<>(Arrays.asList("bob", "charlie"));

        assertEquals(expectedUsers, mentionedUsers);
    }
}
