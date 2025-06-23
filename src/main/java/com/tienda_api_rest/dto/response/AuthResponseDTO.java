package com.tienda_api_rest.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "mensaje", "jwt", "status"})
public record AuthResponseDTO (
        String email,
        String mensaje,
        String jwt,
        boolean status
){
}
