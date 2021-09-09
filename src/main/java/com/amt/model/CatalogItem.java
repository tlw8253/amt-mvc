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
@Table(name = Constants.csCatalogItemTable)

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class CatalogItem implements Constants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csCatalogItemTblCatalogItemId)
	private int catalogItemId;

	@Column(name = cscsCatalogItemTblCatalogItemName, length = 20, nullable = false, unique = true)
	private String catalogItemName;

	@Column(name = csCatalogItemTblCatalogItem, length = 50, nullable = false)
	private String catalogItem;
	
	@Column(name = csCatalogItemTblCatalogItemDesc, length = ciDescriptionMaxLen, nullable = false)
	private String catalogItemDescription;
	
	@Column(name = csCatalogItemTblCatalogItemPrice, length = 255, nullable = false)
	private double catalogItemPrice;
	
	@Column(name = csCatalogItemTblCatalogItemInStockQty, length = 255, nullable = false)
	private int catalogItemInStockQty;

	@ManyToOne
	@JoinColumn(name = csCatalogItemTypeTblCatalogItemTypeId, nullable = false)
	private CatalogItemType catalogItemType;

	
	
	public CatalogItem(String catalogItemName, String catalogItem, String catalogItemDescription, double catalogItemPrice, int catalogItemInStockQty) {
		this.catalogItemName = catalogItemName;
		this.catalogItem = catalogItem;
		this.catalogItemDescription = catalogItemDescription;
		this.catalogItemPrice = catalogItemPrice;
		this.catalogItemInStockQty = catalogItemInStockQty;
	}


	
	
	
	
}
