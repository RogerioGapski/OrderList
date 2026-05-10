package com.orderList.orderList.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "products_table")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "product_id", nullable = false, unique = true, insertable = false)
    private Integer id;

    @NotBlank
    @Column(name = "product_name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "product_price", nullable = false)
    private Double price;

    @NotNull
    @Column(nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "items_id")
    private OrderItem orderItem;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
