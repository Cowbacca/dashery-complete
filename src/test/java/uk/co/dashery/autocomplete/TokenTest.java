package uk.co.dashery.autocomplete;

import org.junit.Test;

import static org.hamcrest.number.OrderingComparison.*;
import static org.junit.Assert.assertThat;


public class TokenTest {

    @Test
    public void testComparesLessThanIfShorter() {
        Token a = new Token("a");
        Token ab = new Token("ab");

        assertThat(a, lessThanOrEqualTo(ab));
    }

    @Test
    public void testComparesGreaterThanIfLonger() {
        Token ab = new Token("ab");
        Token a = new Token("a");

        assertThat(ab, greaterThanOrEqualTo(a));
    }

    @Test
    public void testComparesLessThanIfSameLengthAndAlphabeticallyLess() {
        Token aa = new Token("aa");
        Token ab = new Token("ab");

        assertThat(aa, lessThanOrEqualTo(ab));
    }

    @Test
    public void testComparesGreaterThanIfSameLengthAndAlphabeticallyGreater() {
        Token ab = new Token("ab");
        Token aa = new Token("aa");

        assertThat(ab, greaterThanOrEqualTo(aa));
    }

    @Test
    public void testComparesEqualIfSame() {
        Token aa = new Token("aa");
        Token same = new Token("aa");

        assertThat(aa, comparesEqualTo(same));
    }
}