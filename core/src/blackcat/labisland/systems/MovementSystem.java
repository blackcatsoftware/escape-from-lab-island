package blackcat.labisland.systems;

import blackcat.labisland.components.Position;
import blackcat.labisland.components.Velocity;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;

public class MovementSystem extends EntityProcessingSystem
{
    private ComponentMapper<Position> pos_mapper = null;
    private ComponentMapper<Velocity> vel_mapper = null;
    
    
    @SuppressWarnings("unchecked")
    public MovementSystem()
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
        
        position.getPosition().x += velocity.getDX() * world.getDelta();
        position.getPosition().y += velocity.getDY() * world.getDelta();
    }
}
