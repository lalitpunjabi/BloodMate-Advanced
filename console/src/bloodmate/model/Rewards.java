package bloodmate.model;

import java.util.Objects;

public class Rewards {
	public enum RewardTier { BRONZE, SILVER, GOLD, PLATINUM }

	private String donorId;
	private int points;
	private RewardTier tier = RewardTier.BRONZE;

	public String getDonorId() { return donorId; }
	public void setDonorId(String donorId) { this.donorId = donorId; }
	public int getPoints() { return points; }
	public void setPoints(int points) { this.points = points; }
	public RewardTier getTier() { return tier; }
	public void setTier(RewardTier tier) { this.tier = tier; }

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Rewards)) return false;
		Rewards rewards = (Rewards) o;
		return Objects.equals(donorId, rewards.donorId);
	}
	@Override public int hashCode() { return Objects.hash(donorId); }
} 