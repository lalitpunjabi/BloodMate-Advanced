package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.Recipient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipientDao {
	private final Connection connection;

	public RecipientDao(Connection connection) {
		this.connection = connection;
	}

	public List<Recipient> findAll() {
		List<Recipient> list = new ArrayList<>();
		String sql = "SELECT id, name, email, phone, blood_group, hospital_name, urgency_level, status FROM recipients ORDER BY name";
		try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				Recipient r = new Recipient();
				r.setId(rs.getString("id"));
				r.setName(rs.getString("name"));
				r.setEmail(rs.getString("email"));
				r.setPhone(rs.getString("phone"));
				r.setBloodGroup(rs.getString("blood_group"));
				r.setHospitalName(rs.getString("hospital_name"));
				r.setUrgencyLevel(rs.getString("urgency_level"));
				r.setStatus(rs.getString("status"));
				list.add(r);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public boolean insert(Recipient recipient) {
		String id = recipient.getId() == null || recipient.getId().isEmpty() ? UUID.randomUUID().toString() : recipient.getId();
		recipient.setId(id);
		String sql = "INSERT INTO recipients (id, name, email, phone, blood_group, hospital_name, urgency_level, status) VALUES (?,?,?,?,?,?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			ps.setString(2, recipient.getName());
			ps.setString(3, recipient.getEmail());
			ps.setString(4, recipient.getPhone());
			ps.setString(5, recipient.getBloodGroup());
			ps.setString(6, recipient.getHospitalName());
			ps.setString(7, recipient.getUrgencyLevel());
			ps.setString(8, recipient.getStatus());
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			return false;
		}
	}
}