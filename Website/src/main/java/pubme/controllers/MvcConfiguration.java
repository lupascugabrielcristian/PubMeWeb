package pubme.controllers;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import pubme.interfaces.IGeneratorService;
import pubme.interfaces.ILocationService;
import pubme.interfaces.IResourceReader;
import pubme.services.GeneratorService;
import pubme.services.LocationService;
import pubme.services.ResourceReader;

@ComponentScan(basePackages = "pubme.controllers")
public class MvcConfiguration {

//    @Bean
//    public MultipartResolver multipartResolver(){
//        MultipartResolver multipartResolver = new StandardServletMultipartResolver();
//        return multipartResolver;
//    }

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
}
