package hw;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<Config> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "hw";
    }

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(Config config, Environment environment) throws Exception {
        final Resource resource = new Resource(config.getDefaultName());
        final MyHealthCheck healthCheck = new MyHealthCheck();

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}
