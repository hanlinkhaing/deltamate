package com.deltamate.demo.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Ex_Fty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String po;

    private String destination;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate exFtyDate;


    private int quantity;

    @ManyToOne
    private StyleTable style_fk;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    public Ex_Fty() {
    }

    public Ex_Fty(String po, String destination, LocalDate exFtyDate, int quantity, StyleTable style_fk) {
        this.po = po;
        this.destination = destination;
        this.exFtyDate = exFtyDate;
        this.quantity = quantity;
        this.style_fk = style_fk;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getExFtyDate() {
        return exFtyDate;
    }

    public void setExFtyDate(LocalDate exFtyDate) {
        this.exFtyDate = exFtyDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public StyleTable getStyle_fk() {
        return style_fk;
    }

    public void setStyle_fk(StyleTable style_fk) {
        this.style_fk = style_fk;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
