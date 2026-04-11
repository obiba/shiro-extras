package eu.flatwhite.shiro.spatial.finite;

import java.util.List;

import eu.flatwhite.shiro.spatial.AbstractSpace;
import eu.flatwhite.shiro.spatial.Spatial;

/**
 * A space defined on n-tree (just like URI or FS paths are). The space between
 * two nodes is defined only if they lay on same "path" (a sequence of nodes
 * from root to the node being last node of the biggest path, biggest chain of
 * nodes). Hence, one of the two compared path must contain the other as
 * "prefix".
 * 
 * @author cstamas
 */
public class NodeSpace extends AbstractSpace {
  private static final long serialVersionUID = -4318150961936366069L;

  public Node getOrigin() {
	return new Node(this);
    }

    public boolean isContaining(Spatial spatial) {
	return spatial instanceof Node;
    }

    @Override
    protected double calculateDistance(Spatial s1, Spatial s2) {
	Node p1 = (Node) s1;
	Node p2 = (Node) s2;

	List<Node> path1 = p1.getPath();
	List<Node> path2 = p2.getPath();

	// Identify which path is shorter (potential prefix / ancestor).
	// When lengths are equal both are candidates; we just pick path1 as shorter.
	List<Node> shorter = path1.size() <= path2.size() ? path1 : path2;
	List<Node> longer  = path1.size() <= path2.size() ? path2 : path1;

	// Nodes must lie on the same chain (shorter must be a wildcard-aware
	// prefix of longer). For nodes on different chains return NaN.
	if (isWildcardPrefix(shorter, longer)) {
	    return Math.abs(path1.size() - path2.size());
	} else {
	    return Double.NaN;
	}
    }

    /**
     * Returns {@code true} when {@code prefix} is a segment-wise prefix of
     * {@code path}, where a segment equal to {@code "*"} in either list matches
     * any single segment in the other list (including another {@code "*"}).
     *
     * @param prefix the shorter (or equal-length) path
     * @param path   the longer (or equal-length) path
     * @return {@code true} if {@code prefix} is a wildcard-aware prefix of {@code path}
     */
    private boolean isWildcardPrefix(List<Node> prefix, List<Node> path) {
	for (int i = 0; i < prefix.size(); i++) {
	    String seg1 = prefix.get(i).getPathElem();
	    String seg2 = path.get(i).getPathElem();
	    if (!"*".equals(seg1) && !"*".equals(seg2) && !seg1.equals(seg2)) {
		return false;
	    }
	}
	return true;
    }
}
