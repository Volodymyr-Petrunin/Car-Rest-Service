package org.car.rest.domain.convert;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
@Converter(autoApply = true)
public class YearAttributeConverter implements AttributeConverter<Year, Short> {
    @Override
    public Short convertToDatabaseColumn(Year year) {
        if (year == null) {
            return null;
        }
        return (short) year.getValue();
    }

    @Override
    public Year convertToEntityAttribute(Short aShort) {
        if (aShort == null) {
            return null;
        }
        return Year.of(aShort);
    }
}
