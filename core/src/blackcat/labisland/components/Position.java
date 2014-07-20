package blackcat.labisland.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Position implements Component
{
    private Vector2 position = null;
    
    
    public Position()
    {
        this(0.0f, 0.0f);
    }
    
    public Position(float x, float y)
    {
        position = new Vector2(x, y);
    }
    
    public Vector2 getPosition()
    {
        return position;
    }
    
    public float getX()
    {
        return position.x;
    }
    
    public float getY()
    {
        return position.y;
    }
    
    public void setPosition(Vector2 position)
    {
        // Don't keep original reference
        setPosition(position.y, position.y);
    }
    
    public void setPosition(float x, float y)
    {
        position.x = x;
        position.y = y;
    }
    
    public void setX(float x)
    {
        position.x = x;
    }
    
    public void setY(float y)
    {
        position.y = y;
    }
    
    @Override
    public void reset()
    {
        position.set(0.0f, 0.0f);
    }
}
