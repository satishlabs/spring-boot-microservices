/**
 * 
 */
package com.satishlabs.placeorder.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.satishlabs.placeorder.config.PlaceOrderConfig;
import com.satishlabs.placeorder.dao.BookInventoryDAO;
import com.satishlabs.placeorder.dao.OrderDAO;
import com.satishlabs.placeorder.dao.OrderItemDAO;
import com.satishlabs.placeorder.entity.BookInventory;
import com.satishlabs.placeorder.entity.MyOrder;
import com.satishlabs.placeorder.entity.MyOrderItem;
import com.satishlabs.placeorder.service.OrderService;
import com.satishlabs.rabbitmq.BookInventoryInfo;
import com.satishlabs.rabbitmq.Order;
import com.satishlabs.rabbitmq.OrderInfo;
import com.satishlabs.rabbitmq.OrderItem;

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

	@RabbitListener(queues = PlaceOrderConfig.ORDER_QUEUE)
	public void placeOrder(OrderInfo orderInfo) {
		logInfo.info("--- 3. OrderServiceImpl --- placeOrder() ----");

		// Task1 - Insert records
		Order order = orderInfo.getOrder();
		MyOrder myorder = new MyOrder(order.getOrderDate(), order.getUserId(), order.getTotalQty(),
				order.getTotalCost(), order.getStatus());
		myorder = orderDAO.save(myorder);
		int orderId = myorder.getOrderId();

		// Task2 - Insert orderItems
		List<OrderItem> itemsList = orderInfo.getItemsList();
		for (OrderItem orderItem : itemsList) {
			orderItem.setOrderId(orderId);
			MyOrderItem myorderItem = new MyOrderItem(orderId, orderItem.getBookId(), orderItem.getQty(),
					orderItem.getCost());
			orderItemDAO.save(myorderItem);
		}

		// Task3 - Update Local BookInventory
		// Task4 - Update BookSearch BookInventory
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
	public List<MyOrder> getOrdersByUserId(String userId) {
		logInfo.info("--- OrderServiceImpl --- placeOrder() ----");
		List<MyOrder> orderList = orderDAO.getOrderByUserId(userId);
		return orderList;
	}

	@Override
	public MyOrder getOrderByOrderId(Integer orderId) {
		MyOrder myorder = orderDAO.findById(orderId).get();
		return myorder;
	}

}
