package de.icp.match.request.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ExemptionCategoryConverter implements AttributeConverter<ExemptionCategory, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ExemptionCategory exemptionCategory) {
        if (exemptionCategory == null) {
            return null;
        }
        return exemptionCategory.getCode();
    }

    @Override
    public ExemptionCategory convertToEntityAttribute(Integer exemptionCategoryCode) {
        if (exemptionCategoryCode == null) {
            return null;
        }

        return Stream.of(ExemptionCategory.values())
                .filter(exemptionCategory -> exemptionCategory.getCode().equals(exemptionCategoryCode))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
