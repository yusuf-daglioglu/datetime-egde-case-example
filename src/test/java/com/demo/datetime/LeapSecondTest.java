package com.demo.datetime;

import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

class LeapSecondTest {

    @Test
    void leapSecondTest(){
        ZonedDateTime start = ZonedDateTime.of( 2015, 6, 30, 23, 59, 58, 0, ZoneOffset.UTC );
        for(int i=0; i<5; i++){
            start = start.plusSeconds(1);
            System.out.println(start);
        }
    }
}
