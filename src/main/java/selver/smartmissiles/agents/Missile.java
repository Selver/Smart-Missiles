package selver.smartmissiles.agents;




import selver.smartmissiles.physics.PhysicsManager;
import selver.smartmissiles.physics.Vector2;
import selver.smartmissiles.ui.gPanel;

import java.awt.*;
import java.util.ArrayList;

public class Missile extends Agent {

    public float arrivalTick;
     private Integer width =15;
    private Integer height = 25;
    private Vector2 lastForce;
    ArrayList<Vector2> forceArray;
    private Integer lifeIndex=0;
    private Integer totalLife;
    private Integer DNASize;
    private static float maxRandomForce = 5;
    public Missile(float x, float y,float a,Integer totalLife, Integer DNASize)
    {
        arrivalTick =99999;
        lastForce = new Vector2();
        hasPhysics = true;
        mass = 1;
        collideLayer = 1;
//        AgentManager.registerAgent(this);
        pos.x = x;
        pos.y = y;
        angle = a;
        forceArray = new ArrayList<>();
        this.totalLife = totalLife;
        this.DNASize = DNASize;
        this.lifeIndex=0;
        for (int i = 0; i < DNASize;i++) forceArray.add(randomForce());
    }

    @Override
    public void draw(Graphics2D g) {
        if (this.collided)
        {
            g.setPaint(new Color(100,100,100));
        }else{
            g.setPaint(new Color(0,0,200));
        }
       this.angle = vel.angle();
       int[] xPoints = {
               (int)( pos.x+Math.cos(Math.toRadians(angle))*this.height),
               (int)( pos.x+width/2*Math.cos(Math.toRadians(90-angle))),
               (int)( pos.x-width/2*Math.cos(Math.toRadians(90-angle)))
       };
        int[] yPoints = {
                (int)( pos.y+Math.sin(Math.toRadians(angle))*this.height),
                (int)( pos.y-width/2*Math.sin(Math.toRadians(90-angle))),
                (int)( pos.y+width/2*Math.sin(Math.toRadians(90-angle)))
        };
       g.fillPolygon(xPoints,yPoints,3);
    }

    @Override
    public void update() {
        if (this.pos.distToAbs(AgentManager.targetPos)<AgentManager.targetRadious && arrivalTick ==99999)
        {
            arrivalTick = lifeIndex;
        }
        if (lifeIndex < totalLife) {
            if (lifeIndex % DNASize == 0) {
                force = forceArray.get(lifeIndex / DNASize);
                lastForce = force;
            } else {
                force = lastForce;
            }
            lifeIndex++;
//            Debug.print(Integer.toString(liveIndex));
        }
    }

    private Vector2 randomForce()
    {
//        Vector2 force = new Vector2((float) Math.random()-0.5f,-(float)Math.random()*PhysicsManager.gravity.y*5);
        Vector2 force = new Vector2((float) Math.random()*4-2,-(float)Math.random()*PhysicsManager.gravity.y*20);
        force = force.setMagnitude((float)Math.random()*maxRandomForce);
        return force;
    }

    @Override
    public Agent mate(Agent parent2) {
        Missile child = new Missile(gPanel.width/2,gPanel.height-20,0,this.totalLife,this.DNASize);
        Integer point = (int)Math.floor(Math.floor(Math.random()*this.DNASize));
        child.forceArray.clear();
        for (Integer k=0; k<point;k++)
        {
            child.forceArray.add(this.forceArray.get(k));
        }
        for (Integer k=point;k<child.DNASize;k++)
        {
            child.forceArray.add(((Missile)parent2).forceArray.get(k));
        }
        //mutate
        for (int z=0;z<child.forceArray.size();z++)
        {
            if(Math.random()<0.1)
            {
                child.forceArray.set(z,randomForce());
            }
        }
//        Debug.print(child.pos.toString());
        return child;
    }
}
