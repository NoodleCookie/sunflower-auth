package sunflower.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/test")
public class ControllerTest {

    @Value("${test}")
    private String test;

    @GetMapping
    public String test() {
        return "welcome to sunflower auth server: " + test;
    }
}
