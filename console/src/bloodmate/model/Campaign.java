package bloodmate.model;

import java.time.LocalDate;
import java.util.Objects;

public class Campaign {
	public enum CampaignType { AWARENESS, DONATION_DRIVE }
	public enum CampaignStatus { UPCOMING, ACTIVE, COMPLETED }

	private String id;
	private String title;
	private String description;
	private CampaignType type = CampaignType.DONATION_DRIVE;
	private CampaignStatus status = CampaignStatus.UPCOMING;
	private LocalDate startDate;
	private LocalDate endDate;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public CampaignType getType() { return type; }
	public void setType(CampaignType type) { this.type = type; }
	public CampaignStatus getStatus() { return status; }
	public void setStatus(CampaignStatus status) { this.status = status; }
	public LocalDate getStartDate() { return startDate; }
	public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
	public LocalDate getEndDate() { return endDate; }
	public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Campaign)) return false;
		Campaign campaign = (Campaign) o;
		return Objects.equals(id, campaign.id);
	}
	@Override public int hashCode() { return Objects.hash(id); }
} 