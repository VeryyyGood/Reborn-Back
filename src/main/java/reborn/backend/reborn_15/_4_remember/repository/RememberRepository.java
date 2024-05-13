package reborn.backend.reborn_15._4_remember.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._4_remember.domain.Remember;

import java.util.List;
import java.util.Optional;

@Repository
public interface RememberRepository extends JpaRepository<Remember, Long>, JpaSpecificationExecutor<Remember> {
    Optional<Remember> findTopByPetOrderByDateDesc(Pet pet);

    Optional<Remember> findByPetAndDate(Pet pet, Integer date);

    List<Remember> findAllByPetAndDateLessThanOrderByDateAsc(Pet pet, Integer date);

}
