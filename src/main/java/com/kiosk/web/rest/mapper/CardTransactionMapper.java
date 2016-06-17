package com.kiosk.web.rest.mapper;

import com.kiosk.domain.*;
import com.kiosk.web.rest.dto.CardTransactionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CardTransaction and its DTO CardTransactionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardTransactionMapper {

    CardTransactionDTO cardTransactionToCardTransactionDTO(CardTransaction cardTransaction);

    List<CardTransactionDTO> cardTransactionsToCardTransactionDTOs(List<CardTransaction> cardTransactions);

    CardTransaction cardTransactionDTOToCardTransaction(CardTransactionDTO cardTransactionDTO);

    List<CardTransaction> cardTransactionDTOsToCardTransactions(List<CardTransactionDTO> cardTransactionDTOs);
}
