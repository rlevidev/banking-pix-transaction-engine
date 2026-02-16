package com.banking.transactionservice.api.mapper;

import com.banking.transactionservice.api.dto.TransactionResponseDTO;
import com.banking.transactionservice.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
  // Entity -> DTO
  TransactionResponseDTO toResponseDTO(Transaction transaction);

  // List Entity -> List DTO
  List<TransactionResponseDTO> toResponseList(List<Transaction> transactions);
}
