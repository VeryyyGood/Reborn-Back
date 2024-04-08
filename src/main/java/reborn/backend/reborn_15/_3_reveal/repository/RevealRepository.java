package reborn.backend.reborn_15._3_reveal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import reborn.backend.reborn_15._3_reveal.domain.Reveal;

import java.util.List;

@Repository
public interface RevealRepository extends JpaRepository<Reveal, Long>, JpaSpecificationExecutor<Reveal> {

}
