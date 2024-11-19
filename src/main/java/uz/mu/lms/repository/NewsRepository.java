package uz.mu.lms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    Page<News> findAllByOrderByIdDesc(Pageable pageable);
}
