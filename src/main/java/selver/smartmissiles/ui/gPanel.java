package selver.smartmissiles.ui;

import selver.smartmissiles.agents.AgentManager;
import selver.smartmissiles.debug.Debug;
import selver.smartmissiles.physics.PhysicsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class gPanel extends JPanel implements Runnable{

    public static final Integer width=800;
    public static final Integer height = 600;

    private Thread uiThread;
    private boolean running=false;
    public static int FPS = 100;
    private long targetTime = 1000/FPS;

    private BufferedImage image;
    private Graphics2D g;
//
    public gPanel()
    {
        super();
        setPreferredSize(new Dimension(width,height));
        addKeyListener(new KeyManager());
        addMouseListener(new KeyManager());
        setFocusable(true);
        requestFocus();
    }
    public void addNotify()
    {
        super.addNotify();
        if (this.uiThread==null)
        {
            uiThread = new Thread(this);
            addKeyListener(new KeyManager());
            uiThread.start();
        }
    }


    private void init()
    {
        running = true;
        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        g = image.createGraphics();
        AgentManager.initAgents(100);
        Debug.print("Init");
    }

    @Override
    public void run() {
        init();
        long start;
        long elapsed;
        long wait;
        while(running)
        {
            start = System.nanoTime();
            update();
            draw();
            drawToScreen();
            elapsed = System.nanoTime()-start;
            wait = targetTime - elapsed/1000000;
            if (wait < 0)
            {
                Debug.print("Cant keep up!");
                wait =0;
            }
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update()
    {
        AgentManager.updateAgents();
        PhysicsManager.updatePhysics();
    }

    public void draw()
    {
        super.paint(g); //must redraw. Should be another way
        AgentManager.draw(g);
        Font font = new Font("Serif", Font.PLAIN, 20);
//        g.setFont(font);
//        g.setPaint(new Color(0));
//        g.drawString(Integer.toString(FPS),width-100,height-30);
    }

    public void  drawToScreen()
    {

        Graphics g2 = getGraphics();
        g2.drawImage(image,0,0,null);
    }
}



