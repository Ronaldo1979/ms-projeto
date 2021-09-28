package br.com.cognitivebrasil;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import com.google.common.collect.Lists;
import br.com.cognitivebrasil.util.DadosTest;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableFeignClients
@SpringBootApplication
@EnableSwagger2
public class MsGestaoMunicipalProjetoApplication {
	
	 public static final String AUTHORIZATION_HEADER = "Authorization";
	 public static final String DEFAULT_INCLUDE_PATTERN = "/resource/.*";

	public static void main(String[] args) {
		SpringApplication.run(MsGestaoMunicipalProjetoApplication.class, args);
		
		DadosTest dt = new DadosTest();
		dt.criaProjetoTest();
	}
	
	@Bean
	public Docket gestaoMuniciaplprojetoAPI() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(this.apiDetails())
				.securityContexts(Arrays.asList(securityContext()))
			    .securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.cognitivebrasil"))
				.paths(PathSelectors.ant("/**"))
				.build();
	}
	
	private ApiInfo apiDetails() {
		
		return new ApiInfo(
				"Gestão Municipal - Projetos", 
				"API do microsserviço de gestão de projetos",
				"1.0", 
				"Teams of Service", 
				new springfox.documentation.service.Contact("Ronaldo Oliveira", "https://qintess.com","ronaldo.oliveira@qintess.com"), 
				"API License", 
				"https://qintess.com");
	}
	
	private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
            .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
            new SecurityReference("JWT", authorizationScopes));
    }
}
