package uz.mu.lms.dto;

import java.time.LocalDate;

public record AcademicCalendarDto(

        Integer id,

        Integer departmentId,

        LocalDate sem1StartDate,

        LocalDate sem1MidtermStart,

        LocalDate sem1MidtermEnd,

        LocalDate sem1FinalStart,

        LocalDate sem1FinalEnd,

        LocalDate sem1EndDate,

        LocalDate sem2StartDate,

        LocalDate sem2MidtermStart,

        LocalDate sem2MidtermEnd,

        LocalDate sem2FinalStart,

        LocalDate sem2FinalEnd,

        LocalDate sem2EndDate
) {}
