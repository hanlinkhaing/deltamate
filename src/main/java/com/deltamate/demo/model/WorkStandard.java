package com.deltamate.demo.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class WorkStandard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;

    private String orderNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private LocalDate insertedDate;

    @OneToOne(mappedBy = "workStandard", fetch = FetchType.EAGER)
    private StyleTable styleTable;

    @Transient
    private MultipartFile file;

    public WorkStandard() {
    }

    public WorkStandard(String path, String orderNo) {
        this.path = path;
        this.orderNo = orderNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public LocalDate getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(LocalDate insertedDate) {
        this.insertedDate = insertedDate;
    }

    public StyleTable getStyleTable() {
        return styleTable;
    }

    public void setStyleTable(StyleTable styleTable) {
        this.styleTable = styleTable;
    }
}
