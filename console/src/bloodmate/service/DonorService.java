package bloodmate.service;

import bloodmate.model.Donor;
import bloodmate.repo.DonorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DonorService {
	private final DonorRepository repository;

	public DonorService(DonorRepository repository) {
		this.repository = repository;
	}

	public Donor registerDonor(String name, String email, String phone, String bloodGroup, LocalDate dob) {
		Donor donor = new Donor(UUID.randomUUID().toString(), name, email, phone, bloodGroup, dob);
		repository.save(donor);
		return donor;
	}

	public List<Donor> listDonors() {
		return repository.findAll();
	}
} 