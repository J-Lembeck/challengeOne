package com.fiap.challenge.workOrders.entity;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_orders")
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleModel vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private UserModel createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_mechanic_id")
    private UserModel assignedMechanic;

    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WorkOrderItem> items;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkOrderStatus status;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "finished_at")
    private OffsetDateTime finishedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public void recalculateTotal() {
        if (items == null || items.isEmpty()) {
            this.totalAmount = BigDecimal.ZERO;
        } else {
            this.totalAmount = items.stream()
                    .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }
}
