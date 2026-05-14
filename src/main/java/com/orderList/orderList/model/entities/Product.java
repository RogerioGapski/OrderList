package com.orderList.orderList.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    private Long id;

    @NotBlank
    @Column(name = "product_name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "product_price", nullable = false)
    private Double price;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Long stock;

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
