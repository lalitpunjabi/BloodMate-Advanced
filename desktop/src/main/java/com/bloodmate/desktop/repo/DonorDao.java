package com.bloodmate.desktop.repo;

import com.bloodmate.desktop.model.Donor;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
			"blood_group VARCHAR(5) NOT NULL," +
			"date_of_birth DATE," +
			"gender VARCHAR(10)," +
			"address TEXT," +
			"city VARCHAR(100)," +
			"state VARCHAR(100)," +
			"postal_code VARCHAR(20)," +
			"country VARCHAR(100) DEFAULT 'India'," +
			"eligibility_status VARCHAR(50) DEFAULT 'ELIGIBLE'," +
			"last_donation_date DATE," +
			"total_donations INT DEFAULT 0," +
			"weight_kg DECIMAL(5,2)," +
			"height_cm INT," +
			"medical_conditions TEXT," +
			"emergency_contact_name VARCHAR(100)," +
			"emergency_contact_phone VARCHAR(50)," +
			"reward_points INT DEFAULT 0," +
			"tier_level VARCHAR(20) DEFAULT 'BRONZE'," +
			"created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
			"updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
			")";
		try (Statement st = connection.createStatement()) {
			st.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException("Failed to create donors table", e);
		}
	}

	public List<Donor> findAll() {
		List<Donor> list = new ArrayList<>();
		String sql = "SELECT * FROM donors ORDER BY name";
		try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				list.add(mapResultSetToDonor(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public Donor findById(String id) {
		String sql = "SELECT * FROM donors WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapResultSetToDonor(rs);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public boolean insert(Donor donor) {
		String id = donor.getId() == null || donor.getId().isEmpty() ? UUID.randomUUID().toString() : donor.getId();
		donor.setId(id);
		
		String sql = "INSERT INTO donors (id, name, email, phone, blood_group, date_of_birth, gender, " +
				"address, city, state, postal_code, country, eligibility_status, last_donation_date, " +
				"total_donations, weight_kg, height_cm, medical_conditions, emergency_contact_name, " +
				"emergency_contact_phone, reward_points, tier_level) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			ps.setString(2, donor.getName());
			ps.setString(3, donor.getEmail());
			ps.setString(4, donor.getPhone());
			ps.setString(5, donor.getBloodGroup());
			ps.setDate(6, donor.getDateOfBirth() != null ? Date.valueOf(donor.getDateOfBirth()) : null);
			ps.setString(7, donor.getGender());
			ps.setString(8, donor.getAddress());
			ps.setString(9, donor.getCity());
			ps.setString(10, donor.getState());
			ps.setString(11, donor.getPostalCode());
			ps.setString(12, donor.getCountry());
			ps.setString(13, donor.getEligibilityStatus());
			ps.setDate(14, donor.getLastDonationDate() != null ? Date.valueOf(donor.getLastDonationDate()) : null);
			ps.setInt(15, donor.getTotalDonations());
			ps.setDouble(16, donor.getWeightKg());
			ps.setInt(17, donor.getHeightCm());
			ps.setString(18, donor.getMedicalConditions());
			ps.setString(19, donor.getEmergencyContactName());
			ps.setString(20, donor.getEmergencyContactPhone());
			ps.setInt(21, donor.getRewardPoints());
			ps.setString(22, donor.getTierLevel());
			
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Donor donor) {
		String sql = "UPDATE donors SET name=?, email=?, phone=?, blood_group=?, date_of_birth=?, gender=?, " +
				"address=?, city=?, state=?, postal_code=?, country=?, eligibility_status=?, last_donation_date=?, " +
				"total_donations=?, weight_kg=?, height_cm=?, medical_conditions=?, emergency_contact_name=?, " +
				"emergency_contact_phone=?, reward_points=?, tier_level=? WHERE id=?";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, donor.getName());
			ps.setString(2, donor.getEmail());
			ps.setString(3, donor.getPhone());
			ps.setString(4, donor.getBloodGroup());
			ps.setDate(5, donor.getDateOfBirth() != null ? Date.valueOf(donor.getDateOfBirth()) : null);
			ps.setString(6, donor.getGender());
			ps.setString(7, donor.getAddress());
			ps.setString(8, donor.getCity());
			ps.setString(9, donor.getState());
			ps.setString(10, donor.getPostalCode());
			ps.setString(11, donor.getCountry());
			ps.setString(12, donor.getEligibilityStatus());
			ps.setDate(13, donor.getLastDonationDate() != null ? Date.valueOf(donor.getLastDonationDate()) : null);
			ps.setInt(14, donor.getTotalDonations());
			ps.setDouble(15, donor.getWeightKg());
			ps.setInt(16, donor.getHeightCm());
			ps.setString(17, donor.getMedicalConditions());
			ps.setString(18, donor.getEmergencyContactName());
			ps.setString(19, donor.getEmergencyContactPhone());
			ps.setInt(20, donor.getRewardPoints());
			ps.setString(21, donor.getTierLevel());
			ps.setString(22, donor.getId());
			
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(String id) {
		String sql = "DELETE FROM donors WHERE id=?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			return false;
		}
	}

	public List<Donor> search(String query, String bloodGroup) {
		List<Donor> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM donors WHERE 1=1");
		
		if (query != null && !query.isEmpty()) {
			sql.append(" AND (name LIKE ? OR email LIKE ? OR phone LIKE ?)");
		}
		
		if (bloodGroup != null && !bloodGroup.isEmpty()) {
			sql.append(" AND blood_group = ?");
		}
		
		sql.append(" ORDER BY name");
		
		try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
			int paramIndex = 1;
			if (query != null && !query.isEmpty()) {
				ps.setString(paramIndex++, "%" + query + "%");
				ps.setString(paramIndex++, "%" + query + "%");
				ps.setString(paramIndex++, "%" + query + "%");
			}
			if (bloodGroup != null && !bloodGroup.isEmpty()) {
				ps.setString(paramIndex++, bloodGroup);
			}
			
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapResultSetToDonor(rs));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	
	private Donor mapResultSetToDonor(ResultSet rs) throws SQLException {
		Donor donor = new Donor();
		donor.setId(rs.getString("id"));
		donor.setName(rs.getString("name"));
		donor.setEmail(rs.getString("email"));
		donor.setPhone(rs.getString("phone"));
		donor.setBloodGroup(rs.getString("blood_group"));
		
		// Handle date fields
		Date dob = rs.getDate("date_of_birth");
		if (dob != null) {
			donor.setDateOfBirth(dob.toLocalDate());
		}
		
		donor.setGender(rs.getString("gender"));
		donor.setAddress(rs.getString("address"));
		donor.setCity(rs.getString("city"));
		donor.setState(rs.getString("state"));
		donor.setPostalCode(rs.getString("postal_code"));
		donor.setCountry(rs.getString("country"));
		donor.setEligibilityStatus(rs.getString("eligibility_status"));
		
		Date lastDonation = rs.getDate("last_donation_date");
		if (lastDonation != null) {
			donor.setLastDonationDate(lastDonation.toLocalDate());
		}
		
		donor.setTotalDonations(rs.getInt("total_donations"));
		donor.setWeightKg(rs.getDouble("weight_kg"));
		donor.setHeightCm(rs.getInt("height_cm"));
		donor.setMedicalConditions(rs.getString("medical_conditions"));
		donor.setEmergencyContactName(rs.getString("emergency_contact_name"));
		donor.setEmergencyContactPhone(rs.getString("emergency_contact_phone"));
		donor.setRewardPoints(rs.getInt("reward_points"));
		donor.setTierLevel(rs.getString("tier_level"));
		
		Timestamp createdAt = rs.getTimestamp("created_at");
		if (createdAt != null) {
			donor.setCreatedAt(createdAt.toLocalDateTime());
		}
		
		Timestamp updatedAt = rs.getTimestamp("updated_at");
		if (updatedAt != null) {
			donor.setUpdatedAt(updatedAt.toLocalDateTime());
		}
		
		return donor;
	}
}
