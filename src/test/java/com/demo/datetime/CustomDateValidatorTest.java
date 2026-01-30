package com.demo.datetime;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAccessor;

import static java.time.temporal.ChronoField.OFFSET_SECONDS;

class CustomDateValidatorTest {

    @Test
    void customDateValidatorTest() {
        test("2019-10-27T02:30:00Europe/Prague");
        test("2019-03-31T02:30:00Europe/Prague");
        test("2014-03-31T08:00:00Turkey");
        test("2014-03-31T03:00:00Turkey");
        test("2019-10-27T02:30:00Europe/Prague+05:00");
        test("2019-10-27T02:30:00Europe/Prague+02:00");
        test("2019-10-27T02:30:00Europe/Prague+01:00");
    }

    private void test(final String dateText) {
        try {
            System.out.println(dateText + " -> " + parseStrict(dateText));
        } catch (DateTimeParseException e) {
            System.out.println(dateText + " - " + e.getMessage());
        }
    }

    private ZonedDateTime parseStrict(final String text) {
        final TemporalAccessor parsed = createFormatter().parse(text);
        final ZonedDateTime zonedDateTime = ZonedDateTime.from(parsed);

        if (parsed.isSupported(OFFSET_SECONDS)) {
            // Verify given offset was correct
            final ZoneOffset zoneOffset = ZoneOffset.from(parsed);
            if (! zoneOffset.equals(zonedDateTime.getOffset()))
                throw new DateTimeParseException("Incorrect offset: '" + text + "'", text, 0);
        } else {
            // Without offset, fail if in DST overlap time range
            if (! zonedDateTime.withEarlierOffsetAtOverlap().isEqual(zonedDateTime.withLaterOffsetAtOverlap()))
                throw new DateTimeParseException("Ambiguous time (DST overlap): '" + text + "'", text, 0);
        }

        // Verify time wasn't adjusted because it was in DST gap time range
        final LocalTime localTime = LocalTime.from(parsed);
        if (! localTime.equals(zonedDateTime.toLocalTime()))
            throw new DateTimeParseException("Invalid time (DST gap): '" + text + "'", text, 0);

        return zonedDateTime;
    }

    private DateTimeFormatter createFormatter() {
        return new DateTimeFormatterBuilder()
                .parseCaseSensitive()
                .parseStrict()
                .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .appendZoneRegionId()
                .optionalStart()
                .appendOffsetId()
                .optionalEnd()
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
    }
}
