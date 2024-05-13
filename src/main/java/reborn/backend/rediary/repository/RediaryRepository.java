package reborn.backend.rediary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import reborn.backend.rediary.domain.Rediary;
import reborn.backend.user.domain.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RediaryRepository extends JpaRepository<Rediary, Long>, JpaSpecificationExecutor<Rediary> {

    List<Rediary> findAllByRediaryWriterOrderByCreatedAtDesc(String userName);

    List<Rediary> findAllByRediaryCreatedAt(LocalDate date);
}
