package nl.ou.abi.GraphsMatchingSpike;

import nl.ou.dpd.domain.edge.Relation;
import nl.ou.dpd.domain.node.Node;
import org.jgrapht.EdgeFactory;

public class RelationFactory implements EdgeFactory<Node, Relation> {

    public Relation createEdge(Node leftNode, Node rightNode) {
        final Relation relation = new Relation(null, null);
        relation.setLeftNode(leftNode);
        relation.setRightNode(rightNode);
        return relation;
    }

    public Relation createEdge(Node leftNode, Node rightNode, String id, String name) {
        final Relation relation = this.createEdge(leftNode, rightNode);
        relation.setId(id);
        relation.setName(name);
        return relation;
    }

}
