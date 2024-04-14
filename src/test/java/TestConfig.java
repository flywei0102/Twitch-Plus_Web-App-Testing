import com.example.jupiter.controller.RecommendationController;
import com.example.jupiter.service.RecommendationService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class TestConfig {

    @Bean
    public RecommendationService recommendationService() {
        return Mockito.mock(RecommendationService.class);
    }

    @Bean
    public RecommendationController recommendationController() {
        return new RecommendationController();
    }
}
