import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

/**
 * Created by Angel on 11/8/2015.
 */
public class Craft extends Task<ClientContext>
{
    private Tile currentPortalTile;
    private Tile currentAltarTile;
    private int currentAltar;
    private int currentPortal;

    /*Condition.wait(new Callable<Boolean>() {
    @Override
    public Boolean call() throws Exception {
        return !ctx.players.local().inCombat();
    }}, 500, 10);*/

    public Craft(ClientContext ctx) {
        super(ctx);
    }

    public void setArea(int areaID)
    {
        switch (areaID)
        {
            case 0: //Fally air
                currentPortalTile = Resources.airPortalTile;
                currentAltarTile = Resources.airAltarTile;
                currentAltar = Resources.airAltarID;
                currentPortal = Resources.airPortal;
                break;
            case 1: //Varrock Earth
                currentPortalTile = Resources.earPortalTile;
                currentAltarTile = Resources.earAltarTile;
                currentAltar = Resources.earAltarID;
                currentPortal = Resources.earPortal;
                break;
            case 2: //Al Fire
                currentPortalTile = Resources.firPortalTile;
                currentAltarTile = Resources.firAltarTile;
                currentAltar = Resources.firAltarID;
                currentPortal = Resources.firPortal;
                break;
            case 3: //Ed Body
                currentPortalTile = Resources.bodPortalTile;
                currentAltarTile = Resources.bodAltarTile;
                currentAltar = Resources.bodAltarID;
                currentPortal = Resources.bodPortal;
                break;
        }
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
        if(ctx.movement.distance(currentAltarTile) > 5 && ctx.inventory.select().id(Resources.runeEs).count() != 0)
        {
            System.out.println("Running to altar");
            ctx.movement.step(currentAltarTile);
            Condition.sleep(Random.nextInt(1000, 1500));
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
                }, 250, 6);
        }
        if(ctx.movement.distance(currentPortalTile) > 5 && ctx.inventory.select().id(Resources.runeEs).isEmpty())
        {
            System.out.println("Walking to portal");
            ctx.movement.step(ctx.objects.select().id(currentPortal).poll());
            Condition.sleep(Random.nextInt(1000, 1500));
        }
        if(ctx.inventory.select().id(Resources.runeEs).count() == 0)
        {
            System.out.println("Using portal");
            if(ctx.objects.select().id(currentPortal).poll().interact("Use"))
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.movement.distance(currentPortalTile) != -1;
                }
            }, 250, 6);
        }
    }
}
