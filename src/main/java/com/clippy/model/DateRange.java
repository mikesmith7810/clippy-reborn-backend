package com.clippy.model;

import java.time.LocalDate;

public record DateRange(LocalDate start, LocalDate end) {

    public static DateRange today() {
        LocalDate today = LocalDate.now();
        return new DateRange(today, today);
    }
}
