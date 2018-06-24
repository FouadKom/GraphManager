/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.research.graphman;

import java.util.Set;
import org.openrdf.model.URI;
import slib.graph.model.graph.G;
import slib.graph.model.graph.elements.E;
import slib.graph.model.impl.repo.URIFactoryMemory;
import slib.graph.model.repo.URIFactory;
import slib.sml.sm.core.engine.SM_Engine;
import slib.utils.ex.SLIB_Ex_Critic;

/**
 *
 * @author LENOVO
 */
public class Test {
    
    public static void main(String[] args) throws SLIB_Ex_Critic {
        
        LdResources resources = new LdResources();
        GraphManipulation graphMan = new GraphManipulation();
        
        G graph = graphMan.generateGraph("http://dbpedia.org/resource/");
        
        System.out.println("-------------------------ADDING OUTGOING RESOURCES WITH LEVEL 1-----------------------------------------");
        
        resources.addOutgoingResources(graph , "http://dbpedia.org/resource/Paris", 1);
        graphMan.showVerticesAndEdges(graph);
        
        System.out.println("-------------------------ADDING OUTGOING RESOURCES WITH LEVEL 2-----------------------------------------");
        
        resources.addOutgoingResources(graph , "http://dbpedia.org/resource/Paris", 2);
        graphMan.showVerticesAndEdges(graph);
        
        System.out.println("-------------------------ADDING INGOING RESOURCES WITH LEVEL 1-----------------------------------------");
        
        resources.addIngoingResources(graph , "http://dbpedia.org/resource/Paris", 1);
        graphMan.showVerticesAndEdges(graph);
        
        System.out.println("-------------------------ADDING INGOING RESOURCES WITH LEVEL 2-----------------------------------------");
        
        resources.addIngoingResources(graph , "http://dbpedia.org/resource/Paris", 2);
        graphMan.showVerticesAndEdges(graph);
       
        System.out.println("------------------------------------------------------------------");
        
        System.out.println("Ingoing edges for \"http://dbpedia.org/resource/Paris\"");
        Set<E> IngoingEdges = graphMan.getIngouingEdges(graph, "http://dbpedia.org/resource/Paris");
        System.out.println(IngoingEdges);
        
        System.out.println("------------------------------------------------------------------");
        
        System.out.println("Outgoing edges for \"http://dbpedia.org/resource/Paris\"");
        Set<E> outgoingEdges = graphMan.getOutgouingEdges(graph, "http://dbpedia.org/resource/Paris");
        System.out.println(outgoingEdges);
        
        System.out.println("------------------------------------------------------------------");
        
        graphMan.removeVertex(graph , "http://dbpedia.org/resource/Paris");
        graphMan.showVerticesAndEdges(graph);
        
        
        
        
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
