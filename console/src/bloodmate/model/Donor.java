package bloodmate.model;

import java.time.LocalDate;
import java.util.Objects;

public class Donor {
	private String id;
	private String name;
	private String email;
	private String phone;
	private String bloodGroup; // e.g., A+, O-
	private LocalDate dateOfBirth;
	private LocalDate lastDonationDate; // nullable
	private int totalDonations;

	public Donor() {}

	public Donor(String id, String name, String email, String phone, String bloodGroup, LocalDate dateOfBirth) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.bloodGroup = bloodGroup;
		this.dateOfBirth = dateOfBirth;
	}

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	public String getBloodGroup() { return bloodGroup; }
	public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
	public LocalDate getDateOfBirth() { return dateOfBirth; }
	public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
	public LocalDate getLastDonationDate() { return lastDonationDate; }
	public void setLastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; }
	public int getTotalDonations() { return totalDonations; }
	public void setTotalDonations(int totalDonations) { this.totalDonations = totalDonations; }

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Donor)) return false;
		Donor donor = (Donor) o;
		return Objects.equals(id, donor.id);
	}
	@Override public int hashCode() { return Objects.hash(id); }
	@Override public String toString() {
		return "Donor{" +
			"id='" + id + '\'' +
			", name='" + name + '\'' +
			", bloodGroup='" + bloodGroup + '\'' +
			", totalDonations=" + totalDonations +
			'}';
	}
} 