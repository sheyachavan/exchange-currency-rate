package com.exchange.rates.endpoints.api;

import com.shop.services.mycart.constant.Constants;
import com.shop.services.mycart.dto.OrderDTO;
import com.shop.services.mycart.dto.ResultBean;
import com.shop.services.mycart.exception.MyCartException;
import com.shop.services.mycart.model.Customer;
import com.shop.services.mycart.model.Order;
import com.shop.services.mycart.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class OrderController
{

    @Autowired
    OrderService orderService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(value = "/addOrder", consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResultBean addOrder(@RequestBody OrderDTO orderDTO)
    {
        if (orderDTO.getQuantity() == 0 ||  orderDTO.getQuantity() > 25)
            return new ResultBean(Constants.EC_INVALID_QUANTITY, Constants.EM_INVALID_QUANTITY);

        final Customer customer = new Customer(orderDTO.getCustomerId());
        final Order order = new Order(new Date().getTime(), orderDTO.getQuantity(), customer);

        orderService.addOrder(order);

        return new ResultBean(Constants.SC_ORDER_ADDED, Constants.SM_ORDER_ADDED);
    }

    @GetMapping("/getQueuePositionAndWaitTime/{customerId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public OrderDTO getQueuePositionAndWaitTime(@PathVariable Integer customerId)
    {
        return convertEntityToDTO(orderService.getQueuePositionAndWaitTime(customerId));
    }

    @GetMapping("/getAllOrdersAndWaitTime")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public List<Order> getAllOrdersAndWaitTime()
    {
        return orderService.getAllOrdersAndWaitTime();
    }

    @GetMapping("/nextDelivery")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public List<OrderDTO> nextDelivery()
    {
        return convertEntitiesToDTOs(orderService.nextDelivery());
    }

    @DeleteMapping("/deleteOrder/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultBean deleteOrder(@PathVariable Integer customerId) throws MyCartException
    {
        int res = orderService.deleteOrderByCustomerId(customerId);

        return new ResultBean(Constants.SC_ORDER_DELETED, Constants.SC_ORDER_DELETED);
    }

    private OrderDTO convertEntityToDTO(Order order)
    {
        return modelMapper.map(order, OrderDTO.class);
    }

    private List<OrderDTO> convertEntitiesToDTOs(List<Order> orders)
    {
        final List<OrderDTO> orderDTOS = new ArrayList<>();

        orders.forEach(o -> orderDTOS.add(modelMapper.map(o, OrderDTO.class)));

        return orderDTOS;
    }
}
