
package com.example;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author ASUS
 */
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class CustomerForm extends JFrame {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField phoneField;
    private JButton saveButton;
    private JButton editButton;
    private JButton deleteButton;
    private Mavenproject3 parent;

    public CustomerForm(Mavenproject3 parent) {
        this.parent = parent;
        List<Customer> customers = new ArrayList<>();

        setTitle("WK. Cuan | Stok Barang");
        setSize(1080, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panel form pemesanan
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Nama:"));
        nameField = new JTextField(8);
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField(8);
        formPanel.add(phoneField);
        
        saveButton = new JButton("Add");
        formPanel.add(saveButton);
        
        editButton = new JButton("Edit");
        formPanel.add(editButton);
        
        deleteButton = new JButton("Delete");
        formPanel.add(deleteButton);


        tableModel = new DefaultTableModel(new String[]{"Nama", "Phone"}, 0);
        customerTable = new JTable(tableModel);

        loadCustomerData(customers);
        add(new JScrollPane(customerTable), BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
        setVisible(true);

      customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedName = tableModel.getValueAt(selectedRow, 0).toString();
                    String selectedPhone = tableModel.getValueAt(selectedRow, 1).toString();
                    nameField.setText(selectedName);
                    phoneField.setText(selectedPhone);
                }
            }
      });

      saveButton.addActionListener(e -> {
        try {
            String name = nameField.getText();
            String phone = phoneField.getText();
            
            int id = customers.size() + 1;
            Customer customer = new Customer(id, name, phone);
            customers.add(customer);

            tableModel.addRow(new Object[]{customer.getName(), customer.getPhone()});

            nameField.setText("");
            phoneField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Inputan Salah!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    editButton.addActionListener(e -> {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String name = nameField.getText();
                String phone = phoneField.getText();
                
                tableModel.setValueAt(name, selectedRow, 0);
                tableModel.setValueAt(phone, selectedRow, 1);

                nameField.setText("");
                phoneField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Masukkan harga dalam angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih produk yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    });
    
    deleteButton.addActionListener(e -> {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            customers.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            nameField.setText("");
            phoneField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Pilih produk yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    });
    }

    private void loadCustomerData(List<Customer> customerList) {
        for (Customer customer : customerList) {
            tableModel.addRow(new Object[]{
                customer.getName(), customer.getPhone()
            });
        }
    }
    }
