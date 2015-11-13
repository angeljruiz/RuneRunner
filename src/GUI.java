import javax.swing.*;
import java.awt.*;
/*
 * Created by JFormDesigner on Thu Nov 12 17:36:12 GMT-09:00 2015
 */



/**
 * @author unknown
 */
public class GUI extends JFrame {
    public GUI() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Angel Ruiz-Bates
        frame1 = new JFrame();
        button1 = new JButton();
        toggleButton1 = new JToggleButton();
        label1 = new JLabel();

        //======== frame1 ========
        {
            Container frame1ContentPane = frame1.getContentPane();
            frame1ContentPane.setLayout(new BoxLayout(frame1ContentPane, BoxLayout.X_AXIS));

            //---- button1 ----
            button1.setText("text");
            frame1ContentPane.add(button1);

            //---- toggleButton1 ----
            toggleButton1.setText("text");
            frame1ContentPane.add(toggleButton1);

            //---- label1 ----
            label1.setText("Noobs");
            frame1ContentPane.add(label1);
            frame1.pack();
            frame1.setLocationRelativeTo(frame1.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Angel Ruiz-Bates
    private JFrame frame1;
    private JButton button1;
    private JToggleButton toggleButton1;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
