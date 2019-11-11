package com.techelevator;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.JDBCDAO.JDBCVenueDAO;
import com.techelevator.JDBCDAO.Venue;
import com.techelevator.JDBCDAO.VenueDAO;

public class JDBCVenueDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;

	JdbcTemplate jdbcTemplate;
	private VenueDAO dao;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior-venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new JDBCVenueDAO(dataSource);

	}

	@Test
	public void get_list_VENUE_from_menu() {
		String sql = "truncate venue CASCADE";
		jdbcTemplate.update(sql);

		String createVenueSql = "INSERT INTO venue (id, name, city_id, description) VALUES (DEFAULT, ?, ?, ?)";
		jdbcTemplate.update(createVenueSql, "test", 1l, "Hi");

		List<Venue> returnedVenue = dao.getAllVenues();

		Assert.assertEquals(1, returnedVenue.size());

	}

	@Test
	public void get_Specific_Venue() {
		Venue venue = testToVenue() ;
		String insertSql = "INSERT INTO venue (name, city_id, description) VALUES ( ?, ?, ?) RETURNING id";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(insertSql, venue.getName(), venue.getCityId(), venue.getDescription());
		rows.next();
		venue.setId(rows.getLong("id"));
		
		Venue returnedVenue = dao.getSpecificVenue(venue.getId());
		
		Assert.assertNotNull(returnedVenue);
		Assert.assertEquals(venue.getId(), returnedVenue.getId());
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	protected DataSource getDataSource() {
		return dataSource;
	}
	
	private Venue testToVenue() {
		Venue venue = new Venue();
		venue.setId((long) 5);
		venue.setName("Smirking Stone Bistro");
		venue.setCityId((long) 2);
		venue.setDescription("This fantastical and tropical venue is the perfect escape. Why not take a staycation here and enjoy a lovely tiki theme!");
		venue.setCityName("Srulbury");
		venue.setCityAbbr("OH");
		return venue;
	}

}
