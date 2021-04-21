package br.mil.fab.ccarj.auth.service;


import br.mil.fab.ccarj.auth.domain.model.EnrollmentMessage;

public interface KeycloakService {
    void createClient(String clientId);
    void assignUserToGroup(EnrollmentMessage message);
    void removeUserFromGroup(EnrollmentMessage message);
}
