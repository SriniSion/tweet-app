package com.tweetapp.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).paths(Predicates.not(PathSelectors.regex(SwaggerConstant.ERROR_PATH)))
				.paths(Predicates.not(PathSelectors.regex(SwaggerConstant.ACTUATOR_PATH))).build().apiInfo(metadata())
				.pathMapping(SwaggerConstant.BACK_SLASH);
	}

	@SuppressWarnings("rawtypes")
	private ApiInfo metadata() {
		final ApiInfo apiInfo = new ApiInfo(
				SwaggerConstant.API_DESCRIPTION, SwaggerConstant.API_NAME, SwaggerConstant.VERSION,
				SwaggerConstant.EMPTY_STRING, new springfox.documentation.service.Contact(SwaggerConstant.NAME,
						SwaggerConstant.URL, SwaggerConstant.EMAIL),
				SwaggerConstant.NAME, SwaggerConstant.EMPTY_STRING, new ArrayList<VendorExtension>());
		return apiInfo;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler(SwaggerConstant.SWAGGER_UI).addResourceLocations(SwaggerConstant.RESOURCE_LOCATION);
		registry.addResourceHandler(SwaggerConstant.WEB_JARS).addResourceLocations(SwaggerConstant.WEB_JARS_LOCATION);

	}

}
