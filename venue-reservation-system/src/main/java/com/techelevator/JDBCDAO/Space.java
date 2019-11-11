package com.techelevator.JDBCDAO;

import java.math.BigDecimal;

public class Space {

	private long id;
	private long venue_id;
	private String name;
	private boolean is_accessible;
	private int open_from;
	private int open_to;
	private BigDecimal daily_rate;
	private int max_occupancy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVenue_id() {
		return venue_id;
	}

	public void setVenue_id(long venue_id) {
		this.venue_id = venue_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isIs_accessible() {
		return is_accessible;
	}

	public void setIs_accessible(boolean is_accessible) {
		this.is_accessible = is_accessible;
	}

	public int getOpen_from() {
		return open_from;
	}

	public void setOpen_from(int open_from) {
		this.open_from = open_from;
	}

	public int getOpen_to() {
		return open_to;
	}

	public void setOpen_to(int open_to) {
		this.open_to = open_to;
	}

	public BigDecimal getDaily_rate() {
		return daily_rate;
	}

	public void setDaily_rate(BigDecimal daily_rate) {
		this.daily_rate = daily_rate;
	}

	public int getMax_occupancy() {
		return max_occupancy;
	}

	public void setMax_occupancy(int max_occupancy) {
		this.max_occupancy = max_occupancy;
	}

	@Override
	public String toString() {
		return "Space [id=" + id + ", venue_id=" + venue_id + ", name=" + name + ", is_accessible=" + is_accessible
				+ ", open_from=" + open_from + ", open_to=" + open_to + ", daily_rate=" + daily_rate
				+ ", max_occupancy=" + max_occupancy + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((daily_rate == null) ? 0 : daily_rate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (is_accessible ? 1231 : 1237);
		result = prime * result + max_occupancy;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + open_from;
		result = prime * result + open_to;
		result = prime * result + (int) (venue_id ^ (venue_id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Space other = (Space) obj;
		if (daily_rate == null) {
			if (other.daily_rate != null)
				return false;
		} else if (!daily_rate.equals(other.daily_rate))
			return false;
		if (id != other.id)
			return false;
		if (is_accessible != other.is_accessible)
			return false;
		if (max_occupancy != other.max_occupancy)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (open_from != other.open_from)
			return false;
		if (open_to != other.open_to)
			return false;
		if (venue_id != other.venue_id)
			return false;
		return true;
	}



}
