package reborn.backend.reborn_15._4_remember.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._2_remind.domain.Remind;
import reborn.backend.reborn_15._4_remember.domain.Remember;

@Repository
public interface RememberRepository extends JpaRepository<Remember, Long>{

}
