package com.fiap.challenge.workOrders.averagetime;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avarage_time_work_order")
public class AvarageTimeWorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "work_order_id", nullable = false)
    private UUID workOrder;

    @Column(name = "start_time", nullable = false)
    private Duration startTime;

    @Column(name = "end_time", nullable = false)
    private Duration endTime;
}
