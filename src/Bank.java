import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Angel on 11/8/2015.
 */
public class Bank extends Task<ClientContext>
{
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

        if(ctx.inventory.select().id(Resources.runeEs).count() == 0 && ctx.objects.select().id(Resources.bankIDs).nearest().poll().inViewport())
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
            Condition.sleep(Resources.rng.nextInt(750, 1000));
        }
        if(!ctx.bank.opened())
        {
            ctx.objects.select().id(Resources.bankIDs).nearest().poll().interact("Bank");
            Condition.sleep(Resources.rng.nextInt(750, 1000));
        } else {
            if(!ctx.inventory.select().id(Resources.runeIDs).isEmpty())
            {
                for (int ID : Resources.runeIDs) {
                    if(!ctx.inventory.select().id(ID).isEmpty()) {
                        ctx.bank.deposit(ID, 0);
                    }
                }
                Condition.sleep(Resources.rng.nextInt(750, 1000));
            } else {
                if(usingTalisman && ctx.inventory.select().id(talismanID).isEmpty()) {
                    ctx.bank.withdraw(talismanID, 1);
                    Condition.sleep(Resources.rng.nextInt(750, 1000));
                }
                if(ctx.bank.select().id(Resources.runeEs).count() != 0) {
                    ctx.bank.withdraw(Resources.runeEs, 0);
                    Condition.sleep(Resources.rng.nextInt(750, 1000));
                }
            }
        }
    }
}
