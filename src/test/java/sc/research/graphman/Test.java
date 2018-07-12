/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.research.graphman;

import java.io.IOException;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import slib.graph.model.graph.G;
import slib.utils.ex.SLIB_Ex_Critic;

/**
 *
 * @author LENOVO
 */
public class Test {
    
    
    
    public static void main(String[] args) throws SLIB_Ex_Critic, IOException {
        
        String tdbDir = System.getProperty("user.dir") + "/src/main/resources/tdb";
        String dataSetDir = System.getProperty("user.dir") + "/src/main/resources/data.rdf";
        
        Dataset dataset = TDBFactory.createDataset(tdbDir);
        Model model = dataset.getDefaultModel();
        
        CreateFile file = new CreateFile(dataSetDir);
        file.writeToFile();
        
        LdResources resources = new LdResources();
        GraphManager graphMan = new GraphManager();
        
        G graph = graphMan.generateGraph("http://dbpedia.org/resource/");
        G path = graphMan.generateGraph("http://dbpedia.org/path/");
        
        FileManager.get().readModel(model , dataSetDir);
        dataset.begin(ReadWrite.READ) ;
        
        System.out.println("geting ingoing resources for Mammel with level 4"); //I choose level 4 to check if the resource will not be traversed
        resources.addIngoingResources(graph , "http://www.example.org#Mammal" , 4 , dataset);
        graphMan.showVerticesAndEdges(graph);
        System.out.println("------------------------------------------------------------------");
        
        //clearing the shared list that should hold the traversed resources in between methods
        resources.clear();
      
        System.out.println("geting outgoing resources for fish with level 4");//I choose level 4 to check if the resource will not be traversed
        resources.addOutgoingResources(graph ,  "http://www.example.org#Fish" , 4 , dataset);
        graphMan.showVerticesAndEdges(graph);
        System.out.println("------------------------------------------------------------------"); 

        resources.clear();
   
        resources.getConnectingPath(path , "http://www.example.org#Bear" , "http://www.example.org#Mammal" , 3 , dataset);
        graphMan.showVerticesAndEdges(path);
        

        
        
//        System.out.println("-------------------------ADDING OUTGOING RESOURCES WITH LEVEL 1-----------------------------------------");
//        
//        resources.addOutgoingResources(graph , "http://dbpedia.org/resource/Paris", 3);
//        graphMan.showVerticesAndEdges(graph);
//        
//        System.out.println("-------------------------ADDING OUTGOING RESOURCES WITH LEVEL 2-----------------------------------------");
//        
//        resources.addOutgoingResources(graph , "http://dbpedia.org/resource/Paris", 2);
//        graphMan.showVerticesAndEdges(graph);
//        
//        System.out.println("-------------------------ADDING INGOING RESOURCES WITH LEVEL 1-----------------------------------------");
//        
//        resources.addIngoingResources(graph , "http://dbpedia.org/resource/Paris", 1);
//        graphMan.showVerticesAndEdges(graph);
//        
//        System.out.println("-------------------------ADDING INGOING RESOURCES WITH LEVEL 2-----------------------------------------");
        
//        resources.addIngoingResources(graph , "http://dbpedia.org/resource/Paris", 3);
//        graphMan.showVerticesAndEdges(graph);
//       
//        System.out.println("------------------------------------------------------------------");
//        
//        System.out.println("Ingoing edges for \"http://dbpedia.org/resource/Paris\"");
//        Set<E> IngoingEdges = graphMan.getIngouingEdges(graph, "http://dbpedia.org/resource/Paris");
//        System.out.println(IngoingEdges);
//        
//        System.out.println("------------------------------------------------------------------");
//        
//        System.out.println("Outgoing edges for \"http://dbpedia.org/resource/Paris\"");
//        Set<E> outgoingEdges = graphMan.getOutgouingEdges(graph, "http://dbpedia.org/resource/Paris");
//        System.out.println(outgoingEdges);
//        
//        System.out.println("------------------------------------------------------------------");
//        
//        graphMan.removeVertex(graph , "http://dbpedia.org/resource/Paris");
//        graphMan.showVerticesAndEdges(graph);
        
        
        
        
//        URIFactory factory = URIFactoryMemory.getSingleton();
//        URI vertex = factory.getURI("http://dbpedia.org/resource/Paris");
//        
//        SM_Engine engine = new SM_Engine(graph);
//        Set<URI> ancestors = engine.getAncestorsInc(vertex);
//        Set<URI> descendants = engine.getDescendantsInc(vertex);
//        
//        System.out.println("Ancestors :   " + ancestors);
//        System.out.println("Descendants : " + descendants);
    }
    
}
