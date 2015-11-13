import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Angel on 11/8/2015.
 */
public class Craft extends Task<ClientContext>
{
    private int runeEs = 1436;
    private int airPortal = 14841, earPortal = 14844, firPortal = 14845, bodPortal = 14846; //Portal
    private int airAltarID = 14897, earAltarID = 14900, firAltarID = 14901, bodAltarID = 14902; //Altar
    private Tile airAltarTile = new Tile(2842, 4833, 0), earAltarTile = new Tile(2658, 4839, 0), firAltarTile = new Tile(2584, 4840, 0), bodAltarTile = new Tile(2522, 4838, 0); //Tile next to altar
    private Tile airPortalTile = new Tile(2841, 4830, 0), earPortalTile = new Tile(2657, 4830, 0), firPortalTile = new Tile(2576, 4848, 0), bodPortalTile = new Tile(2521, 4834, 0); //Tile next to portal
    private Tile currentPortalTile;
    private Tile currentAltarTile;
    private int currentAltar;
    private int currentPortal;

    private int expGained = 0;

    private Random rng = new Random();


    public Craft(ClientContext ctx) {
        super(ctx);
    }

    public void setArea(int areaID)
    {
        switch (areaID)
        {
            case 0: //Fally air
                currentPortalTile = airPortalTile;
                currentAltarTile = airAltarTile;
                currentAltar = airAltarID;
                currentPortal = airPortal;
                break;
            case 1: //Varrock Earth
                currentPortalTile = earPortalTile;
                currentAltarTile = earAltarTile;
                currentAltar = earAltarID;
                currentPortal = earPortal;
                break;
            case 2: //Al Fire
                currentPortalTile = firPortalTile;
                currentAltarTile = firAltarTile;
                currentAltar = firAltarID;
                currentPortal = firPortal;
                break;
            case 3: //Ed Body
                currentPortalTile = bodPortalTile;
                currentAltarTile = bodAltarTile;
                currentAltar = bodAltarID;
                currentPortal = bodPortal;
                break;
        }
    }

    @Override
    public boolean activate()
    {
        System.out.println(ctx.skills.experience(20));
        if(ctx.objects.select().id(currentAltar).poll().valid())
        {
            System.out.println("Going to craft!");
            return true;
        } else return false;
    }

    @Override
    public void execute()
    {
        if(ctx.movement.distance(currentAltarTile) > 5 && ctx.inventory.select().id(runeEs).count() != 0)
        {
            System.out.println("Running to altar");
            ctx.movement.step(currentAltarTile);
            Condition.sleep(rng.nextInt(1000, 1500));
        }
        if(!ctx.objects.select().id(currentAltar).poll().inViewport())
        {
            System.out.println("Turning to altar");
            ctx.camera.turnTo(ctx.objects.select().id(currentAltar).poll());
            Condition.sleep(rng.nextInt(1000, 1500));
        }
        if(!ctx.inventory.select().id(runeEs).isEmpty())
        {
            System.out.println("Crafting");
            ctx.objects.select().id(currentAltar).poll().interact("Craft");
            Condition.sleep(rng.nextInt(1000, 1500));
        }
        if(ctx.movement.distance(currentPortalTile) > 5 && ctx.inventory.select().id(runeEs).isEmpty())
        {
            System.out.println("Walking to portal");
            ctx.movement.step(ctx.objects.select().id(currentPortal).poll());
            Condition.sleep(rng.nextInt(1000, 1500));
        }
        if(ctx.inventory.select().id(runeEs).count() == 0)
        {
            System.out.println("Using portal");
            ctx.objects.select().id(currentPortal).poll().interact("Use");
            Condition.sleep(rng.nextInt(1000, 1500));
        }
    }
}
