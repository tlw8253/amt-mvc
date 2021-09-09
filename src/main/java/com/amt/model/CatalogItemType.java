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
@Table(name = Constants.csCatalogItemTypeTable)


@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class CatalogItemType implements Constants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csCatalogItemTypeTblCatalogItemTypeId)
	private int catalogItemTypeId = 0;

	@Column(name = csCatalogItemTypeTblCatalogItemType, length = ciRoleTypeLen, nullable = false, unique = true)
	private String catalogItemType = "";

	@Column(name = csCatalogItemTypeTblCatalogItemTypeDesc, length = ciRoleTypeDescLen, nullable = false)
	private String catalogItemTypeDesc = "";

	public CatalogItemType(String catalogType, String catalogTypeDesc) {
		this.catalogItemType = catalogType;
		this.catalogItemTypeDesc = catalogTypeDesc;
	}


	
	
}
