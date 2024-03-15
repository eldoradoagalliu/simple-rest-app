package org.quarkus.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Employee", description = "Employee representation")
public class Employee {
    private Long id;
    @Schema(required = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
