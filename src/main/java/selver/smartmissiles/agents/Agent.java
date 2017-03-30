package selver.smartmissiles.agents;


import selver.smartmissiles.physics.Vector2;

import java.awt.*;

public abstract class Agent {

    public boolean collided = false;
    public boolean hasPhysics;
    public int collideLayer=0;
    public float mass=0;
    public Vector2 pos= new Vector2();
    public Vector2 vel = new Vector2();
    public Vector2 force = new Vector2();
    public float angle = 0;
    public float score = 0;

    public abstract void draw(Graphics2D g); // every agent should know how to draw himself
    public abstract void update();
    public abstract Agent mate(Agent parent2);

}
