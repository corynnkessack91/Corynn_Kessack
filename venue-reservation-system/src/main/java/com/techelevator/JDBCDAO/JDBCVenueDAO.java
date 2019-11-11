package com.techelevator.JDBCDAO;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCVenueDAO implements VenueDAO {

	private final static String SELECT_VENUE_SQL = "Select id, name, city_id, description From venue";
	private final static String SELECT_CATEGORY_VENUE_SQL = "SELECT venue.id, venue.name, city.name as city_name, state.abbreviation, description, string_agg(category.name, ', ') AS concategories FROM venue";

	private JdbcTemplate jdbcTemplate;
	private Venue venue;

	public JDBCVenueDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}

	@Override
	public List<Venue> getAllVenues() {
		List<Venue> venues = new ArrayList<Venue>();
		SqlRowSet rows = jdbcTemplate.queryForRowSet(SELECT_VENUE_SQL);

		while (rows.next()) {
			Venue venue = mapRowToVenue(rows);
			venues.add(venue);
		}
		return venues;
	}

	@Override
	public Venue getSpecificVenue(Long id) {
		String selectVenueSql = SELECT_CATEGORY_VENUE_SQL
				+ " JOIN city on venue.city_id = city.id JOIN state ON city.state_abbreviation = state.abbreviation LEFT JOIN category_venue ON venue.id = category_venue.venue_id LEFT JOIN category ON category_venue.category_id = category.id WHERE venue.id = ? GROUP BY venue.id, venue.name, city.name, state.abbreviation, description ORDER BY venue.name";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(selectVenueSql, id);
		Venue venue = new Venue();
		rows.next();
		venue = testToVenue(rows);

		return venue;
	}

	private Venue mapRowToVenue(SqlRowSet row) {
		Venue venue = new Venue();
		venue.setId(row.getLong("id"));
		venue.setName(row.getString("name"));
		venue.setCityId(row.getLong("city_id"));
		venue.setDescription(row.getString("description"));
		return venue;
	}

	private Venue testToVenue(SqlRowSet row) {
		Venue venue = new Venue();
		venue.setId(row.getLong("id"));
		venue.setName(row.getString("name"));
		venue.setDescription(row.getString("description"));
		venue.setCityName(row.getString("city_name"));
		venue.setCityAbbr(row.getString("abbreviation"));
		venue.setCategory(row.getString("concategories"));
		return venue;
	}

}
