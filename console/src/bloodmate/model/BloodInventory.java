package bloodmate.model;

import java.time.LocalDate;
import java.util.Objects;

public class BloodInventory {
	public enum SourceType { DONATION, CAMP }

	private String id;
	private String bloodGroup;
	private int unitsAvailable;
	private LocalDate lastUpdated;
	private SourceType sourceType = SourceType.DONATION;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getBloodGroup() { return bloodGroup; }
	public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
	public int getUnitsAvailable() { return unitsAvailable; }
	public void setUnitsAvailable(int unitsAvailable) { this.unitsAvailable = unitsAvailable; }
	public LocalDate getLastUpdated() { return lastUpdated; }
	public void setLastUpdated(LocalDate lastUpdated) { this.lastUpdated = lastUpdated; }
	public SourceType getSourceType() { return sourceType; }
	public void setSourceType(SourceType sourceType) { this.sourceType = sourceType; }

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BloodInventory)) return false;
		BloodInventory that = (BloodInventory) o;
		return Objects.equals(id, that.id);
	}
	@Override public int hashCode() { return Objects.hash(id); }
} 