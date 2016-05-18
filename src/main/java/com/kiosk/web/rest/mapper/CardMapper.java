package com.kiosk.web.rest.mapper;

import com.kiosk.domain.*;
import com.kiosk.web.rest.dto.CardDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Card and its DTO CardDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface CardMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.customerName", target = "userCustomerName")
    CardDTO cardToCardDTO(Card card);

    List<CardDTO> cardsToCardDTOs(List<Card> cards);

    @Mapping(source = "userId", target = "user")
    Card cardDTOToCard(CardDTO cardDTO);

    List<Card> cardDTOsToCards(List<CardDTO> cardDTOs);
}
