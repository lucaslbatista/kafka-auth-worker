package br.mil.fab.ccarj.auth.domain.model;

public enum OperationType {
    INSERT("INSERT"),
    DELETE("DELETE");

    private String description;

    OperationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
