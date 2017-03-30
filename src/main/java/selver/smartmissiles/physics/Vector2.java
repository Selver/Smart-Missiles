package selver.smartmissiles.physics;


import com.sun.org.apache.regexp.internal.RE;

public class Vector2 {

    public float x;
    public float y;

    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2()
    {
        this.y = 0;
        this.x = 0;
    }

    public Vector2 add(Vector2 b) {
        return new Vector2(this.x+b.x,this.y+b.y);
    }

    public Vector2 substract(Vector2 b) {
        return new Vector2(this.x-b.x,this.y-b.y);
    }

    public float magnitude()
    {
        return (float)(Math.sqrt((this.x*this.x + this.y*this.y)));
    }

    public Vector2 scalarMult(float a)
    {
        return new Vector2(this.x*a,this.y*a);
    }

    public Vector2 scalarDiv(float a)
    {
        return new Vector2(this.x/a,this.y/a);
    }


    public Vector2 normalize()
    {
        float mag = this.magnitude();
        return new Vector2(this.x/mag,this.y/mag);
    }

    public Vector2 setMagnitude(float mag)
    {
        return this.normalize().scalarMult(mag);
    }

    public float angle()
    {
        return (float) Math.toDegrees(Math.atan2(this.y,this.x));
    }

    public String toString()
    {
        return "["+this.x+","+this.y+"]";
    }

    public float distToAbs(Vector2 vector2)
    {
        return Math.abs((new Vector2(vector2.x-this.x,vector2.y-this.y)).magnitude());
    }
    public float distTo(Vector2 vector2)
    {
        return (new Vector2(vector2.x-this.x,vector2.y-this.y)).magnitude();
    }
}
