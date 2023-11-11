package tech.ioco.robot.apocalypse.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import tech.ioco.robot.apocalypse.domain.Robot;
import tech.ioco.robot.apocalypse.domain.Survivor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private Utils() {
    }

    public static List<Survivor> createSurvivors(int howMany) {
        List<Survivor> survivors = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            Survivor survivor = new Survivor();
            survivor.setId(RandomUtils.nextLong());
            survivor.setName(RandomStringUtils.random(10));
            survivor.setIdNumber(RandomUtils.nextLong());
            survivor.setInfected(false);
            survivor.setAge(RandomUtils.nextInt());
            survivor.setGender(RandomStringUtils.random(4));
            survivor.setLongitude(RandomUtils.nextDouble());
            survivor.setLatitude(RandomUtils.nextDouble());
            survivors.add(survivor);
        }
        return survivors;
    }

    public static List<Robot> createRobots(int howMany) {
        List<Robot> robots = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            Robot robot = new Robot();
            robot.setId(RandomUtils.nextLong());
            robot.setModel(RandomStringUtils.random(10));
            robot.setSerialNumber(RandomStringUtils.random(10));
            robot.setLatitude(RandomUtils.nextDouble());
            robot.setLongitude(RandomUtils.nextDouble());
            robot.setCategory(RandomStringUtils.random(4));
            robots.add(robot);
        }
        return robots;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getResponseString(HttpURLConnection conn) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }
}
