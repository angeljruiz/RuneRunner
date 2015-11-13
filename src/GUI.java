import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Angel on 11/13/2015.
 */

public class GUI extends JFrame {
    private String[] areaOptions = { "Air Runes - Fally", "Earth Runes - Varrock", "Fire Runes - Al Kharid", "Body Runes - Edge" };
    private JComboBox<String> comboBox1 = new JComboBox<>(areaOptions);
    private JButton button1 = new JButton("Start");
    private int selectedArea = -1;
    private boolean done = false;

    public boolean isDone() { return done; }

    public int getArea()
    {
        return selectedArea;
    }

    ActionListener areaChanger = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedArea = comboBox1.getSelectedIndex();
        }
    };
    ActionListener start =  new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            done = true;
            dispose();
        }
    };


    public GUI() { //lol..
        super("Setup");
        setLayout(new GridLayout());
        setPreferredSize(new Dimension(320, 240));
        comboBox1.addActionListener(areaChanger);
        button1.addActionListener(start);
        add(comboBox1, BorderLayout.CENTER);
        add(button1);
    }
}
