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

    CardDTO cardToCardDTO(Card card);

    List<CardDTO> cardsToCardDTOs(List<Card> cards);

    Card cardDTOToCard(CardDTO cardDTO);

    List<Card> cardDTOsToCards(List<CardDTO> cardDTOs);
}
