package com.example.helptest.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class StockDocument {
    @Id
    private long documentId;

    private String documentType;

    private LocalDateTime date;

    @JsonManagedReference
    @OneToMany(targetEntity = StockInventory.class, mappedBy = "stockDocument", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<StockInventory> inventories;

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Set<StockInventory> getInventories() {
        return inventories;
    }

    public void setInventories(Set<StockInventory> inventories) {
        this.inventories = inventories;
    }
}
