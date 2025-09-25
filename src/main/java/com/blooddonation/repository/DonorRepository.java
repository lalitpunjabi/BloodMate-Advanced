package com.blooddonation.repository;

import com.blooddonation.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    
    Optional<Donor> findByEmail(String email);
    
    Optional<Donor> findByPhone(String phone);
    
    List<Donor> findByBloodGroup(String bloodGroup);
    
    List<Donor> findByBloodGroupAndIsEligibleTrue(String bloodGroup);
    
    List<Donor> findByCityAndState(String city, String state);
    
    List<Donor> findByBloodGroupAndCityAndState(String bloodGroup, String city, String state);
    
    List<Donor> findByIsEligibleTrue();
    
    List<Donor> findByLastDonationDateBefore(LocalDate date);
    
    List<Donor> findByRewardPointsGreaterThanEqualOrderByRewardPointsDesc(Integer minPoints);
    
    @Query("SELECT d FROM Donor d WHERE d.bloodGroup = :bloodGroup AND d.city = :city AND d.isEligible = true")
    List<Donor> findEligibleDonorsByBloodGroupAndCity(@Param("bloodGroup") String bloodGroup, @Param("city") String city);
    
    @Query("SELECT d FROM Donor d WHERE d.bloodGroup = :bloodGroup AND d.state = :state AND d.isEligible = true")
    List<Donor> findEligibleDonorsByBloodGroupAndState(@Param("bloodGroup") String bloodGroup, @Param("state") String state);
    
    @Query("SELECT d FROM Donor d WHERE d.rewardPoints >= :minPoints ORDER BY d.rewardPoints DESC")
    List<Donor> findTopDonorsByPoints(@Param("minPoints") Integer minPoints);
    
    @Query("SELECT COUNT(d) FROM Donor d WHERE d.bloodGroup = :bloodGroup")
    Long countByBloodGroup(@Param("bloodGroup") String bloodGroup);
    
    @Query("SELECT d.city, COUNT(d) FROM Donor d GROUP BY d.city ORDER BY COUNT(d) DESC")
    List<Object[]> getDonorCountByCity();
    
    @Query("SELECT d.bloodGroup, COUNT(d) FROM Donor d GROUP BY d.bloodGroup ORDER BY COUNT(d) DESC")
    List<Object[]> getDonorCountByBloodGroup();
}
