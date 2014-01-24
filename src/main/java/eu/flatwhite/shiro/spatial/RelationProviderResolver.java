package eu.flatwhite.shiro.spatial;

import java.io.Serializable;

public interface RelationProviderResolver extends Serializable {
    RelationProvider resolveRelationProvider(Spatial spatial,
	    String relationProviderString);
}
