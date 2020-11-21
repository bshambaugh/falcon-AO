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

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import iws.falcon.model.Constants;
import iws.falcon.model.coordination.Coordinator;
import java.util.ArrayList;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class Redefinition implements Coordinator
{
    public Model coordinate(Model model)
    {
        ArrayList removedStmt = new ArrayList();

        StmtIterator stmts = model.listStatements();
        while (stmts.hasNext()) {
            Statement stmt = stmts.nextStatement();
            Resource subject = stmt.getSubject();
            if (Constants.RDF_NS.equals(subject.getNameSpace()) 
                    || Constants.RDFS_NS.equals(subject.getNameSpace()) 
                    || Constants.OWL_NS.equals(subject.getNameSpace())) {
                removedStmt.add(stmt);
            }
        }

        for (int i = 0; i < removedStmt.size(); i++) {
            model.remove((Statement) removedStmt.get(i));
        }
        return model;
    }
}
