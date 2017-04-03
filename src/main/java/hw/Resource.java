package hw;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/hw")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {
    private final String defaultName;

    public Resource(String defaultName) {
        this.defaultName = defaultName;
    }

    @GET
    @Timed
    public Msg sayHello(@QueryParam("q") Optional<String> name) {
        return new Msg(name.orElse(defaultName));
    }
}
