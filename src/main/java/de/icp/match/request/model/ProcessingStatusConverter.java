package de.icp.match.request.model;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ProcessingStatusConverter implements AttributeConverter<ProcessingStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProcessingStatus processingStatus) {
        if (processingStatus == null) {
            return null;
        }
        return processingStatus.getCode();
    }

    @Override
    public ProcessingStatus convertToEntityAttribute(Integer processingStatusCode) {
        if (processingStatusCode == null) {
            return null;
        }

        return Stream.of(ProcessingStatus.values())
                .filter(processingStatus -> processingStatus.getCode().equals(processingStatusCode))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
