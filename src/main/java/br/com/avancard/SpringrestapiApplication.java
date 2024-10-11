package br.com.avancard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.avancard.model"}) // Define explicitamente os pacotes onde o Spring deve procurar as classes de entidade JPA.
@ComponentScan(basePackages = {"br.com.avancard.*"}) // Especifica onde o Spring deve escanear os componentes da aplicação, como classes anotadas com @Component, @Service, @Repository, @Controller
@EnableJpaRepositories(basePackages = {"br.com.avancard.repository"}) // Habilita a detecção automática de repositórios JPA, que geralmente são interfaces que estendem JpaRepository ou CrudRepository.
@EnableTransactionManagement // Habilita o suporte para transações no Spring, permitindo que métodos sejam anotados com @Transactional para que possam participar de transações.
@EnableWebMvc // Habilita a configuração manual do Spring MVC (geralmente desnecessário com Spring Boot).
@RestController // Indica que a classe é um controlador REST e os métodos retornarão dados como JSON ou XML.
public class SpringrestapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringrestapiApplication.class, args);
    }

}
