package com.techelevator;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.JDBCDAO.JDBCSpaceDAO;
import com.techelevator.JDBCDAO.Space;
import com.techelevator.JDBCDAO.SpaceDAO;

public class JDBCSpaceDAOIntegrationTest {
	
	private static SingleConnectionDataSource dataSource;

	JdbcTemplate jdbcTemplate;
	private SpaceDAO dao;
	
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
		dao = new JDBCSpaceDAO(dataSource);

	}
	
	@Test
	public void get_list_SPACE_from_menu() {
		String sql = "truncate space CASCADE";
		jdbcTemplate.update(sql);

		String createVenueSql = "INSERT INTO space (venue_id, name, open_from, open_to, daily_rate, max_occupancy) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(createVenueSql, 9l, "Park Place", 2, 8, 900, 60);

		List<Space> returnedSpace = dao.getAllSpaces(9l);

		Assert.assertNotNull(returnedSpace);
		Assert.assertEquals(1, returnedSpace.size());

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
	

	
}
