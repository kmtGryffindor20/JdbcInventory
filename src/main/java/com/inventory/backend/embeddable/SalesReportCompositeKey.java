package com.inventory.backend.embeddable;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReportCompositeKey implements Serializable {

    private Integer day;

    private Integer month;

    private Integer year;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SalesReportCompositeKey)) {
            return false;
        }
        SalesReportCompositeKey that = (SalesReportCompositeKey) obj;
        return this.day.equals(that.day) && this.month.equals(that.month) && this.year.equals(that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }
    
}
