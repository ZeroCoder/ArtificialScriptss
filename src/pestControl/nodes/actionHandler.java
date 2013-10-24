package pestControl.nodes;

import org.shadowbot.bot.api.Job;
import org.shadowbot.bot.api.methods.data.movement.Camera;
import org.shadowbot.bot.api.methods.data.movement.Walking;
import org.shadowbot.bot.api.methods.input.Mouse;
import org.shadowbot.bot.api.methods.interactive.GameEntities;
import org.shadowbot.bot.api.methods.interactive.NPCs;
import org.shadowbot.bot.api.methods.interactive.Players;
import org.shadowbot.bot.api.util.Random;
import org.shadowbot.bot.api.wrapper.Area;
import org.shadowbot.bot.api.wrapper.GameObject;
import org.shadowbot.bot.api.wrapper.NPC;
import org.shadowbot.bot.api.wrapper.Tile;

/**
 * User: Pen
 */
public class actionHandler extends Job {

    final static Area Square = new Area(new Tile[]{new Tile(0, 0, 0),
            new Tile(0, 0, 0), new Tile(0, 0, 0),
            new Tile(0, 0, 0)});
    final static Area normalBoat = new Area(new Tile[]{new Tile(0, 0, 0),
            new Tile(0, 0, 0), new Tile(0, 0, 0),
            new Tile(0, 0, 0)});
    final static Area mediumBoat = new Area(new Tile[]{new Tile(0, 0, 0),
            new Tile(0, 0, 0), new Tile(0, 0, 0),
            new Tile(0, 0, 0)});
    final static Area veteranBoat = new Area(new Tile[]{new Tile(0, 0, 0),
            new Tile(0, 0, 0), new Tile(0, 0, 0),
            new Tile(0, 0, 0)});
    public static Area whichBoat = null;
    public static Tile normalJoin = new Tile(0, 0);
    public static Tile mediumJoin = new Tile(0, 0);
    public static Tile veteranJoin = new Tile(0, 0);
    public static Tile whichPlank;
    public Tile squareTile = new Tile(0, 0);
    public int gangplankId = 14315;
    public int[] squireId = {3159, 3055, 3161};
    public String status = "";
    public static boolean runGUI = true;
    State state;

    private boolean atSquare() {
        return Square.contains(Players.getLocal().getLocation());
    }

    private boolean atPlank() {
        return whichPlank.equals(Players.getLocal().getLocation());
    }

    private boolean atBoat() {
        return whichBoat.contains(Players.getLocal().getLocation());
    }

    @Override
    public void run() {
        if (Players.getLocal().getLocation().equals(atSquare())) {
            state = State.Attack;
        } else if (!Players.getLocal().getLocation().equals(atSquare())) {
            state = State.Walk_to_Middle;
        }
        if (Players.getLocal().getLocation().equals(atPlank())) {
            state = State.Enter_the_Boat;
        } else if (!Players.getLocal().getLocation().equals(atPlank())) {
            state = State.Walk_to_Plank;
        }
        if (Players.getLocal().getLocation().equals(atBoat())) {
            state = State.Wait_For_Escort;
        }


        switch (state) {
            case Attack:
                NPC attackers = NPCs.getNearest(squireId);
                if (attackers != null) {
                    if (attackers.isOnScreen()) {
                        if (!Players.getLocal().isInCombat()) {
                            attackers.interact("Attack");
                        }
                    }
                }
                break;
            case Enter_the_Boat:
                GameObject plank = GameEntities.getNearest(gangplankId);
                if (plank != null) {
                    if (plank.isOnScreen()) {
                        plank.interact("Board");
                    } else {
                        Camera.turnTo(plank);
                    }
                }
                break;
            case Walk_to_Plank:
                if (!Players.getLocal().getLocation().equals(atPlank())) {
                    Walking.walkTo(whichPlank);
                }
                break;
            case Walk_to_Middle:
                if (!Players.getLocal().getLocation().equals(atSquare())) {
                    Walking.walkTo(squareTile);
                }
                break;
            case Wait_For_Escort:
                // Add a Distraction while Waiting?
                break;
        }


    }

    public void antiBan() {
        int Option = Random.nextInt(1, 4);
        switch (Option) {
            case 2:
                int cameraAngel = Random.nextInt(0, 360);
                Camera.setAngel(cameraAngel);
                break;
            case 3:
                break;
            case 4:
                Mouse.setSpeed(Random.nextInt(6, 10));
                break;


        }
    }

    @Override
    public boolean isActive() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    enum State {
        Walk_to_Plank, Enter_the_Boat, Walk_to_Middle, Attack, Wait_For_Escort
    }
}
