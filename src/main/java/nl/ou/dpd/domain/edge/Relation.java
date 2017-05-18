package nl.ou.dpd.domain.edge;


import nl.ou.dpd.domain.node.Node;
import org.jgrapht.graph.DefaultEdge;

import java.util.Objects;

public class Relation extends DefaultEdge {

    private String id;
    private String name;
    private RelationType relationType;
    private boolean selfRef, locked, virtual;
    private Cardinality cardinalityLeft; //cardinality on leftNode
    private Cardinality cardinalityRight; //cardinality on rightNode

    public Relation(Node leftNode, Node rightNode, RelationType type) {
        this.relationType = type;
        this.selfRef = leftNode.equals(rightNode);
        this.locked = false;
        this.virtual = false;
        this.cardinalityLeft = null;
        this.cardinalityRight = null;
    }

    public Relation(Node leftNode, Node rightNode, RelationType type, String name) {
        this(leftNode, rightNode, type);
        this.name = name;
    }

    public Relation(String id, String name) {
        this.id = id;
        this.name = name;
    }

//    public Relation duplicate() {
//    	Relation duplicate = new Relation(this.getId(), this.getName());
//        duplicate.setRelationType(this.getRelationType());
//        duplicate.cardinalityLeft = this.cardinalityLeft;
//        duplicate.cardinalityRight = this.cardinalityRight;
//        duplicate.locked = this.locked;
//        duplicate.virtual = this.virtual;
//        return duplicate;
//    }
//
//    public void makeVirtual() {
//        virtual = true;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    } 

//    public boolean isVirtual() {
//        return virtual;
//    }
//
//    public boolean lock() {
//        this.locked = true;
//        return isLocked();
//    }
//
//    public boolean unlock() {
//        this.locked = false;
//        return !isLocked();
//    }
//
//    public boolean isLocked() {
//        return this.locked;
//    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public boolean isSelfRef() {
        return selfRef;
    }

    public void setCardinalityLeft(Cardinality cardinality) {
        this.cardinalityLeft = cardinality;
    }

    public void setCardinalityRight(Cardinality cardinality) {
        this.cardinalityRight = cardinality;
    }

    public Cardinality getCardinalityLeft() {
        return cardinalityLeft;
    }

    public Cardinality getCardinalityRight() {
        return cardinalityRight;
    }

    public void removeCardinalityLeft() {
        cardinalityLeft = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return selfRef == relation.selfRef &&
                locked == relation.locked &&
                virtual == relation.virtual &&
                Objects.equals(id, relation.id) &&
                Objects.equals(name, relation.name) &&
                relationType == relation.relationType &&
                Objects.equals(cardinalityLeft, relation.cardinalityLeft) &&
                Objects.equals(cardinalityRight, relation.cardinalityRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, relationType, selfRef, locked, virtual, cardinalityLeft, cardinalityRight);
    }
}

