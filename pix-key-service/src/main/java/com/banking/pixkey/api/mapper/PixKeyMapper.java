package com.banking.pixkey.api.mapper;

import com.banking.pixkey.api.dto.PixKeyResponseDTO;
import com.banking.pixkey.domain.model.PixKey;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PixKeyMapper {
  // Entity -> DTO
  PixKeyResponseDTO toResponseDTO(PixKey pixKey);

  // List Entity -> List DTO
  List<PixKeyResponseDTO> toResponseList(List<PixKey> pixKeys);
}
