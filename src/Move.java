import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.TilePath;

import java.util.concurrent.Callable;


/**
 * Created by Angel on 11/8/2015.
 */
public class Move extends Task<ClientContext>
{
    private int currentAltar;
    private TilePath currentPath;
    private TilePath currentPathRev;
    private boolean banking = false;

    public void setArea(int areaID)
    {
        currentAltar = Resources.altarIDs[areaID];
        currentPath = ctx.movement.newTilePath(Resources.paths[areaID]);
        currentPathRev = ctx.movement.newTilePath(Resources.paths[areaID]).reverse();
    }


    public Move (ClientContext ctx)
    {
        super(ctx);
    }
    @Override
    public boolean activate()
    {
        if(ctx.game.tab() != Game.Tab.INVENTORY)
            ctx.game.tab(Game.Tab.INVENTORY);

        banking = (ctx.inventory.select().id(Resources.runeEs).count() >= 1 ? false : true);
        if(ctx.objects.select().id(Resources.altarIDs).isEmpty()) {
            if(banking && ctx.movement.distance(ctx.objects.select().id(Resources.bankIDs).nearest().poll(), ctx.players.local()) != -1 && ctx.movement.distance(ctx.objects.select().id(Resources.bankIDs).nearest().poll(), ctx.players.local()) <= 5) {
                if (!ctx.objects.select().id(Resources.bankIDs).nearest().poll().inViewport()) {
                    ctx.camera.turnTo(ctx.objects.select().id(Resources.bankIDs).nearest().poll());
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.objects.select().id(Resources.bankIDs).nearest().poll().inViewport();
                        }
                    }, 250, 6);
                }
                return false;
            }
            System.out.println("Going to move!");
            return true;
        }
        return false;
    }

    @Override
    public void execute()
    {
        if(ctx.movement.energyLevel() > Random.nextInt(15, 30) && !ctx.movement.running())
            ctx.movement.running(true);

        if(!banking && ctx.objects.select().id(currentAltar).within(12).isEmpty())
        {
            if (!currentPath.traverse()) {
                System.out.println("Guessing");
                ctx.movement.step(currentPath.next());
            }
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.movement.distance(ctx.movement.destination()) <= 5;
                }
            }, 150, 9);
        } else if(!banking && !ctx.objects.select().id(currentAltar).within(12).isEmpty())
        {
            if(!ctx.objects.select().id(currentAltar).poll().inViewport()) {
                ctx.camera.turnTo(ctx.objects.select().id(currentAltar).poll());
            }
            if(!ctx.objects.select().id(currentAltar).within(6).isEmpty()) {
                ctx.objects.select().id(currentAltar).poll().interact("Enter");
                Condition.sleep(Random.nextInt(750, 100));
            } else {
                ctx.movement.step(ctx.objects.select().id(currentAltar).poll());
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.movement.distance(ctx.movement.destination()) <= 5;
                    }
                }, 150, 9);
            }
        }
        if(banking)
        {
            if(ctx.movement.distance(ctx.movement.destination()) <= 5 || ctx.movement.distance(ctx.movement.destination()) == -1) {
                if(ctx.movement.distance(ctx.objects.select().id(Resources.bankIDs).nearest().poll()) <= 3 && !ctx.objects.select().id(Resources.bankIDs).nearest().poll().inViewport()) {
                    ctx.camera.turnTo(ctx.objects.select().id(Resources.bankIDs).nearest().poll());
                    Condition.sleep(Random.nextInt(250, 550));
                }
                if (!currentPathRev.traverse()) {
                    ctx.movement.step(ctx.objects.select().id(Resources.bankIDs).nearest().poll());
                }
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.movement.distance(ctx.movement.destination()) <= 5;
                    }
                }, 150, 9);
            }
        }
    }
}
