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

/**
 *
 * @author LENOVO
 */
public class LdResources {
       public Set<String> getIngoingResources(String r , Integer level){
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
     
       public Set<String> getOutgoingResources(String r , Integer level){
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
       
      public void addTriplesToDataset(Set<String> triples ,  String dirctory){
                
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
