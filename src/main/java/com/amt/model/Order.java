package com.amt.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.amt.app.Constants;

@Entity
@Table(name = Constants.csOrderTable)

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class Order implements Constants {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csOrderTblOrderId)
	private int orderId = 0;

	@Column(name = csOrderTblAmount, nullable = false)	
	private double orderAmount = 0.0;
		
	//this is set internally
	@Column(name = csOrderTblSubmitted, nullable = false)
	private Timestamp orderSubmitted;

	//this is set internally
	@Column(name = csOrderTblSent)
	private Timestamp orderSent;

	@ManyToOne
	@JoinColumn(name = csOrderStatusTblOrderStatusId)	//, nullable = false) // 
	private OrderStatus orderStatus;

	@ManyToOne
	@JoinColumn(name = csUserTblUserId)	//, nullable = false) // 
	private User customer;

	
	public Order(double orderAmount, Timestamp orderSubmitted, Timestamp orderSent, OrderStatus orderStatus, User customer) {
		this.orderAmount = orderAmount;
		this.orderSubmitted = orderSubmitted;
		this.orderSent = orderSent;
		this.orderStatus = orderStatus;
		this.customer = customer;
	}
	
	

	
	
}
