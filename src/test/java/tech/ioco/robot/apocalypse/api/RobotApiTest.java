package tech.ioco.robot.apocalypse.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tech.ioco.robot.apocalypse.domain.Robot;
import tech.ioco.robot.apocalypse.repository.RobotRepository;
import tech.ioco.robot.apocalypse.util.Utils;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RobotApi.class)
public class RobotApiTest {
    @Autowired
    RobotApi robotApi;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RobotRepository robotRepository;

    @Test
    public void givenEmptyRobots_whenGetRobots_thenReturnEmptyList() throws Exception {
        // given
        robotRepository.deleteAll();

        // when
        when(robotRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/robots")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(0)))
        ;
    }

    @Test
    public void givenRobotsExists_whenGetRobots_thenReturnNonEmptyList() throws Exception {
        // given
        List<Robot> robots = Utils.createRobots(5);

        // when
        when(robotRepository.findAll()).thenReturn(robots);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/robots")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(5)))
        ;
    }

    @Test
    public void test_connectRobots() throws Exception {
        // given
        List<Robot> robots = Utils.createRobots(10);

        // when
        when(robotRepository.saveAll(any())).thenReturn(robots);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/robots")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(10)))
        ;
    }
}