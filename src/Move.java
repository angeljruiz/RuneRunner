import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.TilePath;

import java.util.concurrent.Callable;


/**
 * Created by Angel on 11/8/2015.
 */
public class Move extends Task<ClientContext>
{
    private int currentAltar = Resources.edAltar;
    private TilePath currentPath;
    private TilePath currentPathRev;
    private Tile currentAreaEnd = Resources.edEnd;
    private boolean banking = false;

    public void setArea(int areaID)
    {
        switch (areaID)
        {
            case 0: //Fally air
                currentAltar = Resources.falAltar;
                currentPath = ctx.movement.newTilePath(Resources.tileFalAir);
                currentPathRev = ctx.movement.newTilePath(Resources.tileFalAir).reverse();
                currentAreaEnd = Resources.falEnd;
                break;
            case 1: //Varrock Earth
                currentAltar = Resources.varAltar;
                currentPath = ctx.movement.newTilePath(Resources.tileVarEar);
                currentPathRev = ctx.movement.newTilePath(Resources.tileVarEar).reverse();
                currentAreaEnd = Resources.varEnd;
                break;
            case 2: //Al Fire
                currentAltar = Resources.alAltar;
                currentPath = ctx.movement.newTilePath(Resources.tileAlFir);
                currentPathRev = ctx.movement.newTilePath(Resources.tileAlFir).reverse();
                currentAreaEnd = Resources.alEnd;
                break;
            case 3: //Ed Body
                currentAltar = Resources.edAltar;
                currentPath = ctx.movement.newTilePath(Resources.tileEdBod);
                currentPathRev = ctx.movement.newTilePath(Resources.tileEdBod).reverse();
                currentAreaEnd = Resources.edEnd;
                break;
        }
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
        if(ctx.movement.distance(Resources.bodEnd) == -1 && ctx.movement.distance(Resources.firEnd) == -1 && ctx.movement.distance(Resources.airEnd) == -1 && ctx.movement.distance(Resources.earEnd) == -1) {
            if(banking && ctx.objects.select().id(Resources.bankIDs).nearest().poll().inViewport() && ctx.movement.distance(ctx.objects.select().id(Resources.bankIDs).nearest().poll()) <= 3)
                return false;
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

        if(!banking && ctx.movement.distance(currentAreaEnd) > 12)
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
        } else if(!banking && ctx.movement.distance(currentAreaEnd) <= 12)
        {
            if(!ctx.objects.select().id(currentAltar).poll().inViewport()) {
                ctx.camera.turnTo(ctx.objects.select().id(currentAltar).poll());
            }
            if(ctx.movement.distance(currentAreaEnd) <= 6) {
                ctx.objects.select().id(currentAltar).poll().interact("Enter");
                Condition.sleep(Random.nextInt(750, 100));
            } else {
                ctx.movement.step(currentAreaEnd);
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
