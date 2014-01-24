package eu.flatwhite.shiro.spatial;

import java.io.Serializable;

/**
 * Resolves a {@link Space} instance when parsing a permission string.
 * 
 * @see SpatialPermissionResolver
 * @author philippe.laflamme@gmail.com
 */
public interface SpaceResolver extends Serializable {

    public Space resolveSpace(String spaceString);

}
