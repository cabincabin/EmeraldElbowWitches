package model;

import java.util.ArrayList;

public class ListOfNodeObjs {

    private ArrayList<NodeObj> nodes;
    public String currentBuilding;

    public ListOfNodeObjs(ArrayList<NodeObj> nodes) {
        this.nodes = nodes;
        this.currentBuilding = "45 Francis";
    }

    // ---------------------Getters----------------------------


    public ArrayList<NodeObj> getNodes() {
        return this.nodes;
    }

    public Double getDistance(NodeObj nodeA, NodeObj nodeB) {
        return nodeA.getDistance(nodeB);
    }

    public NodeObj getNearestNeighborFilter(int xLoc, int yLoc)throws InvalidNodeException{
        double minDist = Double.MAX_VALUE;
        NodeObj closestNode = null;
        for (NodeObj n: nodes){
            if((n.getDistance(xLoc, yLoc)<minDist) && (n.getNode().getBuilding().equals(this.currentBuilding))){
                closestNode = n;
                minDist = n.getDistance(xLoc, yLoc);
            }
        }
        if(closestNode == null)
            throw new InvalidNodeException("no corresponding edge");
        return closestNode;
    }


    public NodeObj getNearestNeighbor(int xLoc, int yLoc)throws InvalidNodeException{
        double minDist = Double.MAX_VALUE;
        NodeObj closestNode = null;
        for (NodeObj n: nodes){
            if((n.getDistance(xLoc, yLoc)<minDist)){
                closestNode = n;
                minDist = n.getDistance(xLoc, yLoc);
            }
        }
        if(closestNode == null)
            throw new InvalidNodeException("no corresponding edge");
        return closestNode;
    }

    public ArrayList<NodeObj> getFilteredNodes(){
        ArrayList<NodeObj> filteredNodes = new ArrayList<NodeObj>();
        for (NodeObj n : this.nodes){
            if(n.getNode().getBuilding().equals(this.currentBuilding)){
                filteredNodes.add(n);
            }
        }
        return filteredNodes;
    }

    // ---------------------Setters----------------------------
    public void setEdgeWeight(NodeObj nodeA, NodeObj nodeB, int edgeWeight) {

    }

    public void setCurrentBuilding(String currentBuilding) {
        this.currentBuilding = currentBuilding;
    }

    // ---------------------General Functionality--------------

    public boolean pair(EdgeObj newEdge){
        int flag = 0;
        for(NodeObj n: this.nodes){
            if(n.node.getNodeID().equals(newEdge.nodeAStr)){
                n.addEdge(newEdge);
                newEdge.setNodeA(n);
                flag++;
            }else if(n.node.getNodeID().equals(newEdge.nodeBStr)){
                n.addEdge(newEdge);
                newEdge.setNodeB(n);
                flag++;
            }
        }
        if(flag<2){
            return false;
        }else{
            return true;
        }
    }

    //removeNode takes in a node to add, and a list of node objs to modify, if the node is in the list remove it. If the node is not there, do nothing
    public void removeNode(NodeObj nodeToDelete){
        NodeObj actualDeleteNode = null;
        for (NodeObj nodes: this.nodes) {
            if(nodes.getNode().getNodeID().equals(nodeToDelete.getNode().getNodeID())){
                actualDeleteNode = nodes;
                System.out.println("found node to delete");
                //TODO:delete all edges attached to this NodeObj. Delete edges then nodes
            }
        }
        this.nodes.remove(actualDeleteNode);
    }

    //addNode takes in a node to add, and a list of node objs to modify, if the node is already present, do nothing
    public void addEditNode(NodeObj nodeToAdd){
        boolean alreadyExists = false;
        for(NodeObj nodes : this.nodes){
            if(nodes.getNode().getNodeID().equals(nodeToAdd.getNode().getNodeID())){
                alreadyExists = true;
                nodes.setNode(nodeToAdd.getNode());
            }
        }
        if(!alreadyExists) {
            this.nodes.add(nodeToAdd);
        }
    }

//Will need to deal with each edge


}
