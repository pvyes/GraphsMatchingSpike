package nl.ou.abi.GraphsMatchingSpike;

import org.jgrapht.EdgeFactory;

import nl.ou.dpd.domain.edge.Edge;
import nl.ou.dpd.domain.node.Node;

/**
 * @author Peter Vansweevelt
 *
 */
public class EdgeOfNodesFactory implements EdgeFactory<Node, Edge> {
	
	/* 
	 * Creates a new directed Edge without id and with a default name.
	 * @param leftNode the sourceNode
	 * @param rightNode the targetNode
	 * @return a new directed Edge from leftNode to RightNode
	 */
	@Override
	public Edge createEdge(Node leftNode, Node rightNode) {
		return new Edge(null, "newEdge");
	}

}
