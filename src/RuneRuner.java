import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.Timer;


@Script.Manifest(
        name = "RuneRunner",
        description = "Runs runes!"
)
public class RuneRuner extends PollingScript<ClientContext> implements PaintListener {
    private List<Task> taskList = new ArrayList<Task>();
    private boolean init = false;
    private Craft crafter = new Craft(ctx);
    private Move mover = new Move(ctx);
    private Bank banker = new Bank(ctx);

    java.util.Timer startTime = new Timer(false);

    //Image background = getImage("http://i.imgur.com/HA7Avc0.jpg");
    GUI gui = new GUI();

    private Image getImage(String url) {
        try { return ImageIO.read(new URL(url)); }
        catch(IOException e) { return null; }
    }




    @Override
    public void repaint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        //g2.drawImage(background, 10, 200, null);
        g2.drawString(startTime.toString(), 12, 202);
    }

    @Override
    public void start()
    {
        System.out.println("Script Started");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                gui.setSize(640, 480);
                gui.pack();
                gui.setVisible(true);
            }
        });
    }

    @Override
    public void poll() {
        if(init) {
            for (Task task : taskList) {
                if (task.activate()) {
                    task.execute();
                }
            }
        } else if(gui.isDone()) {
            init = true;
            crafter.setArea(gui.getArea());
            mover.setArea(gui.getArea());
            System.out.println(gui.getArea());
            taskList.addAll(Arrays.asList(crafter, mover, banker));
        }
    }

    @Override
    public void stop() {
        System.out.println("Script Stopped!");
    }
}