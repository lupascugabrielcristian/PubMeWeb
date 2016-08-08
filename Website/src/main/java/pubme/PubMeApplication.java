package pubme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import pubme.interfaces.*;
import pubme.services.*;

@SpringBootApplication
public class PubMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubMeApplication.class, args);
	}

	@Bean
	public MultipartResolver multipartResolver(){
		MultipartResolver multipartResolver = new StandardServletMultipartResolver();
		return multipartResolver;
	}

	@Bean
	public ILocationService getLocationService() {
		return new LocationService();
	}

	@Bean
	IGeneratorService getGeneratorService() {
		return new GeneratorService(getLocationService(), getResourceReader());
	}

	@Bean
	public IResourceReader getResourceReader() {
		return new ResourceReader();
	}

	@Bean
	public IUploadService getUploadService() {
		return new UploadService();
	}
}
