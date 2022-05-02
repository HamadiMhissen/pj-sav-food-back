package com.projetSav.PjSav.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "application.jwt")
@Component
@Getter
@Setter
@NoArgsConstructor
public class JwtConfig {
	private String secret;
	private String tokenDuree;
	private String tokenPrefix;
	private String authorizationValue;
	


}
