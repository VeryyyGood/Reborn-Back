package reborn.backend.reborn_15._5_reborn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._5_reborn.domain.Reborn;

import java.util.Optional;

@Repository
public interface RebornRepository extends JpaRepository<Reborn, Long>, JpaSpecificationExecutor<Reborn> {
    Optional<Reborn> findTopByPetOrderByDateDesc(Pet pet);
}
