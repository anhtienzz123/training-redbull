package trainingredbull.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtils {

    public static Properties loadResourceProperties(String filepath) {
        Properties properties = new Properties();
        try (InputStream inputStream =
                CommonUtils.class.getClassLoader().getResourceAsStream(filepath);) {

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            properties.load(br);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static Long getUserId() {
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        return Long.valueOf(userIdStr);
    }
}
