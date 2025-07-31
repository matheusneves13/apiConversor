package br.srm.wefin.apiConversor.util;

import br.srm.wefin.apiConversor.model.TipoMoeda;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoMoedaConverter implements AttributeConverter<TipoMoeda, Integer>{

	@Override
	public Integer convertToDatabaseColumn(TipoMoeda attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
	}

	@Override
	public TipoMoeda convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoMoeda.fromCodigo(dbData); 
	}

}
