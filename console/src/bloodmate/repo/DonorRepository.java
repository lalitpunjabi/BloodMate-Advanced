package bloodmate.repo;

import bloodmate.model.Donor;
import bloodmate.storage.CsvUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class DonorRepository {
	private final Path filePath;

	public DonorRepository(Path dataDir) {
		this.filePath = dataDir.resolve("donors.csv");
	}

	public List<Donor> findAll() {
		try {
			List<String[]> rows = CsvUtil.readAll(filePath);
			List<Donor> donors = new ArrayList<>();
			boolean isHeader = true;
			for (String[] row : rows) {
				if (isHeader) { isHeader = false; continue; }
				donors.add(fromRow(row));
			}
			return donors;
		} catch (IOException e) {
			return new ArrayList<>();
		}
	}

	public Optional<Donor> findById(String id) {
		return findAll().stream().filter(d -> Objects.equals(d.getId(), id)).findFirst();
	}

	public void save(Donor donor) {
		List<Donor> donors = findAll();
		boolean updated = false;
		for (int i = 0; i < donors.size(); i++) {
			if (Objects.equals(donors.get(i).getId(), donor.getId())) {
				donors.set(i, donor);
				updated = true;
				break;
			}
		}
		if (!updated) donors.add(donor);
		writeAll(donors);
	}

	private void writeAll(List<Donor> donors) {
		List<String[]> rows = new ArrayList<>();
		rows.add(header());
		for (Donor d : donors) rows.add(toRow(d));
		try { CsvUtil.writeAll(filePath, rows); } catch (IOException ignored) {}
	}

	private String[] header() {
		return new String[]{"id","name","email","phone","bloodGroup","dateOfBirth","lastDonationDate","totalDonations"};
	}
	private String[] toRow(Donor d) {
		return new String[]{
			d.getId(),
			d.getName(),
			d.getEmail(),
			d.getPhone(),
			d.getBloodGroup(),
			formatDate(d.getDateOfBirth()),
			formatDate(d.getLastDonationDate()),
			String.valueOf(d.getTotalDonations())
		};
	}
	private Donor fromRow(String[] r) {
		Donor d = new Donor();
		d.setId(r.length>0?r[0]:null);
		d.setName(r.length>1?r[1]:null);
		d.setEmail(r.length>2?r[2]:null);
		d.setPhone(r.length>3?r[3]:null);
		d.setBloodGroup(r.length>4?r[4]:null);
		d.setDateOfBirth(parseDate(r.length>5?r[5]:null));
		d.setLastDonationDate(parseDate(r.length>6?r[6]:null));
		try { d.setTotalDonations(Integer.parseInt(r.length>7?r[7]:"0")); } catch (Exception e) { d.setTotalDonations(0);} 
		return d;
	}

	private String formatDate(LocalDate date) { return date == null ? "" : date.toString(); }
	private LocalDate parseDate(String value) { return (value == null || value.isEmpty()) ? null : LocalDate.parse(value); }
} 