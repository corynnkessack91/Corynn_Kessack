package com.techelevator.JDBCDAO;

import java.util.List;

public interface VenueDAO {
	
	public List<Venue> getAllVenues();
	
	public Venue getSpecificVenue(Long id);

}
