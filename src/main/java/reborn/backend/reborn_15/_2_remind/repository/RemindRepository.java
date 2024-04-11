package reborn.backend.reborn_15._2_remind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import reborn.backend.reborn_15._2_remind.domain.Remind;

@Repository
public interface RemindRepository extends JpaRepository<Remind, Long>, JpaSpecificationExecutor<Remind> {


}