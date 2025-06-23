package com.tienda_api_rest.exception;

import java.util.List;

public record ErrorResponse(
        int codigo,
        String mensaje,
        List<String> errores
) {
}
