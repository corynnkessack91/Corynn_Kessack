package com.corynnkessack.inventoryApp.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCInventoryDAO implements InventoryDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCInventoryDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Inventory> getAllInventory(String itemName) {
		List<Inventory> allInventory = new ArrayList<Inventory>();
		String sqlSelectAllItems = "SELECT itemcode, itemname, itemdescription, itemquantity, itemphoto FROM items";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllItems);
		while (results.next()) {
			Inventory inventory = mapRowSetToInventory(results);
			allInventory.add(inventory);
		}
		return allInventory;
	}

	@Override
	public Inventory getByCode(String code) {
		SqlRowSet result = jdbcTemplate.queryForRowSet("SELEECT * FROM item")
			if (result.next()) {
				return mapRowSetToInventory(result);
			}
		return null;
	}
	
	private Inventory mapRowSetToInventory(SqlRowSet results)
	{
		Inventory i = new Inventory();
		i.setItemName(results.getString("itemname"));
		i.setItemCode(results.getInt("itemcode"));
		i.setItemDescription(results.getString("itemdescription"));
		i.setItemQuantity(results.getInt("itemquantity"));
		return i;
	}
}
