package com.test;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class LandingPage {

    @Inject
    Template index;

    @ConfigProperty(name = "COLOR", defaultValue = "blue") 
    String color;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getLandingPage(){
        return index.data("color", color);
    }
    
}
