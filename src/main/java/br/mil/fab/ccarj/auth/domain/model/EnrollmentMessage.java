package br.mil.fab.ccarj.auth.domain.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentMessage implements Serializable {
    private OperationType operation;
    private String cpf;
    private List<ProfileMessage> profiles;
}
