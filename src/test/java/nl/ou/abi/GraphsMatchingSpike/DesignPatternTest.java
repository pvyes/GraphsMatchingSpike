package nl.ou.abi.GraphsMatchingSpike;

import nl.ou.dpd.domain.edge.Edge;
import nl.ou.dpd.domain.node.Clazz;
import nl.ou.dpd.domain.node.Interface;
import nl.ou.dpd.domain.node.Node;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Peter Vansweevelt
 *
 */
public class DesignPatternTest {
	
	@Test
	public void addEdgeTester() {
		Node leftNode = new Clazz("Node1", "Node1Name");
		Node rightNode = new Clazz("Node2", "Node2Name");
		Node rightNode2 = new Clazz("Node22", "Node22Name");
		
		//addVertex
		DesignPattern dp = new DesignPattern("TestDesignPattern");
		dp.addVertex(rightNode);
		assertTrue(dp.containsVertex(rightNode));
		assertFalse(dp.containsVertex(leftNode));
		//addVertex is false when vertex exists already
		assertFalse(dp.addVertex(rightNode));
		
		dp.addVertex(leftNode);
		assertEquals(2, dp.vertexSet().size());
		
		//addEdge
		Edge edge1 = dp.addEdge(leftNode, rightNode);
		edge1.setName("edge1");
		Node n = dp.getEdgeSource(edge1);
		assertEquals(leftNode, dp.getEdgeSource(edge1));
		assertEquals(1, dp.edgeSet().size());
		//adding an existing edge returns null (multiple edges are not allowed)
		assertFalse(dp.isAllowingMultipleEdges());
		assertNull(dp.addEdge(leftNode, rightNode));
		//change vertices of an existing edge by removing the edge from the graph, adding the new vertex (if necessary)
		//and creating an edge in the graph with the existing edge
		dp.removeEdge(edge1);
		dp.addVertex(rightNode2);
		dp.addEdge(leftNode, rightNode2, edge1);
		assertEquals(rightNode2, dp.getEdgeTarget(edge1));
	}
	
	private DesignPattern createAdapterPattern() {
		Node client = new Clazz("Client", "Client");
		Node target = new Interface("Target", "Target");
		Node adapter = new Clazz("Adapter", "Adapter");
		Node adaptee = new Clazz("Adaptee", "Adaptee");
		
		DesignPattern dp = new DesignPattern("Adapter");
		dp.addVertex(client);
		dp.addVertex(target);
		dp.addVertex(adapter);
		dp.addVertex(adaptee);
		
		Edge clientTarget = dp.addEdge(client, target);
		clientTarget.setName("clientTarget");
		Edge adapterTarget = dp.addEdge(adapter, target);
		Edge adpaterAdaptee = dp.addEdge(adapter, adaptee);
		
		return dp;		
	}

}
