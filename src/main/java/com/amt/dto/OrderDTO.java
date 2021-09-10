package com.amt.dto;

import java.sql.Timestamp;
import java.util.List;

import com.amt.model.OrderStatus;
import com.amt.model.OrderedItem;
import com.amt.model.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTO - Data Transfer Object
// This is to be used in the controller class to get parameters by body
@Getter @Setter @EqualsAndHashCode @ToString @NoArgsConstructor
public class OrderDTO {
	private User customer;
	private double orderAmount;
	private Timestamp orderSubmitted;
	private Timestamp orderSent;
	private OrderStatus orderStatus;
	private List<OrderedItem> lstOrderedItem;

	public OrderDTO (double orderAmount, Timestamp orderSubmitted, Timestamp orderSent) {
		this.orderAmount = orderAmount;
		this.orderSubmitted = orderSubmitted;
		this.orderSent = orderSent;
	}

	
	

}// END Class
