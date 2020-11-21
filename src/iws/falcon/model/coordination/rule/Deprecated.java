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
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import iws.falcon.model.coordination.Coordinator;
import java.util.ArrayList;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class Deprecated implements Coordinator
{
    // owl:DeprecatedClass, owl:DeprecatedProperty
    public Model coordinate(Model model)
    {
        ArrayList removedStmts = new ArrayList();
        ArrayList addedStmts = new ArrayList();

        StmtIterator deprecatedClass = model.listStatements((Resource) null,
                RDF.type, OWL.DeprecatedClass);
        while (deprecatedClass.hasNext()) {
            Statement stmt = deprecatedClass.nextStatement();
            removedStmts.add(stmt);
            addedStmts.add(model.createStatement(stmt.getSubject(), RDF.type,
                    OWL.Class));
        }
        StmtIterator deprecatedProperty = model.listStatements((Resource) null,
                RDF.type, OWL.DeprecatedProperty);
        while (deprecatedProperty.hasNext()) {
            Statement stmt = deprecatedProperty.nextStatement();
            removedStmts.add(stmt);
            addedStmts.add(model.createStatement(stmt.getSubject(), RDF.type,
                    OWL.ObjectProperty));
        }

        for (int i = 0; i < removedStmts.size(); i++) {
            model.remove((Statement) removedStmts.get(i));
        }
        for (int i = 0; i < addedStmts.size(); i++) {
            model.add((Statement) addedStmts.get(i));
        }
        return model;
    }
}
