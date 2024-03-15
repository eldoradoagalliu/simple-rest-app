package org.quarkus;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.quarkus.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/employees")
@Tag(name = "Employee Resource", description = "Employee REST APIs")
public class EmployeeResource {
    public static List<Employee> employees = new ArrayList<>();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getEmployees", summary = "Get Employees", description = "Get all employees inside the list")
    @APIResponse(responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response getEmployees() {
        return Response.ok(employees).build();
    }

    @GET
    @Path("/size")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(operationId = "countEmployees", summary = "Count Employees", description = "Size of the list of employees")
    @APIResponse(responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    public Integer countEmployees() {
        return employees.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(operationId = "addEmployee", summary = "Add a new Employee", description = "Add a new employee into the list of employees")
    @APIResponse(responseCode = "201", description = "Employee added", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response addEmployee(
            @RequestBody(description = "Employee to add", required = true,
                    content = @Content(schema = @Schema(implementation = Employee.class))) Employee employee) {
        employees.add(employee);
        return Response.status(Response.Status.CREATED).entity(employee).build();
    }

    @PUT
    @Path("{id}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(operationId = "updateEmployee", summary = "Update an existing Employee", description = "Update a employee inside the list of employees")
    @APIResponse(responseCode = "200", description = "Employee updated", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response updateEmployee(@Parameter(description = "Employee id", required = true) @PathParam("id") Long id,
                                   @Parameter(description = "Employee title", required = true) @PathParam("name") String name) {
        employees = employees.stream().map(employee -> {
            if (employee.getId().equals(id)) {
                employee.setName(name);
            }
            return employee;
        }).collect(Collectors.toList());
        return Response.ok(employees).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(operationId = "deleteEmployee", summary = "Delete an existing Employee", description = "Delete a employee inside the list of employees")
    @APIResponse(responseCode = "204", description = "Employee deleted", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @APIResponse(responseCode = "400", description = "Employee not valid", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response deleteEmployee(@PathParam("id") Long id) {
        Optional<Employee> employeeToDelete = employees.stream().filter(employee -> employee.getId().equals(id))
                .findFirst();

        boolean removed = false;
        if (employeeToDelete.isPresent()) {
            removed = employees.remove(employeeToDelete.get());
        }

        if (removed) {
            return Response.noContent().build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
