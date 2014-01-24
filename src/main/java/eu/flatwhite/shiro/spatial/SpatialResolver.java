package eu.flatwhite.shiro.spatial;

import java.io.Serializable;

/**
 * Resolves a {@link Spatial} instance from a string.
 * 
 * @see {@link SpatialPermissionResolver}
 * @author philippe.laflamme@gmail.com
 */
public interface SpatialResolver extends Serializable {

    public Spatial resolveSpatial(Space space, String spatialString);
}
