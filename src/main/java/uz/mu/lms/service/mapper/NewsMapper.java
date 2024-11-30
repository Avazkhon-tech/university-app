package uz.mu.lms.service.mapper;

import org.mapstruct.Mapper;
import uz.mu.lms.dto.NewsDto;
import uz.mu.lms.model.News;

@Mapper(componentModel = "spring")
public abstract class NewsMapper implements AbstractMapper<News, NewsDto> {
}
