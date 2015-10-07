package org.ua.oblik.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ua.oblik.domain.dao.DateUtils;

/**
 *
 * @author Anton Bakalets
 */
public class DateUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtilsTest.class);
    
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH/mm/ss/dd/MM/yyyy");
    
    private static final String DAY = "10";
    private static final String MONTH = "10";
    private static final String YEAR = "2012";
    
    @Test
    public void test() throws ParseException {
        
        
        final Date midle = DATE_FORMAT.parse("13/56/08/" + DAY + "/" + MONTH + "/" + YEAR);
        LOGGER.debug(midle.toString());
        
        final Date monthBegining = DateUtils.getMonthBegining(midle);
        final String monthBeinningStr = DATE_FORMAT.format(monthBegining);
        LOGGER.debug(monthBeinningStr);
        final String[] splitBegin = monthBeinningStr.split("/");
        Assert.assertEquals("00", splitBegin[0]);
        Assert.assertEquals("00", splitBegin[1]);
        Assert.assertEquals("00", splitBegin[2]);
        Assert.assertEquals("01", splitBegin[3]);
        Assert.assertEquals(MONTH, splitBegin[4]);
        Assert.assertEquals(YEAR, splitBegin[5]);
        
        final Date monthEnd = DateUtils.getMonthEnd(midle);
        final String monthEndStr = DATE_FORMAT.format(monthEnd);
        LOGGER.debug(monthEndStr);
        final String[] splitEnd = monthEndStr.split("/");
        Assert.assertEquals("23", splitEnd[0]);
        Assert.assertEquals("59", splitEnd[1]);
        Assert.assertEquals("59", splitEnd[2]);
        Assert.assertEquals("31", splitEnd[3]);
        Assert.assertEquals(MONTH, splitEnd[4]);
        Assert.assertEquals(YEAR, splitEnd[5]);
    }
}
