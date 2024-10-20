package uz.mu.lms.service.mapper;

public interface AbstractMapper<E, D> {
    D toDto(E entity);
    E toEntity(D dto);
}