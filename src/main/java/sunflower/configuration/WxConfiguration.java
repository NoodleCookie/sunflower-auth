package sunflower.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "wx")
@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxConfiguration {

    private String url;

    private String appid;

    private String secret;
}
