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

public class MarketplaceApplication extends Application<MarketplaceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MarketplaceApplication().run(args);
    }

    @Override
    public String getName() {
        return "MarketPlace";
    }

    @Override
    public void initialize(final Bootstrap<MarketplaceConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<MarketplaceConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(MarketplaceConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final MarketplaceConfiguration configuration,
                    final Environment environment) {
        final ProjectDAO projectDAO = new HashMapProjectDAO();
        final ProjectService projectService = new ProjectService(projectDAO);
        final ProjectResource resource = new ProjectResource(projectService);
        environment.healthChecks().register("datasource", new DataSourceHealthCheck(projectDAO));
        environment.jersey().register(resource);
    }
}
