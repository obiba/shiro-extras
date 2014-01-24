package eu.flatwhite.shiro.spatial;

/**
 * Sphere relation provider uses space origin of the supplied spatial together
 * with each provided spatial to define a sphere (in 3d, circle in 2d,
 * hyper-shere in 4d, etc), and it just checks for the relation of two spheres:
 * if s2 is "smaller", and is hence contained in bigger s1 sphere, it is INSIDE.
 * If the two spheres has equal radius, they TOUCHES, otherwise OUTSIDE is
 * returned.
 * 
 * @author cstamas
 */
public class SphereRelationProvider extends AbstractRelationProvider {
  private static final long serialVersionUID = 1628730358857769622L;

  public Relation relate(Spatial s1, Spatial s2) {
	final double d1 = distanceToOrigin(s1);

	final double d2 = distanceToOrigin(s2);

	Relation relation;

	if (d1 < d2) {
	    // is outside
	    relation = Relation.OUTSIDE;
	} else if (d1 == d2) {
	    // touches, or better, "is on the surface" (if we use 3d analogy)
	    relation = Relation.TOUCHES;
	} else {
	    // is inside
	    relation = Relation.INSIDE;
	}

	return relation;
    }

    /**
     * Returns the distance of {@code s} to the {@code origin} of its 
     * {@code Space}
     * @param s the spatial to compute the distance from the origin
     * @return the distance of {@code s} from its {@code Space} {@code origin}
     * @see {@code Space#getOrigin()}
     */
    protected double distanceToOrigin(Spatial s) {
    	return s.distance(s.getSpace().getOrigin());
    }
}
