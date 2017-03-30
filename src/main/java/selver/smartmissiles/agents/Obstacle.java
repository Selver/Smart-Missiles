package selver.smartmissiles.agents;

import selver.smartmissiles.physics.Vector2;


public class Obstacle {
    public Vector2 pos;
    public  Vector2 size;
    public Obstacle(Vector2 pos_,Vector2 size_)
    {
        this.pos = pos_;
        this.size = size_;
    }
    public String toString()
    {
        return (this.pos.toString() + " " + this.size.toString());
    }
}
