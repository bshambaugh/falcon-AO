/**
 * Copyright 2008 Institute of Web Science, Southeast University, PR China
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iws.falcon.model.coordination.rule;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import iws.falcon.model.coordination.Coordinator;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class AllDifferent implements Coordinator
{
    public Model coordinate(Model model)
    {
        ArrayList removedStmt = new ArrayList();

        String querystr = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " 
                + " PREFIX owl: <http://www.w3.org/2002/07/owl#AllDifferent> " 
                + " SELECT ?x ?y ?z WHERE { ?x rdf:type owl:AllDifferent. "
                + " ?x owl:distinctMembers ?y. ?y rdf:first ?z. } ";

        Query query = QueryFactory.create(querystr);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        for (Iterator iter = results; iter.hasNext();) {
            QuerySolution res = (QuerySolution) iter.next();
            Resource x = (Resource) res.get("x");
            Resource y = (Resource) res.get("y");
            Resource z = (Resource) res.get("z");
            removedStmt.add(model.createStatement(x, RDF.type, OWL.AllDifferent));
            removedStmt.add(model.createStatement(x, OWL.distinctMembers, y));
            removedStmt.add(model.createStatement(y, RDF.first, z));
            Resource tempSubject = y;
            Resource tempObject = z;
            boolean end = false;
            while (!end) {
                tempObject = model.getProperty(tempSubject, RDF.rest).getResource();
                removedStmt.add(model.createStatement(tempSubject, RDF.rest,
                        tempObject));
                if (tempObject.toString().equals(RDF.nil.toString())) {
                    end = true;
                } else {
                    tempSubject = tempObject;
                    tempObject = model.getProperty(tempSubject, RDF.first).getResource();
                    removedStmt.add(model.createStatement(tempSubject,
                            RDF.first, tempObject));
                }
            }
        }

        for (int i = 0; i < removedStmt.size(); i++) {
            model.remove((Statement) removedStmt.get(i));
        }
        return model;
    }
}
