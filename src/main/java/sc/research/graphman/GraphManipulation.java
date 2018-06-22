/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.research.graphman;

import java.util.Iterator;
import slib.utils.ex.SLIB_Ex_Critic;
import java.util.Set;
import org.openrdf.model.URI;
import slib.graph.model.graph.G;
import slib.graph.model.graph.elements.E;
import slib.graph.model.graph.utils.Direction;
import slib.graph.model.impl.graph.memory.GraphMemory;
import slib.graph.model.impl.repo.URIFactoryMemory;
import slib.graph.model.repo.URIFactory;

/**
 *
 * @author LENOVO
 */
public class GraphManipulation {
    
    
    public Set<E> getIngouingEdges(G graph , URI vertex){
        
        return graph.getE(vertex ,Direction.IN); 
        
    }
    
    public Set<E> getIngouingEdges(G graph , URI vertex , URI predicate){
        
        return graph.getE(predicate , vertex ,Direction.IN); 
        
    }
    
     public Set<E> getOutgouingEdges(G graph , URI vertex){
        
        return graph.getE(vertex ,Direction.OUT); 
        
    }
     
     public Set<E> getOutgouingEdges(G graph , URI vertex , URI predicate){
        
        return graph.getE(predicate , vertex ,Direction.OUT); 
        
    }
     
     public Set<E> getEdges(G graph , URI vertex){
        
        return graph.getE(vertex ,Direction.BOTH); 
        
    }
     
    public Set<E> getEdges(G graph , URI vertex , URI predicate){
        
        return graph.getE(predicate, vertex, Direction.BOTH);
        
    }
    
    
     public G generateGraph(Set<String> triples, String GraphURI) throws SLIB_Ex_Critic {

            String triple;

            URIFactory factory = URIFactoryMemory.getSingleton();
            URI uriGraph = factory.getURI(GraphURI); 
            G graph = new GraphMemory(uriGraph);

            URI vSubject,vObject , eProperty;

            System.out.println("- Empty Graph");
            showVerticesAndEdges(graph);

             Iterator iter = triples.iterator();

                        while(iter.hasNext()) {
                            triple = iter.next().toString();

                            vSubject = factory.getURI(LdResources.getSubject(triple));
                            eProperty = factory.getURI(LdResources.getProperty(triple));
                            vObject = factory.getURI(LdResources.getObject(triple));

                            graph.addV(vSubject);
                            System.out.println("vertex " + vSubject + " added");
                            graph.addV(vObject);
                            System.out.println("vertex " + vObject + " added");
                            graph.addE(vSubject, eProperty ,vObject);
                            System.out.println("edge " + eProperty + " added");


                        }

                        System.out.println("- Graph, edges added");
                        showVerticesAndEdges(graph);

                        return graph;

            }
     
          private static void showVerticesAndEdges(G graph) {


            Set<URI> vertices = graph.getV();
            Set<E> edges = graph.getE();

            System.out.println("-Vertices");
            for(URI v : vertices){
                System.out.println("\t"+v);
            }

            System.out.println("-Edge");
            for(E edge : edges){
                System.out.println("\t"+edge);
            }
    }

 
    
}
