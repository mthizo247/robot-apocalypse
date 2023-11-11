package tech.ioco.robot.apocalypse.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.ioco.robot.apocalypse.domain.Survivor;
import tech.ioco.robot.apocalypse.repository.SurvivorRepository;

import java.util.List;

@RestController
@RequestMapping("/survivors")
public class SurvivorApi {
    @Autowired
    SurvivorRepository survivorRepository;

    @GetMapping("")
    @Operation(summary = "Returns all survivors in the database",
            description = "This method returns all survivors in the database")
    public List<Survivor> getSurvivors() {
        return survivorRepository.findAll();
    }

    @PostMapping("")
    @Operation(summary = "Add survivor to the database",
            description = "This method adds a survivor to the database")
    public Survivor addSurvivor(@Validated @RequestBody
                                @Parameter(required = true)
                                        Survivor survivor) {
        return survivorRepository.save(survivor);
    }

    @PostMapping("/{idNumber}/latitude/{latitude}/longitude/{longitude}")
    @Operation(summary = "Update survivor location in the database",
            description = "This method updates survivor location in the database")
    public Survivor updateLocation(@PathVariable("idNumber") Long idNumber,
                                   @PathVariable("latitude") Double latitude,
                                   @PathVariable("longitude") Double longitude) {
        Survivor survivor = survivorRepository.findByIdNumber(idNumber);
        survivor.setLatitude(latitude);
        survivor.setLongitude(longitude);
        return survivorRepository.save(survivor);
    }

    @PostMapping("/{idNumber}/flagInfected/{flagInfected}")
    @Operation(summary = "Flag survivor infected in the database",
            description = "This method flags the survivor as infected in the database")
    public Survivor flagInfected(@PathVariable("idNumber") Long idNumber,
                                 @PathVariable("flagInfected") Boolean flagInfected) {

        Survivor survivor = survivorRepository.findByIdNumber(idNumber);
        survivor.setInfected(flagInfected);
        return survivorRepository.save(survivor);
    }

    @GetMapping("/percent/infected")
    @Operation(summary = "Get percentage of infected survivors",
            description = "This method get the percentage of infected survivors")
    public double percentInfected() {
        List<Survivor> survivors = survivorRepository.findAll();
        double infected = (double) survivors.stream().filter(Survivor::isInfected).count();
        return ((infected / survivors.size()) * 100);
    }

    @GetMapping("/percent/non-infected")
    @Operation(summary = "Get percentage of non infected survivors",
            description = "This method get the percentage of non infected survivors")
    public double percentNonInfected() {
        List<Survivor> survivors = survivorRepository.findAll();
        double nonInfected = (double) survivors.stream().filter(survivor -> !survivor.isInfected()).count();
        return ((nonInfected / survivors.size()) * 100);
    }

    @GetMapping("/infected")
    @Operation(summary = "Get list of infected survivors",
            description = "This method gets the list of infected survivors")
    public List<Survivor> getInfectedSurvivors() {
        return survivorRepository.findByInfected(true);
    }

    @GetMapping("/non-infected")
    @Operation(summary = "Get list of non infected survivors",
            description = "This method gets the list of non infected survivors")
    public List<Survivor> getNonInfectedSurvivors() {
        return survivorRepository.findByInfected(false);
    }
}
