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
@Manifest(author = "Calle", category = SkillCategory.CRAFTING, description = "Crafts :)", scriptname = "ArtificialCrafter")

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

    public void DrawText(Graphics g, String text, int x, int y, Color c) {
        Graphics2D g2 = (Graphics2D) g;
        Color color3 = new Color(51, 51, 51, 205);
        Font font1 = new Font("Arial", 0, 12);
        g.setFont(font1);
        fontMetrics = g.getFontMetrics();
        Rectangle textBox = new Rectangle(x, y - g.getFont().getSize(),
                (int) fontMetrics.getStringBounds(text, g).getWidth() + 8,
                (int) fontMetrics.getStringBounds(text, g).getHeight() + 5);
        Paint defaultPaint = (Paint) g2.getPaint();
        g2.setPaint(new RadialGradientPaint(new Point.Double(textBox.x
                + textBox.width / 2.0D, textBox.y + textBox.height / 2.0D),
                (float) (textBox.getWidth() / 2.0D),
                new float[]{0.5F, 1.0F}, new Color[]{
                new Color(color3.getRed(), color3.getGreen(), color3
                        .getBlue(), 175),
                new Color(0.0F, 0.0F, 0.0F, 0.8F)}));
        g.fillRect(textBox.x, textBox.y + 12, textBox.width, textBox.height);
        g2.setPaint((Paint) defaultPaint);
        g.setColor(Color.BLACK);
        g.drawRect(textBox.x, textBox.y + 12, textBox.width, textBox.height);
        g.setColor(c);
        g.drawString(text, x + 4, y + 15);
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                g.setColor(new Color(255, 255, 255));
                g.drawString("" + text.charAt(i),
                        x + fontMetrics.stringWidth(text.substring(0, i)) + 4,
                        y + 15);
            }
        }
    }
}
