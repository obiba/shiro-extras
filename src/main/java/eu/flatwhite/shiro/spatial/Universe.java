package eu.flatwhite.shiro.spatial;

/**
 * A space that contains all other spaces (used to support wildcards)
 * 
 * @author philippe.laflamme@gmail.com
 */
public class Universe extends AbstractSpace {

  private static final long serialVersionUID = 7368822215618582981L;

  @Override
    public Spatial getOrigin() {
	// yep! the origin of the universe really could be everywhere
	return new Everywhere(this);
    }

    @Override
    public boolean isContaining(Spatial spatial) {
	// The universe contains all points
	return true;
    }

    @Override
    protected double calculateDistance(Spatial s1, Spatial s2) {
	// This cannot be deterimed
	return Double.NaN;
    }

}
