package reborn.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reborn.backend.user.domain.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

}
