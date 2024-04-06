package reborn.backend.reveal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import reborn.backend.reveal.domain.Reveal;

import java.util.List;

@Repository
public interface RevealRepository extends JpaRepository<Reveal, Long>, JpaSpecificationExecutor<Reveal> {

    List<Reveal> findAllByRevealWriterOrderByCreatedAt(String userName);

    List<Reveal> findAllByRevealCreatedAt(Integer date);

}
