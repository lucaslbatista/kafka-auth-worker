package br.mil.fab.ccarj.auth.domain.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PerfilMessage implements Serializable {
    private String name;
    private String clientId;
}
