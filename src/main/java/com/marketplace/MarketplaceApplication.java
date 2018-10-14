package com.marketplace;

import com.marketplace.core.ProjectService;
import com.marketplace.dao.HashMapProjectDAO;
import com.marketplace.dao.ProjectDAO;
import com.marketplace.health.DataSourceHealthCheck;
import com.marketplace.resources.ProjectResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

/*
main method to start up application
 */
public class MarketPlaceApplication extends Application<MarketPlaceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MarketPlaceApplication().run(args);
    }

    @Override
    public String getName() {
        return "MarketPlace";
    }

    @Override
    public void initialize(final Bootstrap<MarketPlaceConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<MarketPlaceConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(MarketPlaceConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final MarketPlaceConfiguration configuration,
                    final Environment environment) {
        final ProjectDAO projectDAO = new HashMapProjectDAO();
        final ProjectService projectService = new ProjectService(projectDAO);
        final ProjectResource resource = new ProjectResource(projectService);
        environment.healthChecks().register("datasource", new DataSourceHealthCheck(projectDAO));
        environment.jersey().register(resource);
    }
}
