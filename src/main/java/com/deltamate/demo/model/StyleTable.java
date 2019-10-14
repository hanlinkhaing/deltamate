package com.deltamate.demo.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
public class StyleTable implements Comparable<StyleTable>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('GuessFull','GuessOff','Kohls','NineWest')")
    private Buyer buyer;

    private String groupName;

    private String styleCode;

    private String buyerCode;

    private int grandTotal;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('HB', 'SLG')")
    private BagType bagType;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime insertDate;

    @OneToMany(mappedBy = "style_fk", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Ex_Fty> ex_ftys = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private WorkStandard workStandard;

    public StyleTable() {
    }

    public StyleTable(Buyer buyer, String groupName, String styleCode, String buyerCode,
                      BagType bagType, LocalDateTime insertDate, int grandTotal) {
        this.buyer = buyer;
        this.groupName = groupName;
        this.styleCode = styleCode;
        this.buyerCode = buyerCode;
        this.bagType = bagType;
        this.insertDate = insertDate;
        this.grandTotal = grandTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public BagType getBagType() {
        return bagType;
    }

    public void setBagType(BagType bagType) {
        this.bagType = bagType;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public List<Ex_Fty> getEx_ftys() {
        return ex_ftys;
    }

    public void setEx_ftys(List<Ex_Fty> ex_ftys) {
        this.ex_ftys = ex_ftys;
    }

    public WorkStandard getWorkStandard() {
        return workStandard;
    }

    public void setWorkStandard(WorkStandard workStandard) {
        this.workStandard = workStandard;
    }

    public int getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(int grandTotal) {
        this.grandTotal = grandTotal;
    }

    @Override
    public int compareTo(StyleTable o) {
        return this.insertDate.compareTo(o.insertDate);
    }

    public static Comparator<StyleTable> InsertDateComparator = new Comparator<StyleTable>() {
        @Override
        public int compare(StyleTable o1, StyleTable o2) {
            return o2.getInsertDate().compareTo(o1.getInsertDate());
        }
    };
}
