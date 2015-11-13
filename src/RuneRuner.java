import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Script.Manifest(
        name = "RuneRunner",
        description = "Runs runes!"
)
public class RuneRuner extends PollingScript<ClientContext> {
    private List<Task> taskList = new ArrayList<Task>();



    @Override
    public void start()
    {
        System.out.println("Script Started");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Painter gui = new Painter();
                JFrame frame = new JFrame("Setup");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        taskList.addAll(Arrays.asList(new Craft(ctx), new Move(ctx), new Bank(ctx)));
    }

    @Override
    public void poll() {
        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
            }
        }
    }

    @Override
    public void stop() {
        System.out.println("Script Stopped!");
    }
}