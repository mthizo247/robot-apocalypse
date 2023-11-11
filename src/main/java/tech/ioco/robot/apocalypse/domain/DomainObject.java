package tech.ioco.robot.apocalypse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@ToString
abstract class DomainObject implements java.io.Serializable {
}
