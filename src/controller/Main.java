package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import model.AddDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main extends Application{


    public static int sceneWidth = 1750;
    public static int sceneHeight = 1000;
    public static Scene currScene;
    public static Stage currStage;
    public static Parent parentRoot;
    public static NodeObj kiosk;
    public static ListOfNodeObjs nodeMap;
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static JanitorService janitorService;


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        janitorService = new JanitorService();
        File test = new File("mapDB");
        deleteDir(test);
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
        Statement statement = connection.createStatement();
        try {
            CreateDB.run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ReadCSV.runNode("src/model/docs/Nodes.csv");
            ReadCSV.runEdge("src/model/docs/Edges.csv");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        String tablename = "nodeTable";
        statement.executeQuery("SELECT * FROM " + tablename);

        // creates and saves the list of nodes for a map
        ArrayList<Node> listOfNodes = new ArrayList<Node>();
        listOfNodes = QueryDB.getNodes();

        // create a list of all the node objects for a map
        ArrayList<NodeObj> loNodeObj = new ArrayList<NodeObj>();
        for (Node n:listOfNodes) {
            loNodeObj.add(new NodeObj(n));
        }

        nodeMap = new ListOfNodeObjs(loNodeObj);

        // creates and saves the list of edges for a map
        ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
        listOfEdges = QueryDB.getEdges();

        // create edge objects
        for(Edge edge:listOfEdges){
            EdgeObj newObj = new EdgeObj(edge.getNodeAID(), edge.getNodeBID(), edge.getEdgeID());
            if(nodeMap.pair(newObj)){
                newObj.setWeight(newObj.genWeightFromDistance());
            }
        }
        //get the kiosk for the assigned floor
        try {
            kiosk = nodeMap.getNearestNeighborFilter(2460, 910);
        }catch(InvalidNodeException e){
            e.printStackTrace();
        }

        System.out.println("Default x: " + kiosk.node.getxLoc() + " Default y: " + kiosk.node.getyLoc());
        //keep this at the end
        //launches fx file and allows for pathfinding to be done
        //What Works: All Nodes are added from the CSV files
        //All Edges are added from the CSV files
        //All Weights Have Been Computed for All Nodes
        //getDistToGoal has been removed and replaced with NodeObj.getDistance(goal)
        javafx.application.Application.launch(args);
    }


    //taken from https://stackoverflow.com/questions/12835285/create-directory-if-exists-delete-directory-and-its-content-and-create-new-one
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.currStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../view/ui/UI_v1.fxml"));
        this.parentRoot = root;
        primaryStage.setTitle("Map");
        Scene newScene = new Scene(root, sceneWidth, sceneHeight);
        this.currScene=newScene;
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    @Override
    public void stop() throws SQLException {
        for(NodeObj n : nodeMap.getNodes()){
            for(EdgeObj e : n.getListOfEdgeObjs()){
                AddDB.addEdge(e.objToEntity());
            }
            AddDB.addNode(n.getNode());
        }
        try {
            WriteNodes.runNodes();
            WriteEdges.runEdges();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static NodeObj getKiosk() {
        return kiosk;
    }

    public static ListOfNodeObjs getNodeMap() {
        return nodeMap;
    }

    public static Scene getCurrScene() {
        return currScene;
    }

    public static Stage getCurrStage() {
        return currStage;
    }

    public static Parent getParentRoot() {
        return parentRoot;
    }

    public static void setKiosk(NodeObj kiosk) {
        Main.kiosk = kiosk;
    }

    public static JanitorService getJanitorService() {
        return janitorService;
    }

    public static void setJanitorService(JanitorService janitorService) {
        Main.janitorService = janitorService;
    }
}