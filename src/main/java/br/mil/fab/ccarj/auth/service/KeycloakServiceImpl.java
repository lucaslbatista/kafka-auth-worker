package br.mil.fab.ccarj.auth.service;


import br.mil.fab.ccarj.auth.domain.model.EnrollmentMessage;
import br.mil.fab.ccarj.auth.domain.model.ProfileMessage;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    private Keycloak keyclakInstance;

    @PostConstruct
    private void init(){
        keyclakInstance = getKeyclakInstance("admin-cli");
    }

    private Keycloak getKeyclakInstance(String clientId) {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8086/auth")
                .realm("master")
                .username("admin")
                .password("admin")
                .clientId(clientId)
                .resteasyClient(new ResteasyClientBuilder()
                        .connectionPoolSize(10)
                        .build())
                .build();
    }

    @Override
    public void createClient(String clientId) {
        try {
            Keycloak keyclakInstance = getKeyclakInstance("admin-cli");
            RealmResource realm = keyclakInstance.realm("master");
            ClientRepresentation clientRepresentation = new ClientRepresentation();
            clientRepresentation.setProtocol("openid-connect");
            clientRepresentation.setName(clientId);
            clientRepresentation.setClientId(clientId);
            clientRepresentation.setSecret(clientId);
            realm.clients().create(clientRepresentation);

            realm.clients().findAll().forEach(ClientRepresentation::getClientId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assignUserToGroup(EnrollmentMessage message) {
        //Keycloak keyclakInstance = getKeyclakInstance("admin-cli");
        RealmResource realmResource = keyclakInstance.realm("master");

        List<GroupRepresentation> groupRepresentationList = new ArrayList<>();
        message.getProfiles().forEach(perfil -> {
            String groupPath = "//" + perfil.getClientId() + "//" + perfil.getName();
            groupRepresentationList.add(realmResource.getGroupByPath(groupPath));
        });


        List<UserRepresentation> result = realmResource.users().search(message.getCpf());
        if (result.size() > 1){
            //ERRROOUUUU
        }
        UserRepresentation user = result.get(0);


        List<String> ids = groupRepresentationList.stream().map(GroupRepresentation::getId).collect(Collectors.toList());
        UserResource userResource = realmResource.users().get(user.getId());
        ids.forEach(userResource::joinGroup);

    }

    @Override
    public void removeUserFromGroup(EnrollmentMessage message) {
        Keycloak keyclakInstance = getKeyclakInstance("admin-cli");
        RealmResource realmResource = keyclakInstance.realm("master");

        ProfileMessage profileMessage = message.getProfiles().get(0); //defender aqui

        String groupPath = "//" + profileMessage.getClientId() + "//" + profileMessage.getName();
        GroupRepresentation group = realmResource.getGroupByPath(groupPath);

        List<UserRepresentation> result = realmResource.users().search(message.getCpf());
        if (result.size() > 1){
            //ERRROOUUUU
        }
        UserRepresentation user = result.get(0);
        realmResource.users().get(user.getId()).leaveGroup(group.getId());
    }


}
