package reborn.backend.reborn_15._5_reborn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reborn.backend.reborn_15._4_remember.domain.Remember;
import reborn.backend.reborn_15._5_reborn.domain.Reborn;

@Repository
public interface RebornRepository extends JpaRepository<Reborn, Long> {

}
