package com.banking.account.api.mapper;

import com.banking.account.api.dto.AccountResponseDTO;
import com.banking.account.domain.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
  AccountResponseDTO toResponseDTO(Account account);
}
