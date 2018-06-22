/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.research.graphman;

import java.util.HashSet;
import java.util.Set;
import slib.graph.model.graph.G;
import slib.utils.ex.SLIB_Ex_Critic;

/**
 *
 * @author LENOVO
 */
public class Test {
    public static void main(String[] args) throws SLIB_Ex_Critic{
        LdResources resources = new LdResources();
        GraphManipulation graphMan = new GraphManipulation();
        G graph;
        
        Set<String> Ingoing_resources = new HashSet<String>(),
                    Outgoing_resources = new HashSet<String>(),
                    All_resources = new HashSet<String>();
        
        Ingoing_resources = resources.getIngoingResources("http://dbpedia.org/resource/Paris", 1);
        Outgoing_resources = resources.getOutgoingResources("http://dbpedia.org/resource/Paris" , 1);
        
        All_resources.addAll(Ingoing_resources);
        All_resources.addAll(Outgoing_resources);
        
        graph = graphMan.generateGraph(All_resources, "http://dbpedia.org/resource/");

        
        
       
    }
    
}
