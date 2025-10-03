package converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import model.AikidoRank;

@Converter(autoApply = true)
public class AikidoRankConverter implements AttributeConverter<AikidoRank, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AikidoRank rank) {
        if (rank == null) return null;
        return rank.ordinal();
    }

    @Override
    public AikidoRank convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;
        return AikidoRank.values()[dbData];
    }
}