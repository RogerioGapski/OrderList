package com.orderList.orderList.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "items_table")
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "items_id", nullable = false, unique = true, updatable = false,  insertable = false)
    private Integer id;

    @NotNull
    @Column(name = "products_quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(nullable = false)
    private Double unitary_price;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.REMOVE)
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
