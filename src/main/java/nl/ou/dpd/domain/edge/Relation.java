package nl.ou.dpd.domain.edge;


import nl.ou.dpd.domain.node.Node;
import org.jgrapht.graph.DefaultEdge;

import java.util.Objects;

public class Relation extends DefaultEdge {

    private String id;
    private String name;
    private RelationType relationType;
    private boolean locked, virtual;
    private Cardinality cardinalityLeft; //cardinality on leftNode
    private Cardinality cardinalityRight; //cardinality on rightNode

    public Relation(RelationType type) {
        this.relationType = type;
        this.locked = false;
        this.virtual = false;
        this.cardinalityLeft = null;
        this.cardinalityRight = null;
    }

    public Relation(Node leftNode, Node rightNode, RelationType type, String name) {
        this(type);
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

    public Relation setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public Relation setId(String id) {
        this.id = id;
        return this;
    } 

    public RelationType getRelationType() {
        return relationType;
    }

    public Relation setRelationType(RelationType relationType) {
        this.relationType = relationType;
        return this;
    }

    public Relation setCardinalityLeft(Cardinality cardinality) {
        this.cardinalityLeft = cardinality;
        return this;
    }

    public Relation setCardinalityRight(Cardinality cardinality) {
        this.cardinalityRight = cardinality;
        return this;
    }

    public Cardinality getCardinalityLeft() {
        return cardinalityLeft;
    }

    public Cardinality getCardinalityRight() {
        return cardinalityRight;
    }

    public Relation removeCardinalityLeft() {
        cardinalityLeft = null;
        return this;
    }

    public Relation removeCardinalityRight() {
        cardinalityRight = null;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return locked == relation.locked &&
                virtual == relation.virtual &&
                Objects.equals(id, relation.id) &&
                Objects.equals(name, relation.name) &&
                relationType == relation.relationType &&
                Objects.equals(cardinalityLeft, relation.cardinalityLeft) &&
                Objects.equals(cardinalityRight, relation.cardinalityRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, relationType, locked, virtual, cardinalityLeft, cardinalityRight);
    }
}

