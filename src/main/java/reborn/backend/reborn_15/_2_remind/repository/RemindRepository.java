package reborn.backend.reborn_15._2_remind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._2_remind.domain.Remind;

import java.util.List;
import java.util.Optional;

@Repository
public interface RemindRepository extends JpaRepository<Remind, Long>, JpaSpecificationExecutor<Remind> {
    Optional<Remind> findTopByPetOrderByDateDesc(Pet pet);

    Optional<Remind> findByPetAndDate(Pet pet, Integer date);

    List<Remind> findAllByPetAndDateLessThanOrderByDateDesc(Pet pet, Integer date);

}