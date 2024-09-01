package com.demo.datetime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class TimeZoneChangeTest {

    /*
     Reference for the timezone change:
     "Turkey begins DST on 2014-03-31"
     https://www.oracle.com/java/technologies/tzdata-versions.html
     https://www.trthaber.com/haber/turkiye/yaz-saati-uygulamasina-gecildi-122625.html
     "Türkiye'de saatler, bu gece saat 03.00'dan itibaren 1 saat ileri alındı."
     */

    @Test
    void oneSecondChange_shouldMove1HourForward_whenOnTimezoneChangeWithFixedTimeZoneId(){

        /* GIVEN */
        final DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("uuuu MMM dd HH:mm:ss Z (z) (VV)");
        final LocalDateTime localDateTime = LocalDateTime.parse("2014 03 31 02:59:59", DateTimeFormatter.ofPattern("uuuu MM dd HH:mm:ss"));
        final ZonedDateTime turkeyZonedDateTime = localDateTime.atZone(ZoneId.of("Turkey"));
        System.out.println("Current Time  : " + outputDateFormat.format(turkeyZonedDateTime));

        /* WHEN */
        final ZonedDateTime turkeyZonedDateTimeAfter1Second = turkeyZonedDateTime.plusSeconds(1);
        final ZonedDateTime turkeyZonedDateTimeAfter2Second = turkeyZonedDateTime.plusSeconds(2);

        /* THEN */
        System.out.println("After 1 second: " + outputDateFormat.format(turkeyZonedDateTimeAfter1Second));
        Assertions.assertEquals(4, turkeyZonedDateTimeAfter1Second.getHour());
        Assertions.assertEquals(0, turkeyZonedDateTimeAfter1Second.getSecond());

        System.out.println("After 2 second: " + outputDateFormat.format(turkeyZonedDateTimeAfter2Second));
        Assertions.assertEquals(4, turkeyZonedDateTimeAfter2Second.getHour());
        Assertions.assertEquals(1, turkeyZonedDateTimeAfter2Second.getSecond());
    }

    @Test
    void oneSecondChange_shouldMove1HourForward_whenOnTimezoneChangeWithFixedTimeOffset(){

        /* GIVEN */
        final DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("uuuu MMM dd HH:mm:ss Z (z) (VV)");
        final LocalDateTime localDateTime = LocalDateTime.parse("2014 03 31 02:59:59", DateTimeFormatter.ofPattern("uuuu MM dd HH:mm:ss"));
        final ZonedDateTime turkeyZonedDateTime = localDateTime.atZone(ZoneId.of("Turkey")).withFixedOffsetZone();
        System.out.println("Current Time  : " + outputDateFormat.format(turkeyZonedDateTime));

        /* WHEN */
        final ZonedDateTime turkeyZonedDateTimeAfter1Second = turkeyZonedDateTime.plusSeconds(1);
        final ZonedDateTime turkeyZonedDateTimeAfter2Second = turkeyZonedDateTime.plusSeconds(2);

        /* THEN */
        System.out.println("After 1 second: " + outputDateFormat.format(turkeyZonedDateTimeAfter1Second));
        Assertions.assertEquals(3, turkeyZonedDateTimeAfter1Second.getHour());
        Assertions.assertEquals(0, turkeyZonedDateTimeAfter1Second.getSecond());

        System.out.println("After 2 second: " + outputDateFormat.format(turkeyZonedDateTimeAfter2Second));
        Assertions.assertEquals(3, turkeyZonedDateTimeAfter2Second.getHour());
        Assertions.assertEquals(1, turkeyZonedDateTimeAfter2Second.getSecond());
    }
}
