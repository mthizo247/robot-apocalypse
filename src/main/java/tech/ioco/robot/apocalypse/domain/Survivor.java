package tech.ioco.robot.apocalypse.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@JsonTypeName("Survivor")
@Entity
@Data
@NoArgsConstructor
public class Survivor extends IdentityDomainObject {
    @Column(nullable = false)
    @NotNull(message = "Name cannot be null")
    String name;
    @Column(nullable = false, unique = true)
    Long idNumber;
    @Column(nullable = false)
    Integer age;
    @Column(nullable = false)
    String gender;
    @Column(nullable = false)
    Double latitude;
    @Column(nullable = false)
    Double longitude;
    boolean infected;
}
