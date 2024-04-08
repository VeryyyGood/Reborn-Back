package reborn.backend.reborn_15._1_reconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._1_reconnect.domain.Reconnect;

@Repository
public interface ReconnectRepository extends JpaRepository<Reconnect, Long>{

}
