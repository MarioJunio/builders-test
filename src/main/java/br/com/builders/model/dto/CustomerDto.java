package br.com.builders.model.dto;

import br.com.builders.model.entity.GenderEnum;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String email;
    private String phone;
    private String document;
    private GenderEnum gender;
    private Instant createdAt;
    private Long version;
}
