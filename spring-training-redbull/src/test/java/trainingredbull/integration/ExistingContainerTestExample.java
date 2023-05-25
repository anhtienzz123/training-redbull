package trainingredbull.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class ExistingContainerTestExample {

    // Define a static container instance
    @Container
    private static final GenericContainer<?> existingContainer = new GenericContainer<>("mysql-test-container1")
            .withExposedPorts(3307); // Specify the ports that the existing container is listening on

    // Inject properties from the existing container into the Spring context
    @DynamicPropertySource
    static void containerProperties(DynamicPropertyRegistry registry) {
        // Retrieve the host and port information of the existing container
        String host = existingContainer.getHost();
        Integer port = existingContainer.getMappedPort(3307); // Map the exposed port of the existing container

        // Set the necessary properties for your application to connect to the existing container
        registry.add("spring.datasource.url", () -> "jdbc:mysql://localhost:3307/training_redbull" );
        registry.add("spring.datasource.username", () -> "root" );
        registry.add("spring.datasource.password", () -> "root" );
        
    }


    @Test
    public void testSomething() {
        // Use yourService to interact with the existing container
        // Write your test logic here

        // Example assertion
        assertEquals(true, true);
    }
}
