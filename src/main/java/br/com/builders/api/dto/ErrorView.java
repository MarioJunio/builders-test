package br.com.builders.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorView {
    private String message;
}
