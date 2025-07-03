package com.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @ConfigProperty(name = "GREETING_MESSAGE", defaultValue = "there") 
    String message;

    @ConfigProperty(name = "API_URL", defaultValue = "./hello")
    String url;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        String response = null;
        
        if(!url.equals("./hello")){
            response = callApi(url);
        }
        else{
            response = "Hello " + message + " from " + getHostName() + ", the time is " + new Date();
        }
        return response;
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

    static String callApi(String url){
        String response = null;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            response = httpResponse.body();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            response = e.getMessage();
        }

        return response;
    }
}
