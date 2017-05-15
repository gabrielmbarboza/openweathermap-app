package com.gabrielmbarboza.openweathermapapp;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        assertEquals("Monday", util.convertTimestampToDay(day));
    }
}