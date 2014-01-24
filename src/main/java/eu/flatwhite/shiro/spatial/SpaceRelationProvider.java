package eu.flatwhite.shiro.spatial;

import java.io.Serializable;

/**
 * Strategy for providing {@code RelationProvider} instances for a specific
 * {@code Space} instance. Implementations of this interface are used by
 * {@link SpatialPermissionResolver} when multiple {@code Space}s exist.
 * 
 * @see SpatialPermissionResolver
 * @author philippe.laflamme@gmail.com
 */
public interface SpaceRelationProvider extends Serializable {

    /**
     * Provides the {@code RelationProvider} instance for the given {@code Space}
     * 
     * @param space the space for which to return the {@code RelationProvider}
     * @return the {@code RelationProvider} instance
     */
    public RelationProvider getRelationProvider(Space space);

}
