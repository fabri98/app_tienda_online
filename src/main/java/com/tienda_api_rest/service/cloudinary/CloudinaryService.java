package com.tienda_api_rest.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String subirImagen(MultipartFile imagen) throws IOException {
        Map resultado = cloudinary.uploader().upload(imagen.getBytes(), ObjectUtils.emptyMap());

        return resultado.get("secure_url").toString();
    }
}
