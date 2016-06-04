package com.kiosk.web.rest.mapper;

import com.kiosk.domain.*;
import com.kiosk.web.rest.dto.KioskDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Kiosk and its DTO KioskDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface KioskMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.customerName", target = "customerCustomerName")
    KioskDTO kioskToKioskDTO(Kiosk kiosk);

    List<KioskDTO> kiosksToKioskDTOs(List<Kiosk> kiosks);

    @Mapping(source = "customerId", target = "customer")
    Kiosk kioskDTOToKiosk(KioskDTO kioskDTO);

    List<Kiosk> kioskDTOsToKiosks(List<KioskDTO> kioskDTOs);
}
