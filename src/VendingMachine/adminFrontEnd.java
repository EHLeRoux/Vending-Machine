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

public class adminFrontEnd {

	private int frameWidth = 850;
	private int frameHeight = 700;

	adminFrontEnd() {
		JFrame adminFrame = new JFrame();
		adminFrame.getContentPane().setBackground(Color.LIGHT_GRAY);

		final JLabel headLabel = new JLabel("Administrator");
		headLabel.setFont(new Font("Serif", Font.BOLD, 30));

		// Creating buttons
		JButton frontView = new JButton("Vending Machine");
		JButton search = new JButton("Search");
		JButton delete = new JButton("Delete");
		JButton update = new JButton("Update");
		JButton add = new JButton("Add");
		JButton viewAll = new JButton("View All");
		JButton viewItem = new JButton("View Item");
		JButton clearAll = new JButton("Clear");
		JButton order = new JButton("Order");

		// Creating of table
		JScrollPane scrollPane = new JScrollPane();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList<String> listOfItems = new JList<String>(listModel);
		scrollPane.setViewportView(listOfItems);
		scrollPane.setBounds(150, 190, 550, 400);
		adminFrame.add(scrollPane);

		// Setting Bounds
		frontView.setBounds(450, 600, 300, 30);
		headLabel.setBounds(300, 5, 400, 30);

		// Top input data
		final JTextField idField = new JTextField();
		final JTextField itemField = new JTextField();
		final JTextField priceField = new JTextField();
		final JTextField qtyField = new JTextField();
		final JTextField supplierField = new JTextField();

		final JLabel idLabel = new JLabel("ID");
		final JLabel itemLabel = new JLabel("Item");
		final JLabel priceLabel = new JLabel("price");
		final JLabel qtyLabel = new JLabel("Quantity");
		final JLabel supplierLabel = new JLabel("Supplier");

		// Buttons
		clearAll.setBounds(700, 300, 100, 40);
		viewItem.setBounds(700, 250, 100, 40);
		viewAll.setBounds(700, 200, 100, 40);
		order.setBounds(700, 350, 100, 40);

		add.setBounds(50, 350, 100, 40);
		update.setBounds(50, 300, 100, 40);
		delete.setBounds(50, 250, 100, 40);
		search.setBounds(50, 200, 100, 40);

		// Top Labels
		idLabel.setBounds(30, 50, 100, 40);
		itemLabel.setBounds(400, 50, 100, 40);
		priceLabel.setBounds(400, 100, 100, 40);
		qtyLabel.setBounds(30, 150, 100, 40);
		supplierLabel.setBounds(30, 100, 100, 40);

		// Adding to the adminFrame
		adminFrame.add(frontView);
		adminFrame.add(headLabel);

		// TextFields
		idField.setBounds(100, 50, 220, 40);
		itemField.setBounds(450, 50, 350, 40);
		priceField.setBounds(450, 100, 350, 40);
		qtyField.setBounds(100, 150, 220, 40);
		supplierField.setBounds(100, 100, 220, 40);

		// Buttons
		adminFrame.add(search);
		adminFrame.add(delete);
		adminFrame.add(update);
		adminFrame.add(add);
		adminFrame.add(viewAll);
		adminFrame.add(viewItem);
		adminFrame.add(clearAll);
		// adminFrame.add(order);

		// Labels
		adminFrame.add(idLabel);
		adminFrame.add(itemLabel);
		adminFrame.add(priceLabel);
		adminFrame.add(qtyLabel);
		adminFrame.add(supplierLabel);

		// TextFields
		adminFrame.add(idField);
		adminFrame.add(itemField);
		adminFrame.add(priceField);
		adminFrame.add(qtyField);
		adminFrame.add(supplierField);

		// Creating frame
		adminFrame.setSize(frameWidth, frameHeight);

		adminFrame.setLayout(null);
		adminFrame.setVisible(true);
		adminFrame.setLocationRelativeTo(null);
		adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Sets the administrator view as false, showing the front page again
		frontView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				adminFrame.setVisible(false);
				FrontEnd frontend = new FrontEnd();
			}

		});

		// Views all the items in the database
		viewAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					listOfItems.setModel(Backend.viewAll());
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
			}
		});

		// Views a specific item selected
		viewItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listOfItems.getSelectedValue() != null) {
					String selected = listOfItems.getSelectedValue();
					String[] stringArray = new String[selected.length()];
					stringArray = selected.split(" - ");
					idField.setText(stringArray[0]);
					itemField.setText(stringArray[1]);
					priceField.setText(stringArray[2]);
					qtyField.setText(stringArray[3]);
					supplierField.setText(stringArray[4]);

				}

			}
		});

		// Deletes a selected item from the database
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String delete = listOfItems.getSelectedValue();
				String[] stringArray = new String[delete.length()];
				stringArray = delete.split(" - ");
				try {
					Backend.delete(Integer.parseInt(stringArray[0]), stringArray[1], Integer.parseInt(stringArray[2]),
							Integer.parseInt(stringArray[3]), stringArray[4]);
					listOfItems.setModel(Backend.viewAll());
				} catch (NumberFormatException e1) {

					e1.printStackTrace();
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
			}
		});

		// Clears the current textfields
		clearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idField.setText("");
				itemField.setText("");
				priceField.setText("");
				qtyField.setText("");
				supplierField.setText("");

			}
		});

		// Adding an additional item to the database
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Backend.add(Integer.parseInt(idField.getText()), itemField.getText(),
							Integer.parseInt(priceField.getText()), Integer.parseInt(qtyField.getText()),
							supplierField.getText());
					listOfItems.setModel(Backend.viewAll());
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
			}
		});

		// Updates the selected item
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Backend.update(Integer.parseInt(idField.getText()), itemField.getText(),
							Integer.parseInt(priceField.getText()), Integer.parseInt(qtyField.getText()),
							supplierField.getText(), Integer.parseInt(idField.getText()));
					listOfItems.setModel(Backend.viewAll());

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// Searches for a item, only id can be used
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					listOfItems.setModel(Backend.search(Integer.parseInt(idField.getText())));

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

}
