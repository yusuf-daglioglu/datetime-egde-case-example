package com.demo.datetime;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class InvalidDateTest {

    /*
     Reference for the timezone change:
     "Turkey begins DST on 2014-03-31"
     https://www.oracle.com/java/technologies/tzdata-versions.html
     https://www.trthaber.com/haber/turkiye/yaz-saati-uygulamasina-gecildi-122625.html
     "Türkiye'de saatler, bu gece saat 03.00'dan itibaren 1 saat ileri alındı."
     */

    @Test
    void javaTimeDoesNotThrowExcepitonForNonExitanceDate(){
        final DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("uuuu MMM dd HH:mm:ss Z (z) (VV)");

        final ZonedDateTime zonedDateTimeInvalid = ZonedDateTime.parse(
                "2014 Mar 31 03:00:01 (Turkey)",
                DateTimeFormatter.ofPattern("uuuu MMM dd HH:mm:ss (VV)"));
        System.out.println("Valid:          " + outputDateFormat.format(zonedDateTimeInvalid));

        final ZonedDateTime zonedDateTimeInvalid2 = ZonedDateTime.parse(
                "2014 Mar 31 03:00:01 +0200 (Turkey)",
                DateTimeFormatter.ofPattern("uuuu MMM dd HH:mm:ss Z (VV)"));
        System.out.println("Valid:          " + outputDateFormat.format(zonedDateTimeInvalid2));

        final ZonedDateTime zonedDateTimeInvalid3 = ZonedDateTime.parse(
                "2014 Mar 31 01:00:01 +0000 (Turkey)",
                DateTimeFormatter.ofPattern("uuuu MMM dd HH:mm:ss Z (VV)"));
        System.out.println("Valid:          " + outputDateFormat.format(zonedDateTimeInvalid3));
    }
}
