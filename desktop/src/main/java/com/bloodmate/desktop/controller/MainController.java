package com.bloodmate.desktop.controller;

import com.bloodmate.desktop.db.Db;
import com.bloodmate.desktop.model.Donor;
import com.bloodmate.desktop.repo.DonorDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {
	@FXML private TableView<Donor> donorTable;
	@FXML private TableColumn<Donor, String> colId;
	@FXML private TableColumn<Donor, String> colName;
	@FXML private TableColumn<Donor, String> colEmail;
	@FXML private TableColumn<Donor, String> colBlood;
	@FXML private TextField nameField;
	@FXML private TextField emailField;
	@FXML private TextField phoneField;
	@FXML private TextField bloodField;
	@FXML private Label statusLabel;

	private final ObservableList<Donor> donors = FXCollections.observableArrayList();
	private DonorDao donorDao;

	@FXML public void initialize() {
		colId.setCellValueFactory(cell -> cell.getValue().idProperty());
		colName.setCellValueFactory(cell -> cell.getValue().nameProperty());
		colEmail.setCellValueFactory(cell -> cell.getValue().emailProperty());
		colBlood.setCellValueFactory(cell -> cell.getValue().bloodGroupProperty());
		donorTable.setItems(donors);

		this.donorDao = new DonorDao(Db.get());
		loadDonors();
	}

	@FXML public void onRefresh(ActionEvent e) { loadDonors(); }

	@FXML public void onAddDonor(ActionEvent e) {
		String name = nameField.getText().trim();
		String email = emailField.getText().trim();
		String phone = phoneField.getText().trim();
		String blood = bloodField.getText().trim();
		if (name.isEmpty() || blood.isEmpty()) {
			setStatus("Name and Blood Group required");
			return;
		}
		Donor d = new Donor();
		d.setName(name);
		d.setEmail(email);
		d.setPhone(phone);
		d.setBloodGroup(blood);
		boolean ok = donorDao.insert(d);
		if (ok) {
			setStatus("Donor added");
			clearForm();
			loadDonors();
		} else {
			setStatus("Failed to add donor");
		}
	}

	private void loadDonors() {
		donors.setAll(donorDao.findAll());
	}

	private void clearForm() {
		nameField.clear();
		emailField.clear();
		phoneField.clear();
		bloodField.clear();
	}

	private void setStatus(String s) { statusLabel.setText(s); }
}
