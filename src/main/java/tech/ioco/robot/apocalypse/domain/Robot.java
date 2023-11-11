package tech.ioco.robot.apocalypse.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@JsonTypeName("Robot")
@Entity
@Data
@NoArgsConstructor
public class Robot extends IdentityDomainObject {
    String model;
    String serialNumber;
    Date manufacturedDate;
    String category;
    Double latitude;
    Double longitude;
}
