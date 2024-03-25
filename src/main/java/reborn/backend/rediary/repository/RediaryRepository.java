package reborn.backend.rediary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import reborn.backend.rediary.domain.Rediary;

import java.util.List;

@Repository
public interface RediaryRepository extends JpaRepository<Rediary, Long>, JpaSpecificationExecutor<Rediary> {

    List<Rediary> findAllByOrderByCreatedAtDesc();
}
