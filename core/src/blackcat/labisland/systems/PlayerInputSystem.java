package blackcat.labisland.systems;

import blackcat.labisland.components.Velocity;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor
{
    static public final float MAX_VELOCITY_X = 400f;
    static public final float MAX_VELOCITY_Y = 300f;
    
    static public final float ACCELERATION_X = 24f;
    static public final float ACCELERATION_Y = 18f;
    
    
    private ComponentMapper<Velocity> vel_mapper = null;
    
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    
    
    @SuppressWarnings("unchecked")
    public PlayerInputSystem()
    {
        super(Filter.allComponents(Velocity.class));
    }
    
    @Override
    public void initialize()
    {
        Gdx.input.setInputProcessor(this);
        
        vel_mapper = world.getMapper(Velocity.class);
    }

    @Override
    protected void process(Entity entity)
    {
        Velocity velocity = vel_mapper.get(entity);
        
        if (up && velocity.getDY() < MAX_VELOCITY_Y)
        {
            velocity.setDY(Math.min(velocity.getDY() + ACCELERATION_Y, MAX_VELOCITY_Y));
        }
        
        if (down && velocity.getDY() > -MAX_VELOCITY_Y)
        {
            velocity.setDY(Math.max(velocity.getDY() - ACCELERATION_Y, -MAX_VELOCITY_Y));
        }
        
        if (left && velocity.getDX() > -MAX_VELOCITY_X)
        {
            velocity.setDX(Math.max(velocity.getDX() - ACCELERATION_X, -MAX_VELOCITY_X));
        }
        
        if (right && velocity.getDX() < MAX_VELOCITY_X)
        {
            velocity.setDX(Math.min(velocity.getDX() + ACCELERATION_X, MAX_VELOCITY_X));
        }
    }
    
    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.LEFT :
            {
                left = true;
                break;
            }
            case Input.Keys.RIGHT :
            {
                right = true;
                break;
            }
            case Input.Keys.UP :
            {
                up = true;
                break;
            }
            case Input.Keys.DOWN :
            {
                down = true;
                break;
            }
        }
        
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.LEFT :
            {
                left = false;
                break;
            }
            case Input.Keys.RIGHT :
            {
                right = false;
                break;
            }
            case Input.Keys.UP :
            {
                up = false;
                break;
            }
            case Input.Keys.DOWN :
            {
                down = false;
                break;
            }
        }
        
        return true;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
