package br.com.builders.service.filter;

import br.com.builders.model.entity.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFilter {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String document;
    private GenderEnum gender;
    private Instant createdAt;
}
