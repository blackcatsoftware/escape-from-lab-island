package blackcat.labisland.systems;

import blackcat.labisland.components.Position;
import blackcat.labisland.components.Velocity;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class OutOfBoundsSystem extends EntityProcessingSystem
{
    private ComponentMapper<Position> pos_mapper = null;
    private ComponentMapper<Velocity> vel_mapper = null;
    
    
    
    @SuppressWarnings("unchecked")
    public OutOfBoundsSystem()
    {
        super(Filter.allComponents(Position.class, Velocity.class));
    }

    @Override
    public void initialize()
    {
        pos_mapper = world.getMapper(Position.class);
        vel_mapper = world.getMapper(Velocity.class);
    }

    @Override
    protected void process(Entity entity)
    {
        Position position = pos_mapper.get(entity);
        Velocity velocity = vel_mapper.get(entity);
        
        if (position.getX() < 0)
        {
            position.setX(0.0f);
            velocity.setDX(-velocity.getDX());
        }
        
        if (position.getX() > Gdx.graphics.getWidth())
        {
            position.setX(Gdx.graphics.getWidth());
            velocity.setDX(-velocity.getDX());
        }
        
        if (position.getY() < 0)
        {
            position.setY(0.0f);
            velocity.setDY(-velocity.getDY());
        }
        
        if (position.getY() > Gdx.graphics.getHeight())
        {
            position.setY(Gdx.graphics.getHeight());
            velocity.setDY(-velocity.getDY());
        }
    }
}