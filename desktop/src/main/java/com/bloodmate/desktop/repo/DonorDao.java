package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.Donor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DonorDao {
	private final Connection connection;

	public DonorDao(Connection connection) {
		this.connection = connection;
		ensureTable();
	}

	private void ensureTable() {
		String sql = "CREATE TABLE IF NOT EXISTS donors (" +
			"id VARCHAR(64) PRIMARY KEY," +
			"name VARCHAR(100) NOT NULL," +
			"email VARCHAR(150)," +
			"phone VARCHAR(50)," +
			"blood_group VARCHAR(5) NOT NULL" +
			")";
		try (Statement st = connection.createStatement()) {
			st.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException("Failed to create donors table", e);
		}
	}

	public List<Donor> findAll() {
		List<Donor> list = new ArrayList<>();
		String sql = "SELECT id, name, email, phone, blood_group FROM donors ORDER BY name";
		try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				Donor d = new Donor();
				d.setId(rs.getString("id"));
				d.setName(rs.getString("name"));
				d.setEmail(rs.getString("email"));
				d.setPhone(rs.getString("phone"));
				d.setBloodGroup(rs.getString("blood_group"));
				list.add(d);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public boolean insert(Donor donor) {
		String id = donor.getId() == null || donor.getId().isEmpty() ? UUID.randomUUID().toString() : donor.getId();
		donor.setId(id);
		String sql = "INSERT INTO donors (id, name, email, phone, blood_group) VALUES (?,?,?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			ps.setString(2, donor.getName());
			ps.setString(3, donor.getEmail());
			ps.setString(4, donor.getPhone());
			ps.setString(5, donor.getBloodGroup());
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			return false;
		}
	}
}
