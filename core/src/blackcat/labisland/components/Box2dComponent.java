package blackcat.labisland.components;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class Box2dComponent implements Component
{
    private Body body = null;
    
    public Body getBody() { return body; }
    
    
    public Box2dComponent(Body body)
    {
        this.body = body;
    }
    
    @Override
    public void reset()
    {
        body.getWorld().createBody(new BodyDef());
    }
}
