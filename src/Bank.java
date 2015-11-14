import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Angel on 11/8/2015.
 */
public class Bank extends Task<ClientContext>
{
    private int runeEs = 1436;
    private int[] runeIDs = { 556, 557, 554, 559 };
    private int[] bankIDs = { 24101, 11748, 3194, 11744, 3193 };
    private int craftedRunes = 0;
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

    public int getCrafted() { return craftedRunes; }

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
            if(!ctx.inventory.select().id(runeIDs).isEmpty())
            {
                for (int ID : runeIDs) {
                    if(!ctx.inventory.select().id(ID).isEmpty()) {
                        craftedRunes += ctx.inventory.select().id(ID).poll().stackSize();
                        ctx.bank.deposit(ID, 0);
                    }
                }
                Condition.sleep(rng.nextInt(750, 1000));
            } else {
                if(usingTalisman && ctx.inventory.select().id(talismanID).isEmpty()) {
                    ctx.bank.withdraw(talismanID, 1);
                    Condition.sleep(rng.nextInt(750, 1000));
                }
                if(ctx.bank.select().id(runeEs).count() != 0) {
                    ctx.bank.withdraw(runeEs, 0);
                    Condition.sleep(rng.nextInt(750, 1000));
                }
            }
        }
    }
}
