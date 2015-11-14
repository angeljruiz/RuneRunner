import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Script.Manifest(
        name = "RuneRunner",
        description = "Runs runes!"
)
public class RuneRuner extends PollingScript<ClientContext> implements PaintListener, MessageListener {
    private List<Task> taskList = new ArrayList<Task>();
    private boolean init = false;
    private Craft crafter = new Craft(ctx);
    private Move mover = new Move(ctx);
    private Bank banker = new Bank(ctx);
    private int runesCrafted = 0;

    private int startExp;

    GUI gui = new GUI();

    public static BufferedImage loadAndSaveImage(PollingScript script, String url, String imageName) { //Thanks Robert G
        final File file = new File(script.getStorageDirectory(), imageName);
        try {
            if (!file.exists()) {
                final BufferedImage img = script.downloadImage(url);
                if (img != null && img.getWidth() > 1 && img.getHeight() > 1) {
                    ImageIO.write(img, "png", file);
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

    public int perHour(int gained) { return ((int) ((gained) * 3600000D / getRuntime()));}


    @Override
    public void messaged(MessageEvent e){
        if(e.text().contains("You bind"))
        {
            runesCrafted += ctx.inventory.select().id(Resources.runeIDs).poll().stackSize();
        }
    }

    @Override
    public void repaint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        Color gray = new Color(128, 128, 128, 190);
        Color black = new Color(255, 255, 255, 100);
        int xpGained = (ctx.skills.experience(20) - startExp);
        long xpPerHR = perHour(xpGained);
        int xpTillLevel = ctx.skills.experienceAt(ctx.skills.level(20) + 1) - ctx.skills.experience(20);

        g2.setColor(gray);
        g2.fillRect(10, 200, 160, 85);
        g2.setColor(black);
        g2.drawRect(10, 200, 160, 85);
        g2.drawRect(10, 200, 160, 18);
        g2.setColor(Color.WHITE);
        g2.drawString("RuneRunner", 15, 215);
        g2.drawString("Runtime: " + formatTime(getRuntime()), 15, 235);
        g2.drawString("Exp: "+ xpGained + " ("+ NumberFormat.getIntegerInstance().format(xpPerHR) + "/hr)", 15, 250);
        g2.drawString("Runes: " + runesCrafted + " (" + NumberFormat.getIntegerInstance().format(perHour(runesCrafted)) + "/hr)", 15, 265);
        g2.drawString("Till level: " + (xpPerHR != 0 ? ((long)(xpTillLevel*3600000D)/xpPerHR) : ""), 15, 280);
    }


    @Override
    public void start()
    {
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
            int area = gui.getArea();
            init = true;
            crafter.setArea(area);
            mover.setArea(area);
            if(!ctx.inventory.select().id(Resources.talismanIDs).isEmpty())
                banker.setTalisman(Resources.talismanIDs[area]);

            taskList.addAll(Arrays.asList(crafter, mover, banker, new Antiban(ctx)));
        }
    }

    @Override
    public void stop() {
        System.out.println("Script Stopped!");
    }
}