package com.tienda_api_rest.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "productos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    @NotNull(message = "El numero de producto no puede ser null")
    @Column(unique = true)
    private String numProducto;
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;
    @Positive
    private double precio;
    @Min(value = 0, message = "El valor minimo del stock es 0")
    private int stock;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_categoria")
    @Valid
    @NotNull(message = "La categoria no puede ser null")
    private Categoria categoria;

    @Column(name = "imagen_url")
    private String imagenUrl;
}
