package eu.flatwhite.shiro.spatial.finite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NodeTest {
    protected NodeSpace space;

	@Before
	public void setUp() throws Exception {

	this.space = new NodeSpace();
    }

	@Test
    public void testSimple() {
	// tree is: "/some/other"
	Node other = Node.parseString(space, "/some/other");

	Assert.assertEquals("The path string is wrong!", "/some/other",
		other.getPathString());

	// again, tree is: "/some/other", but creating it differently
	Node other1 = Node.parseString(space, "/some/other/deeper");

	Assert.assertEquals("The path string is wrong!", "/some/other/deeper",
		other1.getPathString());
    }

	@Test
    public void testRobustnessParse() {
	Node n;

	n = Node.parseString(space, "");

	Assert.assertEquals("/", n.getPathString());

	n = Node.parseString(space, "foo");

	Assert.assertEquals("/foo", n.getPathString());

	n = Node.parseString(space, "foo/bar");

	Assert.assertEquals("/foo/bar", n.getPathString());

	n = Node.parseString(space, "/foo/bar");

	Assert.assertEquals("/foo/bar", n.getPathString());

	n = Node.parseString(space, "foo//bar");

	Assert.assertEquals("/foo/bar", n.getPathString());

	n = Node.parseString(space, "//foo//bar//");

	Assert.assertEquals("/foo/bar", n.getPathString());
    }

	@Test
    public void testSpatial() {
	// tree is: "/l1/l2"
	Node l2 = Node.parseString(space, "/l1/l2");

	// tree is: "/l1/l3"
	Node l3 = Node.parseString(space, "/l1/l3");

	// tree is: "/l1"
	Node l1 = Node.parseString(space, "/l1");

	// tree is: "/l11"
	Node l11 = Node.parseString(space, "/l11");

	Assert.assertEquals(0.0, space.getOrigin().distance(space.getOrigin()), 0.0);
	Assert.assertEquals(0.0,
		space.getOrigin().distance(l2.getParent().getParent()), 0.0);
	Assert.assertEquals(0.0,
		l2.getParent().getParent().distance(space.getOrigin()), 0.0);
	Assert.assertEquals(1.0, space.getOrigin().distance(l2.getParent()), 0.0);
	Assert.assertEquals(1.0, l2.getParent().distance(space.getOrigin()), 0.0);
	Assert.assertEquals(2.0, space.getOrigin().distance(l2), 0.0);
	Assert.assertEquals(2.0, l2.distance(space.getOrigin()), 0.0);
	Assert.assertEquals(1.0, l2.distance(l2.getParent()), 0.0);
	Assert.assertEquals(1.0, l2.getParent().distance(l2), 0.0);

	Assert.assertEquals(0.0, space.getOrigin().distance(space.getOrigin()), 0.0);
	Assert.assertEquals(1.0, l1.distance(space.getOrigin()), 0.0);
	Assert.assertEquals(1.0, l2.getParent().distance(space.getOrigin()), 0.0);
	Assert.assertEquals(1.0, l3.getParent().distance(space.getOrigin()), 0.0);
	Assert.assertEquals(2.0, l2.distance(space.getOrigin()), 0.0);
	Assert.assertEquals(2.0, l3.distance(space.getOrigin()), 0.0);

	// l2 and l3 are non-comparable as we defined "distance" on Tree
	Assert.assertEquals(Double.NaN, l2.distance(l3), 0.0);
	Assert.assertEquals(Double.NaN, l3.distance(l2), 0.0);

	// l1 is comparable with both l2 and l3
	Assert.assertEquals(1.0, l2.distance(l1), 0.0);
	Assert.assertEquals(1.0, l3.distance(l1), 0.0);
	Assert.assertEquals(1.0, l1.distance(l2), 0.0);
	Assert.assertEquals(1.0, l1.distance(l3), 0.0);

	// l11 is not comparable to any other one
	Assert.assertEquals(Double.NaN, l11.distance(l1), 0.0);
	Assert.assertEquals(Double.NaN, l11.distance(l2), 0.0);
	Assert.assertEquals(Double.NaN, l11.distance(l3), 0.0);
	Assert.assertEquals(Double.NaN, l1.distance(l11), 0.0);
	Assert.assertEquals(Double.NaN, l2.distance(l11), 0.0);
	Assert.assertEquals(Double.NaN, l3.distance(l11), 0.0);
    }

    @Test
    public void testWildcardDistance() {
	// /project/*/tables vs /project/alpha/tables → same depth → TOUCHES (distance 0)
	Node wildcardTables  = Node.parseString(space, "/project/*/tables");
	Node alphaTables     = Node.parseString(space, "/project/alpha/tables");
	Assert.assertEquals(0.0, wildcardTables.distance(alphaTables), 0.0);
	Assert.assertEquals(0.0, alphaTables.distance(wildcardTables), 0.0);

	// /project/beta/tables also matches
	Node betaTables = Node.parseString(space, "/project/beta/tables");
	Assert.assertEquals(0.0, wildcardTables.distance(betaTables), 0.0);

	// /project/* and /project/alpha are at the same depth → TOUCHES (distance 0)
	Node wildcardProject = Node.parseString(space, "/project/*");
	Node alpha           = Node.parseString(space, "/project/alpha");
	Assert.assertEquals(0.0, wildcardProject.distance(alpha), 0.0);
	Assert.assertEquals(0.0, alpha.distance(wildcardProject), 0.0);

	// /project/* is shallower than /project/alpha/tables → distance 1 (OUTSIDE)
	Assert.assertEquals(1.0, wildcardProject.distance(alphaTables), 0.0);
	Assert.assertEquals(1.0, alphaTables.distance(wildcardProject), 0.0);

	// different leaf segment → NaN (unrelated chains)
	Node alphaColumns = Node.parseString(space, "/project/alpha/columns");
	Assert.assertEquals(Double.NaN, wildcardTables.distance(alphaColumns), 0.0);
	Assert.assertEquals(Double.NaN, alphaColumns.distance(wildcardTables), 0.0);

	// different depth → NaN (/project/*/tables cannot be ancestor of /project/alpha/beta/tables)
	Node deepTables = Node.parseString(space, "/project/alpha/beta/tables");
	Assert.assertEquals(Double.NaN, wildcardTables.distance(deepTables), 0.0);
	Assert.assertEquals(Double.NaN, deepTables.distance(wildcardTables), 0.0);

	// wildcard matches wildcard (/* vs /anything → distance 0)
	Node starTop    = Node.parseString(space, "/*");
	Node anything   = Node.parseString(space, "/anything");
	Assert.assertEquals(0.0, starTop.distance(anything), 0.0);
	Assert.assertEquals(0.0, anything.distance(starTop), 0.0);

	// two wildcards at same depth touch each other
	Node wild1 = Node.parseString(space, "/project/*/tables");
	Node wild2 = Node.parseString(space, "/project/*/tables");
	Assert.assertEquals(0.0, wild1.distance(wild2), 0.0);
    }
}
