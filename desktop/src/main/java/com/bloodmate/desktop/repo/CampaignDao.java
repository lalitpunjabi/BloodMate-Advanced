package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.Campaign;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CampaignDao {
	private final Connection connection;

	public CampaignDao(Connection connection) {
		this.connection = connection;
	}

	public List<Campaign> findAll() {
		List<Campaign> list = new ArrayList<>();
		String sql = "SELECT id, title, location, status FROM campaigns ORDER BY title";
		try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				Campaign c = new Campaign();
				c.setId(rs.getString("id"));
				c.setTitle(rs.getString("title"));
				c.setLocation(rs.getString("location"));
				c.setStatus(rs.getString("status"));
				list.add(c);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public boolean insert(Campaign campaign) {
		String id = campaign.getId() == null || campaign.getId().isEmpty() ? UUID.randomUUID().toString() : campaign.getId();
		campaign.setId(id);
		String sql = "INSERT INTO campaigns (id, title, location, status) VALUES (?,?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			ps.setString(2, campaign.getTitle());
			ps.setString(3, campaign.getLocation());
			ps.setString(4, campaign.getStatus());
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			return false;
		}
	}
}