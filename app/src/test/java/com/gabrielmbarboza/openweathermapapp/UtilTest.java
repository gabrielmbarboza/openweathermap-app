package com.gabrielmbarboza.openweathermapapp;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class UtilTest {

    @Test
    public void convertTimestampToDay_isCorrect() throws Exception {
        Util util = mock(Util.class);

        long day = Calendar.getInstance().getTimeInMillis();

        assertEquals("Thursday", util.convertTimestampToDayOfWeek(day));
    }
}