package com.amt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.amt.app.Constants;

@Entity
@Table(name = Constants.csOrderStatusTable)

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class OrderStatus implements Constants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csOrderStatusTblOrderStatusId)
	private int orderStatusId = 0;

	@Column(name = csOrderStatusTblOrderStatus, length = 10, nullable = false, unique = true)
	private String orderStatus = "";

	@Column(name = csOrderStatusTblOrderStatusDesc, length = 150, nullable = false)
	private String orderStatusDesc = "";
	
	public OrderStatus(String orderStatus, String orderStatusDesc) {
		this.orderStatus = orderStatus;
		this.orderStatusDesc = orderStatusDesc;
	}

	

	
	
	
}
