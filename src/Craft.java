import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

/**
 * Created by Angel on 11/8/2015.
 */
public class Craft extends Task<ClientContext>
{
    private int currentAltar;
    private int currentPortal;

    public Craft(ClientContext ctx) {
        super(ctx);
    }

    public void setArea(int areaID)
    {
        currentAltar = Resources.altarIDs[areaID];
        currentPortal = Resources.portalIDs[areaID];
    }

    @Override
    public boolean activate()
    {
        if(ctx.objects.select().id(currentAltar).poll().valid())
        {
            System.out.println("Going to craft!");
            return true;
        } else return false;
    }

    @Override
    public void execute()
    {
        if(ctx.objects.select().id(currentAltar).within(5).isEmpty() && ctx.inventory.select().id(Resources.runeEs).count() != 0)
        {
            System.out.println("Running to altar");
            ctx.movement.step(ctx.objects.select().id(currentAltar).poll());
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.objects.select().id(currentAltar).within(5).isEmpty();
                }
            }, 250, 6);
        }
        if(!ctx.objects.select().id(currentAltar).poll().inViewport())
        {
            System.out.println("Turning to altar");
            ctx.camera.turnTo(ctx.objects.select().id(currentAltar).poll());
        }
        if(!ctx.inventory.select().id(Resources.runeEs).isEmpty())
        {
            System.out.println("Crafting");
            if(ctx.objects.select().id(currentAltar).poll().interact("Craft"))
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !ctx.inventory.select().id(Resources.runeIDs).isEmpty();
                    }
                }, 150, 9);
        }
        if(ctx.objects.select().id(currentPortal).within(5).isEmpty() && ctx.inventory.select().id(Resources.runeEs).isEmpty())
        {
            System.out.println("Walking to portal");
            ctx.movement.step(ctx.objects.select().id(currentPortal).poll());
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.objects.select().id(currentPortal).within(5).isEmpty();
                }
            }, 150, 9);
        }
        if(ctx.inventory.select().id(Resources.runeEs).count() == 0)
        {
            System.out.println("Using portal");
            if(ctx.objects.select().id(currentPortal).poll().interact("Use"))
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !ctx.objects.select().id(Resources.ruinIDs).isEmpty();
                    }
            }, 250, 6);
        }
    }
}
