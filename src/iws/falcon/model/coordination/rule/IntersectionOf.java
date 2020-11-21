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
import com.hp.hpl.jena.vocabulary.RDFS;
import iws.falcon.model.coordination.Coordinator;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class IntersectionOf implements Coordinator
{
    /*
     * ( x, owl:intersectionOf, _:1 ), ( _:1, rdf:first, y ), ( _:1, rdf:rest,
     * _:2 ), ( _:2, rdf:first, z ), ( _:2, rdf:rest, rdf:nil ). -> ( x,
     * rdf:type, owl:Class ), ( x rdfs:subClassOf, y ), ( x, rdfs:subClassOf, z ).
     */

    public Model coordinate(Model model)
    {
        ArrayList removedStmt = new ArrayList();
        ArrayList addedStmt = new ArrayList();

        String querystr = " PREFIX owl: <http://www.w3.org/2002/07/owl#> "
                + " SELECT ?x ?y WHERE {?x owl:intersectionOf ?y}";

        Query query = QueryFactory.create(querystr);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        for (Iterator iter = results; iter.hasNext();) {
            QuerySolution res = (QuerySolution) iter.next();
            Resource x = (Resource) res.get("x");
            Resource y = (Resource) res.get("y");
            removedStmt.add(model.createStatement(x, OWL.intersectionOf, y));
            addedStmt.add(model.createStatement(x, RDF.type, OWL.Class));
            Resource rest = y;
            Resource first = null;
            Resource tempRest = y;
            boolean ended = false;
            while (!ended) {
                first = model.getProperty(rest, RDF.first).getResource();
                tempRest = model.getProperty(rest, RDF.rest).getResource();
                if (first == null || rest == null) {
                    ended = true;
                    continue;
                }
                removedStmt.add(model.createStatement(rest, RDF.first, first));
                removedStmt.add(model.createStatement(rest, RDF.rest, tempRest));
                if (!first.equals(RDF.nil)) {
                    addedStmt.add(model.createStatement(x, RDFS.subClassOf,
                            first));
                }
                rest = tempRest;
                if (rest.equals(RDF.nil)) {
                    ended = true;
                }
            }
        }

        for (int i = 0; i < removedStmt.size(); i++) {
            model.remove((Statement) removedStmt.get(i));
        }
        for (int i = 0; i < addedStmt.size(); i++) {
            model.add((Statement) addedStmt.get(i));
        }
        return model;
    }
}
