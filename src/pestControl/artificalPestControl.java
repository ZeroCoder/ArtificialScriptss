package pestControl;

import org.shadowbot.bot.api.Job;
import org.shadowbot.bot.api.Manifest;
import org.shadowbot.bot.api.ShadowScript;
import org.shadowbot.bot.api.SkillCategory;
import org.shadowbot.bot.api.listeners.PaintListener;
import org.shadowbot.bot.api.util.Random;
import org.shadowbot.bot.api.util.Time;
import pestControl.nodes.Gui;
import pestControl.nodes.actionHandler;

import java.awt.*;

/**
 * User: Pen
 */
@Manifest(author = "Farhad", category = SkillCategory.CRAFTING, description = "Crafts :)", scriptname = "ArtificialCrafter")

public class artificalPestControl extends ShadowScript implements PaintListener {

    private final Job[] jobs = {new actionHandler()};
    public long STARTTIME;
    Gui gui = new Gui();
    private FontMetrics fontMetrics;

    @Override
    public void paint(Graphics graphics) {
        long timerun = System.currentTimeMillis() - STARTTIME;
        DrawText(graphics, "ArtificialPestControl V 1.0", 15, 180, Color.red);
        DrawText(graphics, "Runtime: " + Time.parse(timerun), 15, 200, Color.red);
        DrawText(graphics, "Made by Calle :)", 15, 220, Color.red);
    }

    @Override
    public int loop() {
        try {
            for (Job job : this.jobs)
                if (job.isActive()) {
                    job.run();
                    return Random.nextInt(125, 150);
                }
        } catch (Exception e) {
        }
        return Random.nextInt(150, 175);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onStart() {
        gui.setVisible(true);
        STARTTIME = System.currentTimeMillis();
        while (actionHandler.runGUI) ;
        sleep(Random.nextInt(100, 150));

    }

    @Override
    public void onStop() {
    }

// DrawText Here for Paint :)
}
