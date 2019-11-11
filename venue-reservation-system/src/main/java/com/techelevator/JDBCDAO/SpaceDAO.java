package com.techelevator.JDBCDAO;

import java.time.LocalDate;
import java.util.List;

public interface SpaceDAO {
	
	public List<Space> getAllSpaces(Long id);
	
	public List<Space> getOpenSpaces(Long id, LocalDate startDate, LocalDate endDate, int maxOccup);
	
	public Boolean inSeason(Long id, LocalDate startDate);

}
