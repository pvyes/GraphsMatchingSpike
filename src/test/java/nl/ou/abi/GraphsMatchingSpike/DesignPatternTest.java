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
		//addVertex returns false if vertex exists already in the graph
		assertFalse(dp.addVertex(rightNode));
		
		//add a second vertex
		dp.addVertex(leftNode);
		assertEquals(2, dp.vertexSet().size());
		
		//addEdge
		Edge edge1 = dp.addEdge(leftNode, rightNode);
		edge1.setName("edge1");
		assertEquals(leftNode, dp.getEdgeSource(edge1));
		assertEquals(1, dp.edgeSet().size());
		
		//adding an existing edge returns null (multiple edges are not allowed)
		assertFalse(dp.isAllowingMultipleEdges());
		assertNull(dp.addEdge(leftNode, rightNode));
		
		//change vertices of an existing edge by removing the edge from the graph, adding the new vertex (if necessary)
		//and creating an edge in the graph with the existing edge as parameter
		dp.removeEdge(edge1);
		dp.addVertex(rightNode2);
		dp.addEdge(leftNode, rightNode2, edge1);
		assertEquals(rightNode2, dp.getEdgeTarget(edge1));
		
		//addEdge with an edge-parameter returns false if the Edge exists in the graph...
		assertFalse(dp.addEdge(rightNode2, leftNode, edge1));
		//... even if other vertices are involved (An existing Edge cannot change vertices, without removing the edge from the graph first)
		assertFalse(dp.addEdge(rightNode, leftNode, edge1));		
	}
	
	@Test
	public void DesignPatternAdapterTester() {
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
		Edge adapterAdaptee = dp.addEdge(adapter, adaptee);
		
		assertEquals(4, dp.vertexSet().size());
		assertEquals(3, dp.edgeSet().size());
		assertEquals(1, dp.edgesOf(client).size());
		assertEquals(2, dp.edgesOf(target).size());
		assertEquals(2, dp.edgesOf(adapter).size());
		assertEquals(1, dp.edgesOf(adaptee).size());
		
		assertEquals(adapter, dp.getEdgeSource(adapterAdaptee));
		assertEquals(adaptee, dp.getEdgeTarget(adapterAdaptee));
		assertEquals(adapter, dp.getEdgeSource(adapterTarget));
		assertEquals(target, dp.getEdgeTarget(adapterTarget));
		assertEquals(client, dp.getEdgeSource(clientTarget));
		assertEquals(target, dp.getEdgeTarget(clientTarget));
		
		assertEquals(1, dp.outgoingEdgesOf(client).size());
		assertEquals(0, dp.outgoingEdgesOf(target).size());
		assertEquals(2, dp.outgoingEdgesOf(adapter).size());
		assertEquals(0, dp.outgoingEdgesOf(adaptee).size());
		
		assertEquals(0, dp.incomingEdgesOf(client).size());
		assertEquals(2, dp.incomingEdgesOf(target).size());
		assertEquals(0, dp.incomingEdgesOf(adapter).size());
		assertEquals(1, dp.incomingEdgesOf(adaptee).size());			
	}


}
