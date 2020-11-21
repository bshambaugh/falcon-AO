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
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import iws.falcon.model.Constants;
import iws.falcon.model.coordination.Coordinator;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class Annotation implements Coordinator
{
    private ArrayList annoProperties = new ArrayList(5);

    public Annotation()
    {
        annoProperties.add(RDFS.comment);
        annoProperties.add(RDFS.label);
        annoProperties.add(RDFS.seeAlso);
        annoProperties.add(RDFS.isDefinedBy);
        annoProperties.add(OWL.versionInfo);
    }

    private void addAnnoProperty(Property p)
    {
        annoProperties.add(p);
    }

    private boolean isRemovedAnnoProperty(Property p)
    {
        if (p.toString().equals(RDFS.label.toString()) 
                || p.toString().equals(RDFS.comment.toString())) {
            return false;
        }
        if (p.getNameSpace().equals(Constants.DC_NS)) {
            return true;
        }
        for (int i = 0; i < annoProperties.size(); i++) {
            if (annoProperties.get(i).toString().equals(p.toString())) {
                return true;
            }
        }
        return false;
    }

    public Model coordinate(Model model)
    {
        Annotation anno = new Annotation();
        ArrayList removedStmt = new ArrayList();

        String querystr = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + " PREFIX owl: <http://www.w3.org/2002/07/owl#> " 
                + " SELECT ?x WHERE {?x rdf:type owl:AnnotationProperty} ";

        Query query = QueryFactory.create(querystr);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        for (Iterator iter = results; iter.hasNext();) {
            QuerySolution res = (QuerySolution) iter.next();
            Resource x = (Resource) res.get("x");
            removedStmt.add(model.createStatement(x, RDF.type,
                    OWL.AnnotationProperty));
            anno.addAnnoProperty(model.getProperty(x.toString()));
        }
        StmtIterator stmts = model.listStatements();
        while (stmts.hasNext()) {
            Statement s = (Statement) stmts.next();
            Property p = s.getPredicate();
            if (anno.isRemovedAnnoProperty(p)) {
                removedStmt.add(s);
            }
        }

        for (int i = 0; i < removedStmt.size(); i++) {
            model.remove((Statement) removedStmt.get(i));
        }
        return model;
    }
}
