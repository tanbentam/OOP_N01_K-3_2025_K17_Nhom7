package com.oopgroup7.quanlylophoc;

import org.junit.Test;
import static org.junit.Assert.*;
import com.oopgroup7.quanlylophoc.Model.Score;

public class TestScore {

    @Test
    public void testScoreCreation() {
        Score score = new Score();
        assertNotNull(score);
    }

    @Test
    public void testSetSubject() {
        Score score = new Score();
        score.setSubject("Java");
        assertEquals("Java", score.getSubject());
    }

    @Test
    public void testSetValue() {
        Score score = new Score();
        score.setValue(9.0);
        assertEquals(9.0, score.getValue(), 0.001);
    }

    @Test
    public void testNegativeScore() {
        Score score = new Score();
        score.setValue(-1.0);
        assertEquals(-1.0, score.getValue(), 0.001);
    }

    @Test
    public void testScoreGreaterThanTen() {
        Score score = new Score();
        score.setValue(11.0);
        assertEquals(11.0, score.getValue(), 0.001);
    }
}