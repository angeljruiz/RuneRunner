import org.powerbot.script.PaintListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Angel on 11/12/2015.
 */
public class Painter implements PaintListener, MouseListener {
    boolean hide = false;
    Point p;
    Rectangle close = new Rectangle(486, 348, 23, 23);
    Rectangle open = new Rectangle(486, 309, 23, 23);


    public void repaint(Graphics g1)
    {
        Graphics2D g = (Graphics2D)g1;
        g.setColor(Color.BLACK);
        g.drawString("Hello", 246, 429);
    }
    @Override
    public void mouseExited(MouseEvent me)
    {

    }

    @Override
    public  void mouseEntered(MouseEvent me)
    {

    }

    @Override
    public void mouseReleased(MouseEvent me)
    {

    }

    @Override
    public void mousePressed(MouseEvent me)
    {

    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
        p = me.getPoint();
    }
}
