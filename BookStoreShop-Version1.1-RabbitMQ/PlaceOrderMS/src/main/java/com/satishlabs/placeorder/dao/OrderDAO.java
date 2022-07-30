package com.satishlabs.placeorder.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satishlabs.placeorder.entity.MyOrder;

public interface OrderDAO extends JpaRepository<MyOrder, Integer>{
	public List<MyOrder> getOrderByUserId(String userId);
}
