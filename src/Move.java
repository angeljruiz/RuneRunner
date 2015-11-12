import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.TilePath;


/**
 * Created by Angel on 11/8/2015.
 */
public class Move extends Task<ClientContext>
{
    private int[] runeIDs = { 556, 557, 559 };
    private int[] bankIDs = { 24101, 11748, 3194, 11744, 3193 };
    private int runeEs = 1436;
    private Tile alEnd = new Tile(3312, 3253, 0), varEnd = new Tile(3305, 3472, 0), airEnd = new Tile(2841, 4830, 0), earEnd = new Tile(2657, 4830, 0), firEnd = new Tile(2657, 4830, 0), bodEnd = new Tile(2521, 4847, 0), edEnd = new Tile(3054, 3443, 0), falEnd = new Tile(3054, 3443, 0);
    private int falAltar = 14399, varAltar = 14405, alAltar = 14407, edAltar = 14409;
    public static Tile[] tileVarEar = {new Tile(3254, 3423, 0), new Tile(3256, 3427, 0), new Tile(3260, 3428, 0), new Tile(3265, 3429, 0), new Tile(3269, 3428, 0), new Tile(3274, 3428, 0), new Tile(3279, 3429, 0), new Tile(3283, 3430, 0), new Tile(3284, 3434, 0), new Tile(3284, 3439, 0), new Tile(3286, 3443, 0), new Tile(3286, 3448, 0), new Tile(3286, 3453, 0), new Tile(3289, 3456, 0), new Tile(3292, 3459, 0), new Tile(3295, 3462, 0), new Tile(3295, 3467, 0), new Tile(3299, 3469, 0), new Tile(3303, 3470, 0)};
    public static Tile[] tileAlFir = {new Tile(3382, 3268, 0), new Tile(3376, 3265, 0), new Tile(3369, 3265, 0), new Tile(3362, 3265, 0), new Tile(3355, 3265, 0), new Tile(3348, 3265, 0), new Tile(3341, 3265, 0), new Tile(3334, 3265, 0), new Tile(3327, 3265, 0), new Tile(3325, 3259, 0), new Tile(3325, 3252, 0), new Tile(3323, 3246, 0), new Tile(3319, 3241, 0), new Tile(3314, 3237, 0), new Tile(3310, 3242, 0), new Tile(3310, 3249, 0)};
    public static Tile[] tileEdBod = {new Tile(3091, 3491, 0), new Tile(3087, 3488, 0), new Tile(3083, 3485, 0), new Tile(3081, 3481, 0), new Tile(3080, 3477, 0), new Tile(3080, 3472, 0), new Tile(3080, 3467, 0), new Tile(3084, 3466, 0), new Tile(3086, 3462, 0), new Tile(3086, 3457, 0), new Tile(3084, 3453, 0), new Tile(3081, 3450, 0), new Tile(3077, 3451, 0), new Tile(3073, 3450, 0), new Tile(3072, 3446, 0), new Tile(3069, 3443, 0), new Tile(3065, 3440, 0), new Tile(3061, 3438, 0), new Tile(3059, 3438, 0), new Tile(3059, 3438, 0)};
    public static Tile[] tileFalAir = { new Tile(3008, 3358, 0), new Tile(3006, 3354, 0), new Tile(3007, 3350, 0), new Tile(3007, 3345, 0), new Tile(3007, 3340, 0), new Tile(3007, 3335, 0), new Tile(3007, 3330, 0), new Tile(3007, 3325, 0), new Tile(3007, 3320, 0), new Tile(3007, 3315, 0), new Tile(3005, 3311, 0), new Tile(3004, 3306, 0), new Tile(3000, 3304, 0), new Tile(2995, 3304, 0), new Tile(2992, 3301, 0), new Tile(2988, 3300, 0), new Tile(2987, 3296, 0), new Tile(2983, 3294, 0), new Tile(2983, 3293, 0)};
    private int currentAltar = edAltar;
    private TilePath currentPath = ctx.movement.newTilePath(tileEdBod);
    private TilePath currentPathRev = ctx.movement.newTilePath(tileEdBod).reverse();
    private Tile currentAreaEnd = edEnd;
    private boolean banking = false;
    Random rng = new Random();




    public Move (ClientContext ctx)
    {
        super(ctx);
    }
    @Override
    public boolean activate()
    {
        if(ctx.game.tab() != Game.Tab.INVENTORY)
        {
            ctx.game.tab(Game.Tab.INVENTORY);
        }
        banking = (ctx.inventory.select().id(runeEs).count() >= 1 ? false : true);
        if(ctx.movement.distance(bodEnd) == -1 && ctx.movement.distance(firEnd) == -1 && ctx.movement.distance(airEnd) == -1 && ctx.movement.distance(earEnd) == -1 && !(banking && ctx.objects.select().id(bankIDs).nearest().poll().inViewport())) {
            System.out.println("Going to move!");
            return true;
        }
        return false;
    }

    @Override
    public void execute()
    {
        if(banking)
        {
            if(ctx.movement.distance(ctx.objects.select().id(bankIDs).nearest().poll()) > 5)
            {
                System.out.println("Found an altar!");

                ctx.movement.step(ctx.objects.select().id(bankIDs).poll());
            }
            else if(!currentPathRev.traverse())
            {
                ctx.movement.step(currentPathRev.next());
            }
        }
        if(!banking && !ctx.objects.select().id(currentAltar).poll().inViewport()){
            if(ctx.movement.distance(currentAreaEnd) > 5)
            {
                ctx.movement.step(currentAreaEnd);
            }
            else if(!currentPath.traverse() && !ctx.objects.select().id(currentAltar).poll().valid())
                ctx.movement.step(currentPath.next());
        }
        if(ctx.objects.select().id(currentAltar).poll().valid() && !banking)
        {
            System.out.println("Found an altar!");
            if(!ctx.objects.select().id(currentAltar).poll().inViewport())
            {
                ctx.camera.turnTo(ctx.objects.select().id(currentAltar).poll());
                Condition.sleep(rng.nextInt(750, 1000));
            }
            ctx.objects.select().id(currentAltar).poll().interact("Enter");
        }
        Condition.sleep(rng.nextInt(750, 1250));
    }
}
