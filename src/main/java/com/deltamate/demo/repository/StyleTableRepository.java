package com.deltamate.demo.repository;

import com.deltamate.demo.model.BagType;
import com.deltamate.demo.model.Buyer;
import com.deltamate.demo.model.StyleTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StyleTableRepository extends JpaRepository<StyleTable, Long> {
    @Query(value = "select s from StyleTable s where s.bagType like :bagType and s.buyer like :buyer and "  +
            "s.styleCode like :styleCode and s.buyerCode like :buyerCode and s.id in " +
            "(select ex.style_fk from Ex_Fty ex where ex.destination like :destination and ex.po like :po)")
    List<StyleTable> findStyleTableByNoExFtyDate(@Param("bagType")BagType bagType, @Param("buyer")Buyer buyer,
                                                 @Param("styleCode") String styleCode, @Param("buyerCode") String buyerCode,
                                                 @Param("destination") String destination, @Param("po") String po);

    @Query(value = "select s from StyleTable s where s.bagType like :bagType and s.buyer like :buyer and " +
            "s.styleCode like :styleCode and s.buyerCode like :buyerCode and s.id in " +
            "(select ex.style_fk from Ex_Fty ex where ex.destination like :destination and ex.po like :po " +
            "and ex.exFtyDate >= :fromDate)")
    List<StyleTable> findStyleTableByFromDate(@Param("bagType")BagType bagType, @Param("buyer")Buyer buyer,
                                              @Param("styleCode") String styleCode, @Param("buyerCode") String buyerCode,
                                              @Param("destination") String destination, @Param("po") String po,
                                              @Param("fromDate")LocalDate fromDate);

    @Query(value = "select s from StyleTable s where s.bagType like :bagType and s.buyer like :buyer and " +
            "s.styleCode like :styleCode and s.buyerCode like :buyerCode and s.id in " +
            "(select ex.style_fk from Ex_Fty ex where ex.destination like :destination and ex.po like :po " +
            "and ex.exFtyDate <= :toDate)")
    List<StyleTable> findStyleTableByToDate(@Param("bagType")BagType bagType, @Param("buyer")Buyer buyer,
                                            @Param("styleCode") String styleCode, @Param("buyerCode") String buyerCode,
                                              @Param("destination") String destination, @Param("po") String po,
                                            @Param("toDate")LocalDate toDate);

    @Query(value = "select s from StyleTable s where s.bagType like :bagType and s.buyer like :buyer and " +
            "s.styleCode like :styleCode and s.buyerCode like :buyerCode and s.id in " +
            "(select ex.style_fk from Ex_Fty ex where ex.destination like :destination and ex.po like :po " +
            "and ex.exFtyDate >= :fromDate and ex.exFtyDate <= :toDate)")
    List<StyleTable> findStyleTableByExFtyDates(@Param("bagType")BagType bagType, @Param("buyer")Buyer buyer,
                                                @Param("styleCode") String styleCode, @Param("buyerCode") String buyerCode,
                                                @Param("destination") String destination,@Param("po") String po,
                                                @Param("fromDate")LocalDate fromDate, @Param("toDate")LocalDate toDate);

}
