package sunflower.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserContext {
    private static final ThreadLocal<String> user = new ThreadLocal<>();

    public static void setUser(String username) {
        user.set(username);
    }

    public static String getUser() {
        return user.get();
    }
}
