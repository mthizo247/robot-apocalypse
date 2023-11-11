package tech.ioco.robot.apocalypse.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ioco.robot.apocalypse.domain.Robot;
import tech.ioco.robot.apocalypse.repository.RobotRepository;
import tech.ioco.robot.apocalypse.util.Utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/robots")
public class RobotApi {
    @Autowired
    RobotRepository robotRepository;

    @GetMapping("")
    @Operation(summary = "Returns all robots in the database",
            description = "This method returns all robots in the database")
    public List<Robot> getRobots() {
        List<Robot> robots = robotRepository.findAll();
        robots.sort(new PropertyComparator<>("category", true, true));
        return robots;
    }

    @PostMapping("")
    @Operation(summary = "Connect to the Robot CPU system and retrieve all robots",
            description = "This method connects to the Robot CPU system and retrieve all robots")
    public List<Robot> connectRobots() throws Exception {
        URL url = new URL("https://robotstakeover20210903110417.azurewebsites.net/robotcpu");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        String json = Utils.getResponseString(con);
        ObjectMapper mapper = new ObjectMapper();
        List<Robot> robots = mapper.readValue(json, new TypeReference<List<Robot>>() {
        });
        robots.sort(new PropertyComparator<>("category", true, true));
        return robotRepository.saveAll(robots);
    }
}
