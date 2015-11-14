import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Angel on 11/13/2015.
 */
public class Antiban extends Task<ClientContext> {
    private int[] NPCs = { 6748 };

    Random rng = new Random();

    public Antiban(ClientContext ctx) { super(ctx); }

    @Override
    public boolean activate()
    {
       // if(rng.nextInt(0, 100) >= 80)
         //   return true;
        return false;
    }

    @Override
    public void execute()
    {
        System.out.println("Antiban");
       // if(rng.nextInt(0, 100) >= 75)
     //       ctx.game.tab(Game.Tab.)
    }
}
