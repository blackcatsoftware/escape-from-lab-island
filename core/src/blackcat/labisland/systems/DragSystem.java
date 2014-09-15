package blackcat.labisland.systems;

import blackcat.labisland.components.Velocity;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;

public class DragSystem extends EntityProcessingSystem
{
    static public final float DRAG_X = 12.0f;
    static public final float DRAG_Y = 9.0f;
    
    private ComponentMapper<Velocity> vel_mapper = null;
    
    
    @SuppressWarnings("unchecked")
    public DragSystem()
    {
        super(Filter.allComponents(Velocity.class));
    }

    @Override
    public void initialize()
    {
        vel_mapper = world.getMapper(Velocity.class);
    }

    @Override
    protected void process(Entity entity)
    {
        Velocity velocity = vel_mapper.get(entity);
        
        if (velocity.getDX() > 0) velocity.setDX(velocity.getDX() - Math.min(DRAG_X, velocity.getDX()));
        if (velocity.getDX() < 0) velocity.setDX(velocity.getDX() + Math.min(DRAG_X, -velocity.getDX()));
        
        if (velocity.getDY() > 0) velocity.setDY(velocity.getDY() - Math.min(DRAG_Y, velocity.getDY()));
        if (velocity.getDY() < 0) velocity.setDY(velocity.getDY() + Math.min(DRAG_Y, -velocity.getDY()));
    }
}
