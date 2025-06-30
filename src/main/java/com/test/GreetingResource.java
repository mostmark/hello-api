package com.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @ConfigProperty(name = "GREETING_MESSAGE", defaultValue = "there") 
    String message;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello " + message + " from " + getHostName() + ", the time is " + new Date();
    }

    static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            // Fallback to environment variables
            String envHostname = System.getenv("HOSTNAME");
            if (envHostname != null && !envHostname.isEmpty()) {
                return envHostname;
            }
            envHostname = System.getenv("COMPUTERNAME"); // Windows
            if (envHostname != null && !envHostname.isEmpty()) {
                return envHostname;
            }
            return "UnknownHost";
        }
    }
}
