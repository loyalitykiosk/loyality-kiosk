package com.kiosk.web.rest.mapper;

import com.kiosk.domain.*;
import com.kiosk.web.rest.dto.CardRequestDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CardRequest and its DTO CardRequestDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardRequestMapper {

    @Mapping(source = "kiosk.id", target = "kioskId")
    @Mapping(source = "kiosk.name", target = "kioskName")
    CardRequestDTO cardRequestToCardRequestDTO(CardRequest cardRequest);

    List<CardRequestDTO> cardRequestsToCardRequestDTOs(List<CardRequest> cardRequests);

    @Mapping(source = "kioskId", target = "kiosk")
    CardRequest cardRequestDTOToCardRequest(CardRequestDTO cardRequestDTO);

    List<CardRequest> cardRequestDTOsToCardRequests(List<CardRequestDTO> cardRequestDTOs);

    default Kiosk kioskFromId(Long id) {
        if (id == null) {
            return null;
        }
        Kiosk kiosk = new Kiosk();
        kiosk.setId(id);
        return kiosk;
    }
}
