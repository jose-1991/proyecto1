package org.jflores.project.service;

import org.jflores.project.dao.OrderDAO;
import org.jflores.project.models.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    OrderDAO orderDAO;

    @Mock
    Scanner scanner;

    @InjectMocks
    OrderService orderService;

    Order actualOrder;
    @BeforeEach
    void setUp() {
        actualOrder = new Order();
    }

    @Test
    void addNewOrder() {
        actualOrder = orderService.addNewOrder();
        assertNotNull(actualOrder);
    }

    @Test
    void modifyOrder() {
    }

    @Test
    void deleteOrder() {
    }
}