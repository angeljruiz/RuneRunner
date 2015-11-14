import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Angel on 11/8/2015.
 */
public class Bank extends Task<ClientContext>
{
    private int runeEs = 1436;
    private int[] bankIDs = { 24101, 11748, 3194, 11744, 3193 };
    private Random rng = new Random();
    private int talismanID = -1;
    private boolean usingTalisman;


    public Bank(ClientContext ctx){
        super(ctx);
    }

    public void setTalisman(int ID)
    {
        usingTalisman = true;
        talismanID = ID;
    }

    @Override
    public boolean activate()
    {

        if(ctx.inventory.select().id(runeEs).count() == 0 && ctx.objects.select().id(bankIDs).nearest().poll().inViewport())
        {
            System.out.println("Banking!");
            return true;
        }
        return false;
    }

    @Override
    public void execute()
    {
        if(ctx.objects.select().id(3193).nearest().poll().valid())
        {
            ctx.objects.select().id(3193).nearest().poll().interact("Open");
            Condition.sleep(rng.nextInt(750, 1000));
        }
        if(!ctx.bank.opened())
        {
            ctx.objects.select().id(bankIDs).nearest().poll().interact("Bank");
            Condition.sleep(rng.nextInt(750, 1000));
        } else {
            if(!ctx.inventory.select().isEmpty())
            {
                ctx.bank.depositInventory();
                Condition.sleep(rng.nextInt(750, 1000));
            } else {
                /*if(usingTalisman) {
                    ctx.bank.withdraw(talismanID, 1);
                    Condition.sleep(rng.nextInt(750, 1000));
                } */
                if(ctx.bank.select().id(runeEs).count() != 0) {
                    ctx.bank.withdraw(runeEs, 0);
                    Condition.sleep(rng.nextInt(750, 1000));
                }
            }
        }
    }
}
