package reborn.backend.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reborn.backend.global.entity.Uuid;

public interface UuidRepository extends JpaRepository<Uuid, Long> {

}
