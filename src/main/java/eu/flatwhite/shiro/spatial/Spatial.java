package eu.flatwhite.shiro.spatial;

import java.io.Serializable;

/**
 * The point in space.
 */
public interface Spatial extends Serializable {
    /**
     * Returns the space where this point is defined.
     * 
     * @return
     */
    Space getSpace();

    /**
     * Returns the distance between this spatial and the provided spatial. This
     * method is actually a shortcut to
     * <code>Space.distance(this, spatial);</code>, so same applies here.
     * 
     * @param spatial
     * @return
     */
    double distance(Spatial spatial);
}
