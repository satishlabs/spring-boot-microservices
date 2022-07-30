/**
 * 
 */
package com.satishlabs.placeorder.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.satishlabs.placeorder.config.PlaceOrderConfig;
import com.satishlabs.placeorder.dao.BookInventoryDAO;
import com.satishlabs.placeorder.dao.OrderDAO;
import com.satishlabs.placeorder.dao.OrderItemDAO;
import com.satishlabs.placeorder.dto.OrderInfo;
import com.satishlabs.placeorder.entity.BookInventory;
import com.satishlabs.placeorder.entity.Order;
import com.satishlabs.placeorder.entity.OrderItem;
import com.satishlabs.placeorder.service.OrderService;
import com.satishlabs.rabbitmq.BookInventoryInfo;

/**
 * @author Satish
 * 
 *         Jul 18, 2022
 */

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	static Logger logInfo = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private OrderItemDAO orderItemDAO;

	@Autowired
	private BookInventoryDAO bookInventoryDAO;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void placeOrder(OrderInfo orderInfo) {
		logInfo.info("--- OrderServiceImpl --- placeOrder() ----");

		// Task1 - Insert records
		Order myorder = orderInfo.getOrder();
		myorder = orderDAO.save(myorder);
		int orderId = myorder.getOrderId();

		// Task2 - Insert orderItems
		List<OrderItem> itemsList = orderInfo.getItemsList();
		for (OrderItem myorderItem : itemsList) {
			myorderItem.setOrderId(orderId);
			orderItemDAO.save(myorderItem);
		}

		// Task3 - Update Local BookInventory
		// Task4 - Update BookSearch BookInventory

		// RestTemplate orderRestTemp = new RestTemplate();
		// String endpoint = "http://localhost:8000/updateBookInventory/";

		for (OrderItem myOrderItem : itemsList) {
			Integer bookId = myOrderItem.getBookId();
			BookInventory bookInventory = bookInventoryDAO.findById(bookId).get();
			Integer currentStock = bookInventory.getBooksAvailable();
			currentStock = currentStock - myOrderItem.getQty();
			bookInventory.setBooksAvailable(currentStock);

			// Local Inventory
			bookInventoryDAO.save(bookInventory);

			// BookSearchMS Inventory
			// orderRestTemp.put(endpoint, bookInventory);
			BookInventoryInfo bookInventoryInfo = new BookInventoryInfo();
			bookInventoryInfo.setBookId(bookInventory.getBookId());
			bookInventoryInfo.setBooksAvailable(bookInventory.getBooksAvailable());
			rabbitTemplate.convertAndSend(PlaceOrderConfig.INVENTORY_QUEUE, bookInventoryInfo);
		}

	}

	@Override
	public List<Order> getOrdersByUserId(String userId) {
		logInfo.info("--- OrderServiceImpl --- placeOrder() ----");
		List<Order> orderList = orderDAO.getOrderByUserId(userId);
		return orderList;
	}

	@Override
	public Order getOrderByOrderId(Integer orderId) {
		Order myorder = orderDAO.findById(orderId).get();
		return myorder;
	}

}
