import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    private long startTime;
    private int startExp;

    private Image background;
    GUI gui = new GUI();

    public static BufferedImage loadAndSaveImage(PollingScript script, String url, String imageName) { //Thanks Robert G
        final File file = new File(script.getStorageDirectory(), imageName);
        try {
            if (!file.exists()) {
                final BufferedImage img = script.downloadImage(url);
                if (img != null && img.getWidth() > 1 && img.getHeight() > 1) {
                    ImageIO.write((BufferedImage)img, "png", file);
                    return img;
                }
            } else {
                return ImageIO.read(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatTime (final long time) { //Thanks Robert G
        final int sec = (int) (time / 1000), h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
        return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
    }

    public int perHour(int gained) { return ((int) ((gained) * 3600000D / (System.currentTimeMillis() - startTime)));}



    @Override
    public void repaint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        g2.drawImage(background, 10, 200, null);
        g2.drawString("Run time: " + formatTime(this.getRuntime()), 12, 212);
        g2.drawString("Exp gained: " + (ctx.skills.experience(20) - startExp) + "/" + perHour(ctx.skills.experience(20) - startExp), 12, 227);
    }

    @Override
    public void start()
    {
            background = loadAndSaveImage(this, "http://i.imgur.com/HA7Avc0.jpg", "background");
            startTime = System.currentTimeMillis();
            startExp = ctx.skills.experience(20);
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