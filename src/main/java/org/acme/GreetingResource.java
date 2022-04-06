package org.acme;

import org.jboss.logging.Logger;

import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

@Path("/hello")
public class GreetingResource {

    private static final Logger LOG = Logger.getLogger(GreetingResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @Inject
    MeterRegistry registry;

    @GET
    @Path("/{name}")
    public String sayHello(@PathParam(value = "name") String name) {
        LOG.info("Name is: " + name);
        registry.counter("greeting_counter", Tags.of("name", name)).increment();

        return "Hello " + name + "!";
    }


}