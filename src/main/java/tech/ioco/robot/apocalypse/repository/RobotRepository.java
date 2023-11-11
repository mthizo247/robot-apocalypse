package tech.ioco.robot.apocalypse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ioco.robot.apocalypse.domain.Robot;

public interface RobotRepository extends JpaRepository<Robot, Long> {
}