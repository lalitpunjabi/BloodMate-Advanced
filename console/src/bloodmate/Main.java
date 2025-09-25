package bloodmate;

import bloodmate.model.Donor;
import bloodmate.repo.DonorRepository;
import bloodmate.service.DonorService;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final Scanner SCANNER = new Scanner(System.in);
	private static DonorService donorService;

	public static void main(String[] args) {
		DonorRepository donorRepository = new DonorRepository(Paths.get("console").resolve("data"));
		donorService = new DonorService(donorRepository);

		println("BloodMate (Core Java, Console)");
		while (true) {
			printMenu();
			String choice = prompt("Choose an option");
			switch (choice) {
				case "1":
					registerDonor();
					break;
				case "2":
					listDonors();
					break;
				case "3":
					println("Check eligibility - not implemented yet.");
					break;
				case "4":
					println("Record donation - not implemented yet.");
					break;
				case "5":
					println("Find blood for recipient - not implemented yet.");
					break;
				case "0":
					println("Goodbye!");
					return;
				default:
					println("Invalid choice. Please try again.");
			}
		}
	}

	private static void registerDonor() {
		String name = prompt("Name");
		String email = prompt("Email");
		String phone = prompt("Phone");
		String blood = prompt("Blood Group (e.g., A+, O-)");
		String dobStr = prompt("Date of Birth (YYYY-MM-DD)");
		LocalDate dob = null;
		try { dob = LocalDate.parse(dobStr); } catch (Exception e) { println("Invalid date format."); return; }
		Donor donor = donorService.registerDonor(name, email, phone, blood, dob);
		println("Registered donor: " + donor.getId());
	}

	private static void listDonors() {
		List<Donor> donors = donorService.listDonors();
		if (donors.isEmpty()) {
			println("No donors found.");
			return;
		}
		println("ID\tName\tBlood Group\tTotal Donations");
		for (Donor d : donors) {
			println(d.getId() + "\t" + d.getName() + "\t" + d.getBloodGroup() + "\t" + d.getTotalDonations());
		}
	}

	private static void printMenu() {
		println("");
		println("===== Main Menu =====");
		println("1) Register Donor");
		println("2) List Donors");
		println("3) Check Eligibility");
		println("4) Record Donation");
		println("5) Find Blood for Recipient");
		println("0) Exit");
	}

	private static String prompt(String label) {
		System.out.print(label + ": ");
		return SCANNER.nextLine().trim();
	}

	private static void println(String value) {
		System.out.println(value);
	}
} 