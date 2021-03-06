package eu.flatwhite.shiro.spatial.maven;

import eu.flatwhite.shiro.spatial.AbstractSpace;
import eu.flatwhite.shiro.spatial.Spatial;

public class ArtifactSpace extends AbstractSpace {
  private static final long serialVersionUID = -1507328742375354347L;

  private final ArtifactCoordinate origin = new ArtifactCoordinate(this, "",
	    "", "0");

    public Spatial getOrigin() {
	return origin;
    }

    public boolean isContaining(Spatial spatial) {
	return spatial instanceof ArtifactCoordinate;
    }

    protected double calculateDistance(Spatial s1, Spatial s2) {
	return calculateDistance((ArtifactCoordinate) s1,
		(ArtifactCoordinate) s2);
    }

    protected double calculateDistance(ArtifactCoordinate a1,
	    ArtifactCoordinate a2) {
	if (equals(a1.getGroupId(), a2.getGroupId())
		&& equals(a1.getArtifactId(), a2.getArtifactId())
		&& equals(a1.getVersion(), a2.getVersion())) {
	    return 0;
	} else {
	    return Double.NaN;
	}
    }

    protected static boolean equals(String str1, String str2) {
	return (str1 == null ? str2 == null : str1.equalsIgnoreCase(str2));
    }
}
