package uz.mu.lms.service;

import uz.mu.lms.dto.AcademicCalendarDto;
import uz.mu.lms.dto.ResponseDto;

public interface AcademicCalendarService {

    ResponseDto<AcademicCalendarDto> createAcademicCalendar(AcademicCalendarDto academicCalendarDto);

}
