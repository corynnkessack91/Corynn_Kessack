package com.corynnkessack.inventoryApp.model;

import java.util.List;

public interface InventoryDAO {
	
	public List<Inventory> getAllInventory();
	
	public Inventory getByCode(String code);

	
}