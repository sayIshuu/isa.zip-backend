package backend.zip.config;

import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}") // heroku redis의 경우 REDIS_URL에 포트번호와 host가 모두 들어있기 때문에 이렇게 연결해줘도 된다.
    private String host;
    @Bean //ConnectionFactory 빈으로 등록
    public RedisConnectionFactory redisConnectionFactory() {

        //RedisProperties 에서 application.properties에서 설정한 URL을 가져온다
        RedisURI redisURI = RedisURI.create(host);

        // URI를 가지고 Configuration을 만들어 준 다음에
        // Connection을 만들어주는 factory를 만들어 factory를 반환한다
        org.springframework.data.redis.connection.RedisConfiguration configuration = LettuceConnectionFactory.createRedisConfiguration(redisURI);

        //Lettuce는 전통적인 제디스보다 성능이 더 좋다
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);

        // Initializing 된다
        factory.afterPropertiesSet();

        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}