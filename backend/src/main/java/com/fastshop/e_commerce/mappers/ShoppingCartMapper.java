package com.fastshop.e_commerce.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.fastshop.e_commerce.dtos.itemCart.ItemCartDTO;
import com.fastshop.e_commerce.dtos.shoppingCart.ShoppingCartDTO;
import com.fastshop.e_commerce.models.AccountBO;
import com.fastshop.e_commerce.models.ItemCartBO;
import com.fastshop.e_commerce.models.ShoppingCartBO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ShoppingCartMapper {

    public static ShoppingCartBO dtoToEntity(ShoppingCartDTO dto, AccountBO account) {
        ShoppingCartBO entity = new ShoppingCartBO();

        entity.setId(dto.getId());
        entity.setAccount(account);

        entity.setItems(dto.getItems().stream()
                .map(item -> ItemCartMapper.dtoToEntity(item, entity))
                .collect(Collectors.toList()));

        return entity;
    }

    public static ShoppingCartDTO entityToDto(ShoppingCartBO entity) {
        Long id = entity.getId();
        Long accountId = entity.getAccount().getId();

        return new ShoppingCartDTO(id, accountId);
    }

    public static ShoppingCartDTO entityToDto(ShoppingCartBO entity, List<ItemCartBO> items) {
        Long id = entity.getId();
        Long accountId = entity.getAccount().getId();
        List<ItemCartDTO> itemsToAdd = items.stream().map(ItemCartMapper::entityToDto).collect(Collectors.toList());

        return new ShoppingCartDTO(id, accountId, itemsToAdd);
    }
}
