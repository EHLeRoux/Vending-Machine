package VendingMachine;

import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import javax.swing.JScrollPane;

import javax.swing.JTextField;

public class FrontEnd {

	private int frameHeight = 700;
	private int frameWidth = 850;

	FrontEnd() {
		// Creating two frames
		JFrame mainFrame = new JFrame();
		mainFrame.getContentPane().setBackground(Color.LIGHT_GRAY);

		try {
			final Backend database = new Backend();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// Creating Vending Machine
		Vendingmachine machine = new Vendingmachine();

		// Labels
		final JLabel headLabel = new JLabel("Vending Machine");

		final JLabel totalCreditsLabel = new JLabel("Credits");

		// Buttons
		final JButton buyButton = new JButton("Buy");
		final JButton adminButton = new JButton("Administrator");
		final JButton takeButton = new JButton("Take");
		final JButton inputButton = new JButton("Insert");
		final JButton viewItems = new JButton("View Items");
		final JButton changeButton = new JButton("Change");

		// TextFields
		final JTextField moneyTextField = new JTextField();
		final JTextField changeTextField = new JTextField();
		final JTextField itemDispenseTextField = new JTextField();
		final JTextField totalCredits = new JTextField();

		// mainTable displaying the MySQL database

		JScrollPane scrollPane = new JScrollPane();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList<String> listOfItems = new JList<String>(listModel);
		scrollPane.setViewportView(listOfItems);
		scrollPane.setBounds(40, 250, 600, 350);
		mainFrame.add(scrollPane);

		// Object price = table.getValueAt(table.getSelectedRow(), 2);
		// System.out.println("Price of current item" + price);

		// Setting bounds
		// Labels
		headLabel.setBounds(300, 5, 400, 30);

		totalCreditsLabel.setBounds(310, 150, 300, 30);
		// Buttons
		adminButton.setBounds(450, 600, 300, 30);
		inputButton.setBounds(280, 50, 100, 30);
		changeButton.setBounds(280, 100, 100, 30);

		buyButton.setBounds(50, 200, 100, 30);
		takeButton.setBounds(50, 600, 100, 30);
		viewItems.setBounds(700, 200, 100, 30);

		// TextFields
		moneyTextField.setBounds(400, 50, 100, 30);
		changeTextField.setBounds(400, 100, 100, 30);
		itemDispenseTextField.setBounds(200, 600, 200, 30);
		totalCredits.setBounds(400, 150, 100, 30);

		// ScrollPane
		scrollPane.setBounds(150, 190, 550, 400);
		// scrollPane.setViewportView(listOfItems);

		// Adding to mainFrame
		// Labels
		mainFrame.add(headLabel);
		mainFrame.add(changeButton);
		mainFrame.add(totalCreditsLabel);
		// Buttons
		mainFrame.add(adminButton);
		mainFrame.add(inputButton);

		mainFrame.add(buyButton);
		mainFrame.add(takeButton);
		mainFrame.add(viewItems);

		// TextFields
		mainFrame.add(moneyTextField);
		mainFrame.add(changeTextField);
		mainFrame.add(itemDispenseTextField);
		mainFrame.add(totalCredits);

		// table
		mainFrame.add(scrollPane);

		// Setting of fonts and sizes
		headLabel.setFont(new Font("Serif", Font.BOLD, 30));

		// Creating frame
		mainFrame.setSize(frameWidth, frameHeight);
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// When buttons are pressed
		// Changes to the administrator Frame where you can add, delete, search and
		// update the sql database
		adminButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				adminFrontEnd adminFrame = new adminFrontEnd();

				mainFrame.setVisible(false);
			}

		});

		// View the current items in the vending machine, shows the item, price and
		// quantity of item
		viewItems.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					listOfItems.setModel(Backend.viewAllLimited());
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
			}
		});
		// If the user has baught enough and wants to take their change
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				changeTextField.setText("");
				moneyTextField.setText("");
				totalCredits.setText("");

			}
		});

		// Here the user enters their money input amount and accumulates credits
		inputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int moneyInput = Integer.parseInt(moneyTextField.getText());
				machine.addTotalCredits(moneyInput);
				totalCredits.setText(Integer.toString(machine.getTotalCredits()));
			}
		});

		// After selecting the desired item, the user can press the buy button which
		// will dispense the item
		buyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (listOfItems.getSelectedValue() != null) {
					String selected = listOfItems.getSelectedValue();
					String[] stringArray = new String[selected.length()];
					stringArray = selected.split(" - ");

					machine.setSellPrice(Integer.parseInt(stringArray[1]));
					machine.setItem(stringArray[0]);

				}
				// Calculating the change and totalCredits; setting the item to the itemDispense
				int change = machine.getTotalCredits() - machine.getSellPrice();
				if (change < 0) { 
					JOptionPane.showMessageDialog(mainFrame, "Insufficient funds");
				} else {
					machine.minusTotalCredits(machine.getSellPrice());
					totalCredits.setText(Integer.toString(machine.getTotalCredits()));
					String tempString = Integer.toString(change);
					changeTextField.setText(tempString);
					String item = machine.getItem();
					itemDispenseTextField.setText(item);
				}

			}
		});

		// Here the user takes the item and the database is updated by 1 less quantity
		takeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				itemDispenseTextField.setText("");

				if (listOfItems.getSelectedValue() != null) {
					String selected = listOfItems.getSelectedValue();
					String[] stringArray = new String[selected.length()];
					stringArray = selected.split(" - ");

					machine.setQuantity(Integer.parseInt(stringArray[2]) - 1);
					try {
						Backend.updateQty(stringArray[0], machine.getQuantity());
						listOfItems.setModel(Backend.viewAllLimited());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}

			}
		});

	}

	// Main Method
	public static void main(String[] args) throws SQLException {

		FrontEnd frontend = new FrontEnd();

	}
}
