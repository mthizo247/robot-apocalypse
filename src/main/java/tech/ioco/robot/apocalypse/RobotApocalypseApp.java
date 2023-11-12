package tech.ioco.robot.apocalypse;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@EntityScan(basePackages = "tech.ioco.robot.apocalypse.domain")
@RestController
@Hidden
public class RobotApocalypseApp {
    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        SpringApplication.run(RobotApocalypseApp.class, args);
    }

    @GetMapping(value = {"", "/"})
    public String ok() {
        return "ok";
    }

    @RequestMapping(value = "/swagger")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("swagger-ui/index.html#/");
    }

    @Bean
    public OpenAPI openAPI() {
        String docUrl = "https://ioco.atlassian.net/wiki/spaces/pages/robot-apocalypse-ronald-mthombeni-assessment";
        String docDescription = "Confluence documentation";


        return new OpenAPI()
                .components(new Components())
                .addServersItem(new Server().url("/"))
                .info(new Info().title("Robot Apocalypse Ronald Mthombeni Assessment").version("1.0")).
                        externalDocs(new ExternalDocumentation().url(docUrl).description(docDescription));
    }
}