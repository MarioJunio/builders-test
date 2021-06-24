package br.com.builders.model.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "customer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String email;
    private String phone;
    private String document;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @CreatedDate
    private Instant createdAt;

    @Version
    private Long version;

    @PrePersist
    public void setCreatedAt() {
        createdAt = Instant.now();
    }
}
