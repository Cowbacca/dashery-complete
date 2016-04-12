package uk.co.dashery.clothingquery.clothing;

import com.google.common.collect.Sets;
import org.junit.Test;
import uk.co.dashery.clothingquery.clothing.tag.Tag;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClothingTest {

    public static final String TAG = "test";
    public static final int MORE_RELEVANT = 1;
    public static final int EQUAL_RELEVANCE = 0;
    public static final int LESS_RELEVANT = -1;

    @Test
    public void testAddsNameToTags() {
        Clothing clothing = new Clothing();
        clothing.setName("a test name");

        assertThat(clothing.getTags(), is(getTags(Clothing.NAME_SCORE_MULTIPLIER, "a", TAG, "name")));
    }

    private Set<Tag> getTags(int scoreMultiplier, String... values) {
        return Arrays.stream(values)
                .map(value -> new Tag(value, scoreMultiplier))
                .collect(Collectors.toSet());
    }

    @Test
    public void testComparesOneTagsAsLessRelevantThanOneTag() {
        Clothing clothing = clothingWithOneTag();
        Clothing moreRelevantClothing = clothingWithTwoTags();

        assertThat(clothing.compareRelevance(moreRelevantClothing, TAG), is(LESS_RELEVANT));
    }

    private Clothing clothingWithOneTag() {
        Clothing clothing = new Clothing();
        clothing.setSearchableText(TAG);
        return clothing;
    }

    private Clothing clothingWithTwoTags() {
        Clothing moreRelevantClothing = new Clothing();
        moreRelevantClothing.setSearchableText(TAG + " " + TAG);
        return moreRelevantClothing;
    }

    @Test
    public void testComparesTwoTagsAsMoreRelevantThanOneTag() {
        Clothing clothing = clothingWithTwoTags();
        Clothing lessRelevantClothing = clothingWithOneTag();

        assertThat(clothing.compareRelevance(lessRelevantClothing, TAG), is(MORE_RELEVANT));
    }

    @Test
    public void testComparesEqualNumberOfTagsAsEquallyRelevant() {
        Clothing clothing = clothingWithOneTag();
        Clothing equallyRelevantClothing = clothingWithOneTag();

        assertThat(clothing.compareRelevance(equallyRelevantClothing, TAG), is(EQUAL_RELEVANCE));
    }

    @Test
    public void testAddsRelevanceFromBothNameAndSearchableText() {
        Clothing clothing = new Clothing();
        clothing.setSearchableText(TAG + " " + TAG);
        clothing.setName(TAG);

        assertThat(clothing.compareRelevance(clothingWithTwoTags(), TAG), is(MORE_RELEVANT));
    }

    @Test
    public void testIgnoresPunctuationEtcWhenCalculatingNumberOfOccurrences() {
        Clothing clothing = new Clothing();
        clothing.setSearchableText("test test.");

        Tag expectedTag = new Tag("test", 2 * Clothing.SEARCHABLE_TEXT_SCORE_MULTIPLIER);

        assertThat(clothing.getTags(), is(Sets.newHashSet(expectedTag)));
    }
    
    @Test
    public void testAddsBrandToTags() {
        Clothing clothing = new Clothing();
        clothing.setBrand("Some Brand");

        assertThat(clothing.getTags(), is(getTags(Clothing.BRAND_SCORE_MULTIPLIER, "some", "brand")));
    }

}
