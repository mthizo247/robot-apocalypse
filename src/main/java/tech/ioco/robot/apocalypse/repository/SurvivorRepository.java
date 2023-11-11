package tech.ioco.robot.apocalypse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ioco.robot.apocalypse.domain.Survivor;

import java.util.List;

public interface SurvivorRepository extends JpaRepository<Survivor, Long> {
    Survivor findByIdNumber(Long idNumber);
    List<Survivor> findByInfected(boolean infected);
}