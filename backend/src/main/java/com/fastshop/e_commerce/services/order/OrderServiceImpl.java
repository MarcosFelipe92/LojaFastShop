package com.fastshop.e_commerce.services.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.auth.AuthService;
import com.fastshop.e_commerce.dtos.address.AddressDTO;
import com.fastshop.e_commerce.dtos.order.OrderDTO;
import com.fastshop.e_commerce.dtos.user.UserDTO;
import com.fastshop.e_commerce.enuns.OrderStatus;
import com.fastshop.e_commerce.exceptions.common.NotFoundException;
import com.fastshop.e_commerce.mappers.AddressMapper;
import com.fastshop.e_commerce.mappers.OrderMapper;
import com.fastshop.e_commerce.mappers.UserMapper;
import com.fastshop.e_commerce.models.AddressBO;
import com.fastshop.e_commerce.models.OrderBO;
import com.fastshop.e_commerce.models.UserBO;
import com.fastshop.e_commerce.repositories.OrderRepository;
import com.fastshop.e_commerce.services.user.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final AuthService authService;
    private final UserService userService;

    public List<OrderDTO> findAll(JwtAuthenticationToken token) {
        Long userId = authService.getUserId(token);
        return repository.findByAccount_User_Id(userId).stream().map(OrderMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO findById(Long id, JwtAuthenticationToken token) {
        OrderBO entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        Long orderOwnerId = entity.getAccount().getUser().getId();
        if (!hasAccessToOrder(orderOwnerId, token)) {
            throw new AccessDeniedException("Não é possível acessar um pedido que não seja seu.");
        }

        return OrderMapper.entityToDto(entity);
    }

    @Transactional
    public OrderDTO create(OrderDTO dto, AddressDTO addressDto, JwtAuthenticationToken token) {
        UserDTO userDTO = userService.findById(authService.getUserId(token), token);
        UserBO user = UserMapper.dtoToEntity(userDTO);
        AddressBO address = AddressMapper.dtoToEntity(addressDto, user.getAccount());

        OrderBO order = OrderMapper.dtoToEntity(dto, user.getAccount(), address);
        order.setStatus(OrderStatus.PENDING);

        order = repository.save(order);

        return OrderMapper.entityToDto(order, order.getItems());
    }

    @Override
    public void delete(Long id, JwtAuthenticationToken token) {
        OrderBO entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        Long orderOwnerId = entity.getAccount().getUser().getId();
        if (!hasAccessToOrder(orderOwnerId, token)) {
            throw new AccessDeniedException("Não é possível acessar um pedido que não seja seu.");
        }

        repository.deleteById(id);
    }

    private boolean hasAccessToOrder(Long orderOwnerId, JwtAuthenticationToken token) {
        Long userId = authService.getUserId(token);
        return orderOwnerId.equals(userId) || authService.validateUserPermission(token);
    }
}
