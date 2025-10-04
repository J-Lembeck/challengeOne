package com.fiap.core.domain;

import com.fiap.core.domain.enums.WorkOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class WorkOrder {
    private UUID id;
    private Customer customer;
    //TODO implementar cada domain
    //private Vehicle vehicle;
    //private User createdBy;
    //private User assignedMechanic;
    //List<WorkOrderPart> workOrderParts;
    //List<WorkOrderServiceModel> workOrderServices;
    private WorkOrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime finishedAt;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WorkOrder(UUID id, Customer customer, WorkOrderStatus status) {
        this.id = id;
        this.customer = customer;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public WorkOrder(Customer customer) {
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.status = WorkOrderStatus.RECEIVED;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }

    public void setStatus(WorkOrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
