package presentation;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import dao.AbstractDAO;
import dao.ClientDao;
import dao.ProductDao;
import dao.OrderDao;
import model.Client;
import model.Product;
import model.Orderr;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class AddOrderGui extends JFrame {

    boolean okClient=false;
    boolean okProduct=false;
     private static JTable clientTable= new JTable();
     private static JTable productTable= new JTable();
     private static DefaultTableModel model;
     JPanel panel_mare= new JPanel(new GridLayout(2,2));
     JPanel p1= new JPanel();
     JPanel p2= new JPanel(new GridLayout(3,1));
     JPanel p3= new JPanel();
    private AddProductToOrderGUI addProduct1=new AddProductToOrderGUI();
    private static Product p;
    private static int newId=-1;
    String[] client;

     public AddOrderGui()
     {
         AbstractDAO<Client> clients= new ClientDao();
         AbstractDAO<Product> products= new ProductDao();

         JLabel lblClientsTable = new JLabel("Clients Table");
         p1.add(lblClientsTable);

         ClientTable(clients);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(5, 59, 720, 220);
        p1.add(scrollPane);

        scrollPane.setViewportView(clientTable);

        panel_mare.add(p1);

        JButton addClient= new JButton("Add client");
        JButton addProduct= new JButton("Add product");
        JButton addOrder= new JButton("Add order");

        p2.add(addClient);
        p2.add(addProduct);
        p2.add(addOrder);

        panel_mare.add(p2);

         JLabel lblProductsTable = new JLabel("Products Table");
         p3.add(lblProductsTable);

         productTable(products);

         JScrollPane scrollPane1 = new JScrollPane();
         scrollPane1.setBounds(5, 59, 720, 220);
         p3.add(scrollPane1);

         scrollPane1.setViewportView(productTable);

         panel_mare.add(p3);

         addOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0)
            {
                if(addProduct1.isOkk()== true && okClient==true)
                {
                       setNewId(newId+1);
                       String quantityy=addProduct1.getQuantity_text();
                       int q=Integer.parseInt(quantityy);
                       String id_product=addProduct1.getId_text();
                       String id_client=client[0];

                       int i1=Integer.parseInt(id_product);
                       int i2=Integer.parseInt(id_client);

                      if(addProduct1.verifyQuantity(q,p)==true)
                     {
                         p.setQuantity(p.getQuantity()-q);

                         Orderr order=new Orderr(i2,i1,q);
                         System.out.println(newId);

                         AbstractDAO<Orderr> orderproducts=new OrderDao();

                         orderproducts.insert(order);

                         AbstractDAO<Product> products=new ProductDao();
                         products.update(p);
                         ProductGui.productTable(products);
                         AddOrderGui.productTable(products);

                         //********************   FACTURA      *****************
                         try {
                             FileWriter myWriter = new FileWriter("afisare.txt", true);
                             myWriter.write("ID- ul comenzii :" + newId + '\n');
                             myWriter.write("ID-ul clientului :"+ i2 + '\n');
                             myWriter.write("ID- ul produsului :"+ i1+ '\n');
                             myWriter.write("Cantitatea produsului :"+ q + '\n');
                             myWriter.write("******************************" + '\n');
                             myWriter.close();
                         } catch (IOException e) {
                             System.out.println("An error occurred.");
                             e.printStackTrace();
                         }
                     }
                     else
                    {
                         JOptionPane.showMessageDialog(null,"Quantity too small!", null,JOptionPane.ERROR_MESSAGE);
                    }

                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Select a product and a client!", null,JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        addProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setAddProduct1(new AddProductToOrderGUI());
                String[] s=infoProductTable(productTable);
                setP(addProduct1.infoProduct(s));

                addProduct1.setVisible(false);
                JOptionPane.showMessageDialog(null,"The product has been selected.", null,JOptionPane.ERROR_MESSAGE);
            }
        });

        addClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                client=infoClientTable(clientTable);
                okClient=true;
                JOptionPane.showMessageDialog(null,"The client has been selected.", null,JOptionPane.ERROR_MESSAGE);
            }
        });

         this.add(panel_mare);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setSize(1000,1000);
         this.setVisible(true);

     }


    public void setAddProduct1(AddProductToOrderGUI addProduct1) {
        this.addProduct1 = addProduct1;
    }

    public static Product getP() {
        return p;
    }

    public static void setP(Product p) {
        AddOrderGui.p = p;
    }

    public static void ClientTable(AbstractDAO<Client> cls)
    {
        model=new DefaultTableModel(cls.populateTable(),cls.columnNames());
        clientTable.setModel(model);
        clientTable.setBounds(5,90,400,80);
        clientTable.setVisible(true);
    }

    public static void  productTable(AbstractDAO<Product> p)
    {
        model=new DefaultTableModel(p.populateTable(),p.columnNames());
        productTable.setModel(model);
        productTable.setBounds(5,5,400,80);
        productTable.setVisible(true);
    }

    public static String[] infoProductTable(JTable table)
    {
        String[] product=new String[100];
        int selectedRow=-1;
        selectedRow=table.getSelectedRow();

        if(selectedRow==-1)
        {
            JOptionPane.showMessageDialog(null,"Select a product!", null,JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String selectedId=(String)table.getModel().getValueAt(selectedRow, 0);

        if(selectedId==null)
        {
            JOptionPane.showMessageDialog(null,"Select a product!", null,JOptionPane.ERROR_MESSAGE);
            return null;
        }
        else
        {

            int nrColumn=table.getColumnCount();

            for(int i=0;i<nrColumn;i++)
            {
                product[i]=(String )table.getModel().getValueAt(selectedRow, i);
            }

            return product;
        }
    }

    public static String[] infoClientTable(JTable table)
    {
        String[] client=new String[100];
        int selectedRow=-1;
        selectedRow=table.getSelectedRow();

        if(selectedRow==-1)
        {
            JOptionPane.showMessageDialog(null,"Select a client!", null,JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String selectedId=(String)table.getModel().getValueAt(selectedRow, 0);

        if(selectedId==null)
        {
            JOptionPane.showMessageDialog(null,"Select a client!", null,JOptionPane.ERROR_MESSAGE);
            return null;
        }
        else
        {

            int nrColumn=table.getColumnCount();

            for(int i=0;i<nrColumn;i++)
            {
                client[i]=(String )table.getModel().getValueAt(selectedRow, i);
            }

            return client;
        }
    }


    public void idNewOrder(int i)
    {
        AddProductToOrderGUI.idNewOrder(i);
    }

    public static void setNewId(int newId) {
        AddOrderGui.newId = newId;
    }
}

