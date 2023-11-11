package tech.ioco.robot.apocalypse.api;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tech.ioco.robot.apocalypse.domain.Survivor;
import tech.ioco.robot.apocalypse.repository.SurvivorRepository;
import tech.ioco.robot.apocalypse.util.Utils;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SurvivorApi.class)
public class SurvivorApiTest {
    @Autowired
    SurvivorApi survivorApi;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SurvivorRepository survivorRepository;

    @Test
    public void givenEmptySurvivors_whenGetSurvivors_thenReturnEmptyList() throws Exception {
        // given
        survivorRepository.deleteAll();

        // when
        when(survivorRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/survivors")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(0)))
        ;
    }

    @Test
    public void givenSurvivorsExists_whenGetSurvivors_thenReturnNonEmptyList() throws Exception {
        // given
        List<Survivor> survivors = Utils.createSurvivors(5);

        // when
        when(survivorRepository.findAll()).thenReturn(survivors);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/survivors")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(5)))
        ;
    }

    @Test
    public void test_updateLocation() throws Exception {
        // given
        Long idNumber = RandomUtils.nextLong();
        Survivor survivor = Utils.createSurvivors(1).get(0);

        // when
        when(survivorRepository.findByIdNumber(idNumber)).thenReturn(survivor);
        when(survivorRepository.save(any(Survivor.class))).thenReturn(survivor);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/survivors/" + idNumber + "/latitude/25/longitude/26")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.latitude").value(25))
                .andExpect(jsonPath("$.longitude").value(26))
        ;
    }

    @Test
    public void test_flagInfected() throws Exception {
        // given
        Long idNumber = RandomUtils.nextLong();
        Survivor survivor = Utils.createSurvivors(1).get(0);
        survivor.setInfected(false);

        // when
        when(survivorRepository.findByIdNumber(idNumber)).thenReturn(survivor);
        when(survivorRepository.save(any(Survivor.class))).thenReturn(survivor);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/survivors/" + idNumber + "/flagInfected/true")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.infected").value(true))
        ;
    }
}