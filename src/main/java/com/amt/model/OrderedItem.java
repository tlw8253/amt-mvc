package com.amt.model;

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
@Table(name = Constants.csOrderItemsTable)


@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class OrderedItem implements Constants {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csOrderItemsTblOrderItemsId)
	private int orderItemId;

	@Column(name = csOrderItemsTblOrderPrice)
	private double orderItemPrice;
	
	@Column(name = csOrderItemsTblItemQty)
	private int orderItemQty;

	@ManyToOne
	@JoinColumn(name = csOrderTblOrderId, nullable = false) // 
	private Order order;

	@ManyToOne
	@JoinColumn(name = csCatalogItemTblCatalogItemId, nullable = false) // 
	private CatalogItem catalogItem;


	public OrderedItem(Double orderPrice, int orderQty) {
		this.orderItemPrice = orderPrice;
		this.orderItemQty = orderQty;
	}

	public OrderedItem(Double orderPrice, int orderQty, CatalogItem catalogItem) {
		this.orderItemPrice = orderPrice;
		this.orderItemQty = orderQty;
		this.catalogItem = catalogItem;
	}


	


	
	
}
