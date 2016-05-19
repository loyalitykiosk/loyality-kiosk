package com.kiosk.web.rest.mapper;

import com.kiosk.domain.*;
import com.kiosk.web.rest.dto.PromotionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Promotion and its DTO PromotionDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface PromotionMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.customerName", target = "userCustomerName")
    PromotionDTO promotionToPromotionDTO(Promotion promotion);

    List<PromotionDTO> promotionsToPromotionDTOs(List<Promotion> promotions);

    @Mapping(source = "userId", target = "user")
    Promotion promotionDTOToPromotion(PromotionDTO promotionDTO);

    List<Promotion> promotionDTOsToPromotions(List<PromotionDTO> promotionDTOs);
}
