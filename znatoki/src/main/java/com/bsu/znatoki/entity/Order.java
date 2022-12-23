package com.bsu.znatoki.entity;

import com.bsu.znatoki.entity.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Table(name = "orders")
@Entity
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String description;

    @Column(name = "status", columnDefinition = "order_status")
    @Enumerated(EnumType.STRING)
    @Type(type = "com.bsu.znatoki.entity.enums.EnumTypePostgreSql")
    private OrderStatus status;

    @Column(name = "author_id")
    private int authorId;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "oreds_items",
                joinColumns = @JoinColumn(name = "orderId"),
                inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<OrderItem> orderItems;
}
