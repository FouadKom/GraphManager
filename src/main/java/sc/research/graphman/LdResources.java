/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.research.graphman;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.openrdf.model.URI;
import slib.graph.model.graph.G;
import slib.graph.model.repo.URIFactory;
import slib.graph.model.impl.repo.URIFactoryMemory;

/**
 *
 * @author LENOVO
 */
public class LdResources {
       public void addIngoingResources(G graph , String r , Integer level){
        
         RDFNode subject = null;
         RDFNode property = null;
         
         URI vSubject , vObject , eProperty;
         
         URIFactory factory = URIFactoryMemory.getSingleton();
         
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
                         
                        vSubject = factory.getURI(subject.toString());
                        eProperty = factory.getURI(property.toString());
                        vObject = factory.getURI(r);
                          
                        graph.addV(vSubject);
                        System.out.println("vertex " + vSubject + " added");
                        graph.addV(vObject);
                        System.out.println("vertex " + vObject + " added");
                        graph.addE(vSubject, eProperty ,vObject);
                        System.out.println("edge " + eProperty + " added");
                          
                        if(level > 0){
                            
                            addIngoingResources(graph , subject.toString() , level);
                           
                         }
                    
                    }
                    
                    
                }   

                finally {
                   qexec.close();
                }
         
      
        }
     
       public void addOutgoingResources(G graph ,String r , Integer level){

         RDFNode object = null;
         RDFNode property = null;
         URI vSubject , vObject , eProperty;
         
         URIFactory factory = URIFactoryMemory.getSingleton();
         
         String queryString = "select distinct ?property ?object\n" +
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
                         
                        object = (RDFNode)soln.get("object");
                        property = (RDFNode)soln.get("property");
                         
                        vSubject = factory.getURI(r);
                        eProperty = factory.getURI(property.toString());
                        vObject = factory.getURI(object.toString());
                          
                        graph.addV(vSubject);
                        System.out.println("vertex " + vSubject + " added");
                        graph.addV(vObject);
                        System.out.println("vertex " + vObject + " added");
                        graph.addE(vSubject, eProperty ,vObject);
                        System.out.println("edge " + eProperty + " added");
                         
                        if(level > 0){
                            
                            addOutgoingResources(graph , object.toString() , level);
                           
                        }
                    
                    }
                    
                    
                }   

                finally {
                   qexec.close();
                }
        }
    }