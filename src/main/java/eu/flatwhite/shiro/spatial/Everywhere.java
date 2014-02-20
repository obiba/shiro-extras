package eu.flatwhite.shiro.spatial;

/**
 * A {@code Spatial} that is everywhere at the same time. The relation of any
 * other point with this one should always be {@code Relation#TOUCHES}.
 * <p>
 * Used for handling wildcards in a permission string.
 * 
 * @author philippe.laflamme@gmail.com
 */
public final class Everywhere implements Spatial {

  private static final long serialVersionUID = -9056328572693111506L;

  private final Space space;
    
    public Everywhere(Space space) {
	this.space = space;
    }
    
    @Override
    public Space getSpace() {
        return space;
    }

    /**
     * We are always at a distance of zero to any other {@code Spatial} or our
     * space.
     * <p>
     * This method returns {@code 0} for any {@code Spatial} instance within our
     * space, otherwise, it delegates to the space's implementation.
     */
    @Override
    public double distance(Spatial spatial) {
	return getSpace().isContaining(spatial) ? 0d : getSpace().distance(this, spatial);
    }
}
