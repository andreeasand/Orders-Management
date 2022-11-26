package presentation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.*;

public class View extends JFrame{
//TODO:...
   JPanel panel_mare= new JPanel(new GridLayout(3,1));

   public View()
   {
       JButton client_op= new JButton("Client Operations");
       JButton product_op=new JButton("Product Operations");
       JButton product_or= new JButton("Product Orders");
       panel_mare.add(client_op);
       panel_mare.add(product_op);
       panel_mare.add(product_or);

       client_op.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                ClientGUI client_frame= new ClientGUI();
           }
       });

       product_op.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               ProductGui product_frame=new ProductGui();
           }
       });

       product_or.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               AddOrderGui order_frame=new AddOrderGui();
           }
       });
       this.add(panel_mare);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setSize(450,450);
       this.setVisible(true);
   }

}
