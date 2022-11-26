package presentation;

import java.awt.*;

import javax.swing.*;

import model.Product;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddProductToOrderGUI extends JFrame {

    JFrame frame=new JFrame();
    private JPanel contentPane;
    private JTextField id_text;
    private JTextField name_text;
    private JTextField price_text;
    private JTextField quantity_text;
    private static int newId=-1;
    private static Product p;
    private boolean okk=false;

    public AddProductToOrderGUI() {
        newId=-1;
        JPanel panel0 =new JPanel();
        JLabel titlu= new JLabel("Add product");
        titlu.setFont(new Font("Calibri", Font.BOLD, 20));
        titlu.setHorizontalAlignment(SwingConstants.CENTER);
        panel0.add(titlu);
        frame.add(panel0,BorderLayout.NORTH);

        contentPane=new JPanel(new GridLayout(4,2));
        JLabel id_label= new JLabel("ID:");
        id_text=new JTextField();

        JLabel name_label=new JLabel("Name: ");
        name_text= new JTextField();

        JLabel price_label=new JLabel("Price: ");
        price_text= new JTextField();

        JLabel quantity_label= new JLabel("Quantity: ");
        quantity_text=new JTextField();

        contentPane.add(id_label);
        contentPane.add(id_text);
        contentPane.add(name_label);
        contentPane.add(name_text);
        contentPane.add(price_label);
        contentPane.add(price_text);
        contentPane.add(quantity_label);
        contentPane.add(quantity_text);

        frame.add(contentPane,BorderLayout.CENTER);

        JPanel panel2=new JPanel();
        JButton btnOk = new JButton("OK");
        panel2.add(btnOk);

       frame.add(panel2,BorderLayout.SOUTH);


        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                  setOkk(true);
                  frame.setVisible(false);
            }
        });

        frame.setSize(250,250);
        frame.setVisible(true);
    }

    public String getId_text() {
        return id_text.getText();
    }

    public String getName_text() {
        return name_text.getText();
    }

    public String getPrice_text() {
        return price_text.getText();
    }

    public String getQuantity_text() {
        return quantity_text.getText();
    }

    public static int getNewId() {
        return newId;
    }

    public static Product getP() {
        return p;
    }

    public  Product infoProduct(String[] s)
    {
        id_text.setText(s[0]);
        name_text.setText(s[1]);
        price_text.setText(s[2]);
        quantity_text.setText("");
        int quantity=Integer.parseInt(s[3]);
        int id=Integer.parseInt(id_text.getText());
        int pricee= Integer.parseInt(price_text.getText());
        p=new Product(id,s[1],pricee,quantity);

        return p;
    }

    public boolean isOkk() {
        return okk;
    }

    public void setOkk(boolean okk) {
        this.okk = okk;
    }

    public static void idNewOrder(int i)
    {
        newId=i;
    }

    public static boolean verifyQuantity(int q,Product p2)
    {
        System.out.println(p2.getQuantity());
        if(q<=p2.getQuantity())
        {
            return true;
        }
        else
            return false;
    }

}


