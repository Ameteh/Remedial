package com.example;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author ASUS
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ProductForm extends JFrame {
    private JTable drinkTable;
    private DefaultTableModel tableModel;
    private JTextField codeField;
    private JTextField nameField;
    private JComboBox<String> categoryField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    public ProductForm() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "P001", "Americano", "Coffee", 18000, 10));
        products.add(new Product(2, "P002", "Pandan Latte", "Coffee", 15000, 8));
        
        setTitle("WK. Cuan | Stok Barang");
        setSize(1080, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panel form pemesanan
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Kode Barang"));
        codeField = new JTextField(3);
        formPanel.add(codeField);
        
        formPanel.add(new JLabel("Nama Barang:"));
        nameField = new JTextField(8);
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Kategori:"));
        categoryField = new JComboBox<>(new String[]{"Coffee", "Dairy", "Juice", "Soda", "Tea"});
        formPanel.add(categoryField);
        
        formPanel.add(new JLabel("Harga Jual:"));
        priceField = new JTextField(8);
        formPanel.add(priceField);
        
        formPanel.add(new JLabel("Stok Tersedia:"));
        stockField = new JTextField(3);
        formPanel.add(stockField);
        
        addButton = new JButton("Add");
        formPanel.add(addButton);
        
        editButton = new JButton("Edit");
        formPanel.add(editButton);
        
        deleteButton = new JButton("Delete");
        formPanel.add(deleteButton);

        

        tableModel = new DefaultTableModel(new String[]{"Kode", "Nama", "Kategori", "Harga Jual", "Stok"}, 0);
        drinkTable = new JTable(tableModel);

        loadProductData(products);
        add(new JScrollPane(drinkTable), BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
        setVisible(true);

      drinkTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = drinkTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedCode = tableModel.getValueAt(selectedRow, 0).toString();
                    String selectedName = tableModel.getValueAt(selectedRow, 1).toString();
                    String selectedCategory = tableModel.getValueAt(selectedRow, 2).toString();
                    String selectedPrice = tableModel.getValueAt(selectedRow, 3).toString();
                    String selectedStock = tableModel.getValueAt(selectedRow, 4).toString();
                    codeField.setText(selectedCode);
                    nameField.setText(selectedName);
                    categoryField.setSelectedItem(selectedCategory);
                    priceField.setText(selectedPrice);
                    stockField.setText(selectedStock);
                }
            }
      });

      addButton.addActionListener(e -> {
        try {
            String code = codeField.getText();
            String name = nameField.getText();
            String category = categoryField.getSelectedItem().toString();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            int id = products.size() + 1;
            Product product = new Product(id, code, name, category, price, stock);
            products.add(product);

            tableModel.addRow(new Object[]{product.getCode(), product.getName(), product.getCategory(), product.getPrice(), product.getStock()});

            codeField.setText("");
            nameField.setText("");
            priceField.setText("");
            stockField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Inputan Salah!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    editButton.addActionListener(e -> {
        int selectedRow = drinkTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String code = codeField.getText();
                String name = nameField.getText();
                String category = categoryField.getSelectedItem().toString();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());

                tableModel.setValueAt(code, selectedRow, 0);
                tableModel.setValueAt(name, selectedRow, 1);
                tableModel.setValueAt(category, selectedRow, 2);
                tableModel.setValueAt(price, selectedRow, 3);
                tableModel.setValueAt(stock, selectedRow, 4);

                codeField.setText("");
                nameField.setText("");
                priceField.setText("");
                stockField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Masukkan harga dalam angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih produk yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    });
    
    deleteButton.addActionListener(e -> {
        int selectedRow = drinkTable.getSelectedRow();
        if (selectedRow != -1) {
            products.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            codeField.setText("");
            nameField.setText("");
            priceField.setText("");
            stockField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Pilih produk yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    });
    }

    private void loadProductData(List<Product> productList) {
        for (Product product : productList) {
            tableModel.addRow(new Object[]{
                product.getCode(), product.getName(), product.getCategory(), product.getPrice(), product.getStock()
            });
        }
    }
    }
