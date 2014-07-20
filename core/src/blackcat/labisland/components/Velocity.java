package blackcat.labisland.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Velocity implements Component
{
    private Vector2 velocity = null;
    
    
    public Velocity()
    {
        this(0.0f, 0.0f);
    }
    
    public Velocity(float x, float y)
    {
        velocity = new Vector2(x, y);
    }
    
    public Vector2 getVelocity()
    {
        return velocity;
    }
    
    public float getDX()
    {
        return velocity.x;
    }
    
    public float getDY()
    {
        return velocity.y;
    }
    
    public void setVelocity(Vector2 velocity)
    {
        // Don't keep original reference
        setVelocity(velocity.y, velocity.y);
    }
    
    public void setVelocity(float x, float y)
    {
        velocity.x = x;
        velocity.y = y;
    }
    
    public void setDX(float x)
    {
        velocity.x = x;
    }
    
    public void setDY(float y)
    {
        velocity.y = y;
    }
    
    @Override
    public void reset()
    {
        velocity.set(0.0f, 0.0f);
    }
}
