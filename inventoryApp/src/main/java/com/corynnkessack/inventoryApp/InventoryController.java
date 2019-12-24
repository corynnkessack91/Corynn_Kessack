package com.corynnkessack.inventoryApp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.corynnkessack.inventoryApp.model.Inventory;
import com.corynnkessack.inventoryApp.model.InventoryDAO;
import com.corynnkessack.inventoryApp.model.NeededItemDAO;

@Controller
//@SessionAttributes();
public class InventoryController {
	
	@Autowired
	private InventoryDAO inventoryDAO;
	
	@Autowired
	private NeededItemDAO neededItemDAO;
	
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String displayHomePage(HttpServletRequest request) {
		List<Inventory> inventory = inventoryDAO.getAllInventory();
		request.setAttribute("inventory", inventory);
		return "homePage";
	}
	
	@RequestMapping(path = "/seeItem", method = RequestMethod.GET)
	public String displayItem(ModelMap map, @RequestParam String code) {
		Inventory inventory = 
		
		
	}
	
	@RequestMapping(path = "/addnewitem", method = RequestMethod.GET)
	public String addNewItem(ModelMap map, @RequestParam String code) {
		
	
		
		return  "newItem";
	}
	
	@RequestionMapping(path = "/")

	//homepage
	//detail page
	//survey form page (show GET)
	//survey (POST)
	//survey result (GET)
	
	
	//homepage (populates db results) GET
	//item page GET
	//addItem form (GET)
	//addItem form (POST)
	//addItem form display (GET)
	//neededItem (GET) (needed item does not interact w inventory db)
	//neededItem (POST)
	//needed Item display (GET)
	
	//homepage: selectable icons to: item
	//		header: Home (homepage), Add Item (addItem), Add needed item (neededItem)
	//
	
	//Inventory DB and needed item: item code, item name, quantity, description, photo
	//
	
}





