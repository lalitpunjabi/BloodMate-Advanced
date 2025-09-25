package bloodmate.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Recipient {
	public enum RequestPriority { LOW, MEDIUM, HIGH }
	public enum RequestStatus { OPEN, FULFILLED, CANCELLED }

	private String id;
	private String name;
	private String requiredBloodGroup;
	private int unitsRequired; // number of units
	private RequestPriority priority = RequestPriority.MEDIUM;
	private RequestStatus status = RequestStatus.OPEN;
	private LocalDateTime requestedAt = LocalDateTime.now();

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getRequiredBloodGroup() { return requiredBloodGroup; }
	public void setRequiredBloodGroup(String requiredBloodGroup) { this.requiredBloodGroup = requiredBloodGroup; }
	public int getUnitsRequired() { return unitsRequired; }
	public void setUnitsRequired(int unitsRequired) { this.unitsRequired = unitsRequired; }
	public RequestPriority getPriority() { return priority; }
	public void setPriority(RequestPriority priority) { this.priority = priority; }
	public RequestStatus getStatus() { return status; }
	public void setStatus(RequestStatus status) { this.status = status; }
	public LocalDateTime getRequestedAt() { return requestedAt; }
	public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Recipient)) return false;
		Recipient that = (Recipient) o;
		return Objects.equals(id, that.id);
	}
	@Override public int hashCode() { return Objects.hash(id); }
} 