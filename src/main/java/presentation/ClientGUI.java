package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.ProductDao;
import model.Client;
import dao.AbstractDAO;
import dao.ClientDao;


public class ClientGUI extends JFrame {
       JPanel panel_mare= new JPanel(new GridLayout(4,1));
       JButton add= new JButton("Add new client");
       JButton edit= new JButton("Edit client");
       JButton delete= new JButton("Delete client");
       JButton view= new JButton("View clients");
      private static  JTable table=new JTable();
       private static DefaultTableModel model;

       public ClientGUI()
       {
           panel_mare.add(add);
           panel_mare.add(edit);
           panel_mare.add(delete);
           panel_mare.add(view);

           add.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   JFrame addClient=new JFrame();
                   JPanel panel1= new JPanel(new GridLayout(4,2));
                   JLabel name_label= new JLabel("Name: ");
                   JTextField name_text=new JTextField();
                   JLabel email_label= new JLabel("Email: ");
                   JTextField email_text=new JTextField();
                   JLabel address_label= new JLabel("Address: ");
                   JTextField address_text=new JTextField();
                   JLabel phone_label= new JLabel("Phone: ");
                   JTextField phone_text= new JTextField();
                   JButton ok= new JButton("OK");
                   panel1.add(name_label);
                   panel1.add(name_text);
                   panel1.add(email_label);
                   panel1.add(email_text);
                   panel1.add(address_label);
                   panel1.add(address_text);
                   panel1.add(phone_label);
                   panel1.add(phone_text);

                   addClient.add(panel1,BorderLayout.CENTER);

                   JPanel panel2= new JPanel();
                   panel2.add(ok);

                   addClient.add(panel2, BorderLayout.SOUTH);

                   ok.addActionListener(new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                           String name= name_text.getText();
                           String email= email_text.getText();
                           String address= address_text.getText();
                           String phone= phone_text.getText();

                           Client c=new Client(name,email,address,phone);
                           AbstractDAO<model.Client> clients = new ClientDao();
                           clients.insert(c);
                           addClient.setVisible(false);
                       }
                   });

                   addClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                   addClient.setSize(350,200);
                   addClient.setVisible(true);
               }
           });

           edit.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   JFrame editClient= new JFrame();
                   JPanel panel1= new JPanel(new GridLayout(5,2));
                   JLabel id_label= new JLabel("Client's ID: ");
                   JTextField id_text= new JTextField();
                   JLabel name_label= new JLabel("New Name: ");
                   JTextField name_text=new JTextField();
                   JLabel email_label= new JLabel("New Email: ");
                   JTextField email_text=new JTextField();
                   JLabel address_label= new JLabel("New Address: ");
                   JTextField address_text=new JTextField();
                   JLabel phone_label= new JLabel("New Phone: ");
                   JTextField phone_text= new JTextField();
                   JButton ok= new JButton("OK");
                   panel1.add(id_label);
                   panel1.add(id_text);
                   panel1.add(name_label);
                   panel1.add(name_text);
                   panel1.add(email_label);
                   panel1.add(email_text);
                   panel1.add(address_label);
                   panel1.add(address_text);
                   panel1.add(phone_label);
                   panel1.add(phone_text);

                   editClient.add(panel1,BorderLayout.CENTER);

                   JPanel panel2= new JPanel();
                   panel2.add(ok);

                   editClient.add(panel2, BorderLayout.SOUTH);

                   ok.addActionListener(new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                           String id=id_text.getText();
                           String name= name_text.getText();
                           String email= email_text.getText();
                           String address= address_text.getText();
                           String phone= phone_text.getText();
                           int id2=Integer.parseInt(id);

                           Client c=new Client(id2,name,email,address,phone);
                           AbstractDAO<Client> clients=new ClientDao();
                           clients.update(c);
                           editClient.setVisible(false);
                       }
                   });

                   editClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                   editClient.setSize(350,200);
                   editClient.setVisible(true);

               }
           });

           delete.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   JFrame deleteClient= new JFrame();
                   JPanel panel0 =new JPanel();
                   JLabel titlu= new JLabel("Delete client");
                   titlu.setFont(new Font("Calibri", Font.BOLD, 20));
                   titlu.setHorizontalAlignment(SwingConstants.CENTER);
                   panel0.add(titlu);

                   deleteClient.add(panel0,BorderLayout.NORTH);

                   JPanel panel1=new JPanel(new GridLayout(1,2));
                   JLabel id_label= new JLabel("Client's ID:");
                   JTextField id_text= new JTextField();
                   panel1.add(id_label);
                   panel1.add(id_text);

                   deleteClient.add(panel1, BorderLayout.CENTER);

                   JPanel panel2= new JPanel();
                   JButton ok= new JButton("OK");
                   panel2.add(ok);

                   deleteClient.add(panel2, BorderLayout.SOUTH);

                   ok.addActionListener(new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                           String id= id_text.getText();
                           int id2=Integer.parseInt(id);
                           AbstractDAO<Client> clients=new ClientDao();
                           clients.delete(id2);
                           deleteClient.setVisible(false);
                       }
                   });

                   deleteClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                   deleteClient.setSize(350,150);
                   deleteClient.setVisible(true);
               }
           });

           view.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {

                   JFrame viewClient= new JFrame();
                   AbstractDAO<Client> clients = new ClientDao();
                   JPanel contentPane = new JPanel();
                   contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                   setContentPane(contentPane);
                   contentPane.setLayout(null);

                   JLabel lblClientsTable = new JLabel("Clients Table");
                   lblClientsTable.setBounds(5, 5, 496, 73);
                   lblClientsTable.setFont(new Font("Arial Black", Font.BOLD, 13));
                   contentPane.add(lblClientsTable);

                   JLabel label = new JLabel("");
                   label.setBounds(506, 5, 501, 73);
                   contentPane.add(label);

                   JScrollPane scrollPane = new JScrollPane();
                   scrollPane.setBounds(5, 59, 728, 224);
                   contentPane.add(scrollPane);

                   ClientTable(clients);


                   scrollPane.setViewportView(table);


                   JLabel label_1 = new JLabel("");
                   label_1.setBounds(5, 161, 496, 73);
                   contentPane.add(label_1);

                   JLabel label_2 = new JLabel("");
                   label_2.setBounds(5, 239, 496, 73);
                   contentPane.add(label_2);

                   viewClient.add(contentPane);
                   viewClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                   viewClient.setSize(750,350);
                   viewClient.setVisible(true);

               }
           });
           this.add(panel_mare);
           this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           this.setSize(450,450);
           this.setVisible(true);
       }

    public static void ClientTable(AbstractDAO<Client> cls)
    {
        model=new DefaultTableModel(cls.populateTable(),cls.columnNames());
        table.setModel(model);
        table.setVisible(true);
    }
}
