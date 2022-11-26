package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.ClientDao;
import model.Product;
import dao.AbstractDAO;
import dao.ProductDao;

public class ProductGui extends JFrame{
    JPanel panel_mare= new JPanel(new GridLayout(4,1));
    JButton add= new JButton("Add new product");
    JButton edit= new JButton("Edit product");
    JButton delete= new JButton("Delete product");
    JButton view= new JButton("View products");
    private static  JTable table=new JTable();
    private static DefaultTableModel model;

    public ProductGui() {
        panel_mare.add(add);
        panel_mare.add(edit);
        panel_mare.add(delete);
        panel_mare.add(view);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addProduct = new JFrame();
                JPanel panel1 = new JPanel(new GridLayout(3, 2));
                JLabel name_label = new JLabel("Name: ");
                JTextField name_text = new JTextField();
                JLabel price_label = new JLabel("Price: ");
                JTextField price_text = new JTextField();
                JLabel quantity_label = new JLabel("Quantity: ");
                JTextField quantity_text = new JTextField();
                JButton ok = new JButton("OK");
                panel1.add(name_label);
                panel1.add(name_text);
                panel1.add(price_label);
                panel1.add(price_text);
                panel1.add(quantity_label);
                panel1.add(quantity_text);

                addProduct.add(panel1, BorderLayout.CENTER);

                JPanel panel2 = new JPanel();
                panel2.add(ok);

                addProduct.add(panel2, BorderLayout.SOUTH);

                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = name_text.getText();
                        String price = price_text.getText();
                        String quantity = quantity_text.getText();

                        int pricee = Integer.parseInt(price);
                        int quantityy = Integer.parseInt(quantity);

                        model.Product c = new model.Product(name, pricee, quantityy);
                        AbstractDAO<model.Product> products = new ProductDao();
                        products.insert(c);
                        addProduct.setVisible(false);
                    }
                });

                addProduct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                addProduct.setSize(350, 200);
                addProduct.setVisible(true);
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame editProduct = new JFrame();
                JPanel panel1 = new JPanel(new GridLayout(4, 2));
                JLabel id_label = new JLabel("Product's ID: ");
                JTextField id_text = new JTextField();
                JLabel name_label = new JLabel("New Name: ");
                JTextField name_text = new JTextField();
                JLabel price_label = new JLabel("New Price: ");
                JTextField price_text = new JTextField();
                JLabel quantity_label = new JLabel("New Quantity: ");
                JTextField quantity_text = new JTextField();
                JButton ok = new JButton("OK");
                panel1.add(id_label);
                panel1.add(id_text);
                panel1.add(name_label);
                panel1.add(name_text);
                panel1.add(price_label);
                panel1.add(price_text);
                panel1.add(quantity_label);
                panel1.add(quantity_text);

                editProduct.add(panel1, BorderLayout.CENTER);

                JPanel panel2 = new JPanel();
                panel2.add(ok);

                editProduct.add(panel2, BorderLayout.SOUTH);

                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String id = id_text.getText();
                        String name = name_text.getText();
                        String price = price_text.getText();
                        String quantity = quantity_text.getText();

                        int id2 = Integer.parseInt(id);
                        int pricee = Integer.parseInt(price);
                        int quantityy = Integer.parseInt(quantity);

                        System.out.println("GUI id: " + id2 + "\n");

                        Product c = new Product(id2, name, pricee, quantityy);
                        AbstractDAO<model.Product> products = new ProductDao();
                        products.update(c);
                        editProduct.setVisible(false);
                    }
                });

                editProduct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                editProduct.setSize(350, 200);
                editProduct.setVisible(true);

            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame deleteProduct = new JFrame();
                JPanel panel0 = new JPanel();
                JLabel titlu = new JLabel("Delete product");
                titlu.setFont(new Font("Calibri", Font.BOLD, 20));
                titlu.setHorizontalAlignment(SwingConstants.CENTER);
                panel0.add(titlu);

                deleteProduct.add(panel0, BorderLayout.NORTH);

                JPanel panel1 = new JPanel(new GridLayout(1, 2));
                JLabel id_label = new JLabel("Product's ID:");
                JTextField id_text = new JTextField();
                panel1.add(id_label);
                panel1.add(id_text);

                deleteProduct.add(panel1, BorderLayout.CENTER);

                JPanel panel2 = new JPanel();
                JButton ok = new JButton("OK");
                panel2.add(ok);

                deleteProduct.add(panel2, BorderLayout.SOUTH);

                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String id = id_text.getText();
                        int id2 = Integer.parseInt(id);
                        AbstractDAO<model.Product> products = new ProductDao();
                        products.delete(id2);
                        deleteProduct.setVisible(false);
                    }
                });

                deleteProduct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                deleteProduct.setSize(350, 150);
                deleteProduct.setVisible(true);
            }
        });

        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame viewProduct = new JFrame();
                AbstractDAO<Product> products = new ProductDao();
                JPanel contentPane = new JPanel();
                contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                JLabel lblClientsTable = new JLabel("Products Table");
                lblClientsTable.setBounds(5, 5, 496, 73);
                lblClientsTable.setFont(new Font("Arial Black", Font.BOLD, 13));
                contentPane.add(lblClientsTable);

                JLabel label = new JLabel("");
                label.setBounds(506, 5, 501, 73);
                contentPane.add(label);


                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setBounds(5, 59, 728, 224);
                contentPane.add(scrollPane);

                productTable(products);

                scrollPane.setViewportView(table);


                viewProduct.add(contentPane);
                viewProduct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                viewProduct.setSize(750, 350);
                viewProduct.setVisible(true);

            }
        });
        this.add(panel_mare);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 450);
        this.setVisible(true);
    }

    public static void productTable(AbstractDAO<Product> prs)
    {
        model=new DefaultTableModel(prs.populateTable(),prs.columnNames());
        table.setModel(model);
        table.setVisible(true);
    }
}
