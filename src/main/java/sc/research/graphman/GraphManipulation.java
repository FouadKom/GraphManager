/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.research.graphman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.openrdf.model.URI;
import slib.graph.model.graph.G;
import slib.graph.model.graph.elements.E;
import slib.graph.model.graph.utils.Direction;
import slib.graph.model.impl.graph.elements.Edge;
import slib.graph.model.impl.graph.memory.GraphMemory;
import slib.graph.model.impl.repo.URIFactoryMemory;
import slib.graph.model.repo.URIFactory;
import slib.sml.sm.core.engine.SM_Engine;
import slib.utils.ex.SLIB_Ex_Critic;
import slib.utils.ex.SLIB_Exception;

/**
 *
 * @author LENOVO
 */
public class GraphManipulation {
     public static void main(String[] args) throws SLIB_Ex_Critic {

    String triple;
    
    URIFactory factory = URIFactoryMemory.getSingleton();
    URI uriGraph = factory.getURI("http://dbpedia.org/resource/"); 
    G graph = new GraphMemory(uriGraph);

    Set<String> Ingoing_resource = new HashSet<String>();
    
    URI vSubject,vObject , eProperty;
     
    Ingoing_resource = getIngoingResources("http://dbpedia.org/resource/Paris" , 1);
    
//    addTriplesToDataset(Ingoing_resource ,  "C:\\Users\\LENOVO\\Documents\\NetBeansProjects\\GraphManipulation\\src\\Resources\\data.rdf");
    
    System.out.println("- Empty Graph");
    System.out.println(graph.toString());
    
     Iterator iter = Ingoing_resource.iterator();
     
                while(iter.hasNext()) {
                    triple = iter.next().toString();
                   
                    vSubject = factory.getURI(getSubject(triple));
                    eProperty = factory.getURI(getProperty(triple));
                    vObject = factory.getURI(getObject(triple));
                    
                    graph.addV(vSubject);
                    System.out.println("vertex " + getSubject(triple) + " added");
                    graph.addV(vObject);
                    System.out.println("vertex " + getObject(triple) + " added");
                    graph.addE(vSubject, eProperty ,vObject);
                    System.out.println("edge " + getProperty(triple) + " added");
                    
                    
                }
                
                System.out.println("- Graph, edges added");
                System.out.println(graph.toString());
     
    }
    
    public static Set<String> getIngoingResources(String r , Integer level){
         Set<String> Ingoing_resource = new HashSet();
         RDFNode subject = null;
         RDFNode property = null;
         
         String queryString = "select distinct ?subject ?property \n" +
                              "where \n" +
                              "{?subject ?property <" + r + ">.\n" +
                              "FILTER(ISURI(?subject))} \n" +
                              "LIMIT 1";


                Query query = QueryFactory.create(queryString);

                QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
                
                level = level - 1; 
                try {                                                                                                                                                                                                                                       
                    ResultSet results = qexec.execSelect();
                    for (; results.hasNext();) {

                         QuerySolution soln = results.nextSolution() ;
                         
                         subject = (RDFNode)soln.get("subject");
                         property = (RDFNode)soln.get("property");
                          
                         Ingoing_resource.add(soln.toString() + "( ?object = <" + r + "> )");
                         
                         
                         if(level >= 0){
                            
                            Ingoing_resource.addAll(getIngoingResources(subject.toString() , level));
                           
                         }
                    
                    }
                    
                    
                }   

                finally {
                   qexec.close();
                }
         
        return Ingoing_resource;
     }
     
       public static Set<String> getOutgoingResources(String r , Integer level){
         Set<String> Outgoing_resource = new HashSet();
         RDFNode subject = null;
         RDFNode property = null;
         
         String queryString = "select distinct ?object ?property\n" +
                              "where \n" +
                              "{<" + r + "> ?property ?object.\n" +
                              "FILTER(ISURI(?object))} \n"+
                              "LIMIT 1";


                Query query = QueryFactory.create(queryString);

                QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
                
                level = level - 1; 
                try {                                                                                                                                                                                                                                       
                    ResultSet results = qexec.execSelect();
                    for (; results.hasNext();) {

                         QuerySolution soln = results.nextSolution() ;
                         
                         subject = (RDFNode)soln.get("object");
                         property = (RDFNode)soln.get("property");
                          
                         Outgoing_resource.add("( ?subject = <" + r + "> )" + soln.toString() + "level: " + level);
                         
                         
                         if(level >= 0){
                            
                            Outgoing_resource.addAll(getOutgoingResources(subject.toString() , level));
                           
                         }
                    
                    }
                    
                    
                }   

                finally {
                   qexec.close();
                }
         
        return Outgoing_resource;
     }
       
      public static void addTriplesToDataset(Set<String> triples ,  String dirctory){
                
                String subject,
                       property,
                       object,
                       triple;
                
                Model model = ModelFactory.createDefaultModel();
                File file = new File(dirctory);
                if(file.exists()){
                    
                   InputStream in = FileManager.get().open(dirctory);
                   model.read(in, "");
                    
                    
                }
                
                Iterator iter = triples.iterator();
                while (iter.hasNext()) {
                    triple = iter.next().toString();
                    
                    subject = getSubject(triple);
                    property = getProperty(triple);
                    object = getObject(triple);
                    
                    Resource rcs = model.getResource(subject);
                    rcs.addProperty(model.getProperty(property), object);
            
                }

                try{
                  FileOutputStream fout=new FileOutputStream(dirctory);
                  model.write(fout , "RDF/XML");
                  }

                  catch (Exception e)
                  {
                        System.out.println("Failed: " + e);
                  }
        }
      
      public static String getSubject(String triple){
          return triple.substring(triple.indexOf("?subject", 0) + 11 , triple.indexOf("?property", 0)-5).replace("<", "").replace(">", "");
      }
      
      public static String getProperty(String triple){
          return triple.substring(triple.indexOf("?property", 0) + 12 , triple.indexOf("?object", 0)-4).replace("<", "").replace(">", "");
      }
      
      public static String getObject(String triple){
          return triple.substring(triple.indexOf("?object", 0) + 10 , triple.length() - 2).replace("<", "").replace(">", "");
      }
    
}
