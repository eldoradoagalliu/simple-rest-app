package org.quarkus;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(info = @Info(title = "Employee APIs", description = "Employee Application", version = "1.0.1",
        license = @License(name = "Microsoft", url = "http://localhost:8080")), tags = {@Tag(name = "employees", description = "Employees")})
public class EmployeeApplication extends Application {
}
