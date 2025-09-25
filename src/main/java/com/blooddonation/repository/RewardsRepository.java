package com.blooddonation.repository;

import com.blooddonation.entity.Rewards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardsRepository extends JpaRepository<Rewards, Long> {
    
    Optional<Rewards> findByDonorId(Long donorId);
    
    List<Rewards> findByDonorIdOrderByPointsDesc(Long donorId);
    
    @Query("SELECT r FROM Rewards r ORDER BY r.points DESC")
    List<Rewards> findAllOrderByPointsDesc();
    
    @Query("SELECT r FROM Rewards r WHERE r.points >= :minPoints ORDER BY r.points DESC")
    List<Rewards> findByMinPointsOrderByPointsDesc(Integer minPoints);
    
    @Query("SELECT r FROM Rewards r WHERE r.tier = :tier ORDER BY r.points DESC")
    List<Rewards> findByTierOrderByPointsDesc(Rewards.RewardTier tier);
    
    @Query("SELECT r.donor.id, r.points, r.tier FROM Rewards r ORDER BY r.points DESC")
    List<Object[]> getLeaderboardData();
}
