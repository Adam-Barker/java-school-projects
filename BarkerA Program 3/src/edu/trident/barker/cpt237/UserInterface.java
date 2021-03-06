//FILE: UserInterface.Java
//PROG:	Adam Barker
//PURP:	Creates a user interface to gather or set information for a cab object
package edu.trident.barker.cpt237;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class UserInterface extends JFrame implements ActionListener,
		ItemListener, MaintenanceListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7019585094449027290L;
	static final int WIDTH = 500;
	static final int HEIGHT = 250;
	static final int PLACEMENTX = 0;
	static final int PLACEMENTY = 251;
	static final String TITLE = "Cab Operator";

	JButton okBtn;
	JRadioButton addGas;
	JRadioButton recordTrip;
	JRadioButton repGasAvailable;
	JRadioButton repMilesAvailable;
	JRadioButton repMilesDriven;
	JRadioButton repGrossEarnings;
	JRadioButton repNetEarnings;
	JRadioButton milesSinceService;
	JRadioButton service;
	JTextField txtField1;
	JTextField txtField2;
	JTextField txtField3;
	private Cab2 cab;

	public UserInterface(Cab2 cab) {
		this.cab = cab;
		initiate();
	}

	/**
	 * Method that creates and maintains the user interface for the cab object.
	 * 
	 * @author Adam Barker @
	 */
	public void initiate() {
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(PLACEMENTX, PLACEMENTY);
		setTitle(TITLE);
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		layout.setHgap(35);
		setLayout(layout);
		ButtonGroup bGroup = new ButtonGroup();
		cab.addMaintenanceListener(this);
		okBtn = new JButton("OK");
		addGas = new JRadioButton("Add Gas");
		recordTrip = new JRadioButton("Record Trip");
		repGasAvailable = new JRadioButton("Gas Available");
		repMilesAvailable = new JRadioButton("Miles Available");
		repMilesDriven = new JRadioButton("Miles Driven");
		repGrossEarnings = new JRadioButton("Gross Earnings");
		repNetEarnings = new JRadioButton("Net Earnings");
		milesSinceService = new JRadioButton("Miles Since Service");
		service = new JRadioButton("Service");
		txtField1 = new JTextField(40);
		txtField2 = new JTextField(40);
		txtField3 = new JTextField(20);

		bGroup.add(repGasAvailable);
		bGroup.add(repMilesAvailable);
		bGroup.add(addGas);
		bGroup.add(milesSinceService);
		bGroup.add(recordTrip);
		bGroup.add(repGrossEarnings);
		bGroup.add(repMilesDriven);
		bGroup.add(repNetEarnings);
		bGroup.add(service);

		add(txtField1);
		add(txtField2);
		add(txtField3);
		add(addGas);
		add(recordTrip);
		add(repGasAvailable);
		add(repMilesAvailable);
		add(service);
		add(okBtn);
		okBtn.addActionListener(this);
		addGas.addItemListener(this);
		recordTrip.addItemListener(this);
		repGasAvailable.addItemListener(this);
		repMilesAvailable.addItemListener(this);
		service.addItemListener(this);
		txtField1.addActionListener(this);
		txtField2.addActionListener(this);
		txtField3.addActionListener(this);
		txtField1.setText(String.valueOf(0));
		txtField2.setText(String.valueOf(0));
		txtField3.setText(String.valueOf(0));
		txtField3.setEnabled(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == okBtn) {
			if (repGasAvailable.isSelected()) {
				txtField1.setText(String.valueOf(0));
				txtField2.setText(String.valueOf(cab.getGasAvailable()));
			} else if (repMilesAvailable.isSelected()) {
				txtField1.setText(String.valueOf(0));
				txtField2.setText(String.valueOf(cab.getMilesAvailable()));
			} else if (repMilesDriven.isSelected()) {
				txtField1.setText(String.valueOf(0));
				txtField2.setText(String.valueOf(cab.getTotalMiles()));
			} else if (milesSinceService.isSelected()) {
				txtField1.setText(String.valueOf(0));
				txtField2.setText(String.valueOf(cab.getServiceMiles()));
			} else if (service.isSelected()) {
				cab.service();
				txtField1.setText(String.valueOf(0));
				txtField2
						.setText("Vehicle has been serviced. Miles since service are reset!");
			} else {
				String input1 = txtField1.getText();
				String input2 = txtField3.getText();
				try {
					double num1 = Double.parseDouble(input1);
					double num2 = Double.parseDouble(input2);
					if (addGas.isSelected()) {
						double gasInput = num1;
						double gasPrice = num2;
						cab.addGas(gasInput, gasPrice);
						txtField2
								.setText(String.valueOf(cab.getGasAvailable()));
					} else if (recordTrip.isSelected()) {
						double miles = num1;
						if (cab.getMilesAvailable() > miles) {
							cab.trip(miles);
							txtField2
									.setText(String.valueOf(cab.getTotalFare()));
						} else
							txtField2.setText("Not Enough Gas!");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(rootPane,
							"That is an invalid number!");
				}
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (addGas.isSelected())
			txtField3.setEnabled(true);
		else
			txtField3.setEnabled(false);
	}

	@Override
	public void maintenanceAlert(int i) {
		if (i == MaintenanceListener.SERVICE_NEEDED) {
			txtField1.setText("Service Needed");
			recordTrip.setEnabled(false);
		} else if (i == MaintenanceListener.SERVICED) {
			txtField1.setText(String.valueOf(0));
			recordTrip.setEnabled(true);
		}
	}
}
