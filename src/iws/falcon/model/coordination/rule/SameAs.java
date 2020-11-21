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
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class SameAs extends Equivalent
{
    private ArrayList allSameAs = null;
    private int index = 0;

    public void addSameAs(Object a, Object b)
    {
        SameNode aNode = null;
        SameNode bNode = null;

        if (allValues.containsKey(a)) {
            aNode = (SameNode) allValues.get(a);
        } else {
            aNode = new SameNode(a);
            allValues.put((Resource) a, (SameNode) aNode);
        }
        if (allValues.containsKey(b)) {
            bNode = (SameNode) allValues.get(b);
        } else {
            bNode = new SameNode(b);
            allValues.put((Resource) b, (SameNode) bNode);
        }

        if (aNode.setNum < bNode.setNum) {
            if (bNode.setNum == SameNode.MaxInteger) {
                bNode.setNum = aNode.setNum;
                return;
            } else {
                setAllSame(bNode.setNum, aNode.setNum);
            }
        } else if (bNode.setNum < aNode.setNum) {
            if (aNode.setNum == SameNode.MaxInteger) {
                aNode.setNum = bNode.setNum;
                return;
            } else {
                setAllSame(aNode.setNum, bNode.setNum);
            }
        } else if (aNode.setNum == SameNode.MaxInteger) {
            index++;
            aNode.setNum = index;
            bNode.setNum = index;
        }

    }

    private void setAllSame(int oldNum, int newNum)
    {
        Iterator nodeList = allValues.values().iterator();
        while (nodeList.hasNext()) {
            SameNode node = (SameNode) nodeList.next();
            if (node.setNum == oldNum) {
                node.setNum = newNum;
            }
        }
    }

    public ArrayList getAllSameAs()
    {
        if (allSameAs == null) {
            allSameAs = new ArrayList();
            Iterator nodeIter = allValues.values().iterator();
            while (nodeIter.hasNext()) {
                allSameAs.add((SameNode) nodeIter.next());
            }
        }
        return allSameAs;
    }

    private Object getSameAs(Object left)
    {
        if (isEquivalent(left)) {
            getAllSameAs();
            for (int i = 0; i < allSameAs.size(); i++) {
                SameNode node = (SameNode) allSameAs.get(i);
                if (node.setNum == ((SameNode) allValues.get(left)).setNum) {
                    return node.value;
                }
            }
        }
        return left;
    }

    public boolean isEquivalent(Object o)
    {
        return allValues.containsKey(o);
    }

    public Object getEquivalent(Object left)
    {
        return getSameAs(left);
    }

    public Model coordinate(Model model)
    {
        ArrayList removedStmts = new ArrayList();
        ArrayList addedStmts = new ArrayList();

        StmtIterator stmts = model.listStatements((Resource) null, OWL.sameAs,
                (Resource) null);
        while (stmts.hasNext()) {
            Statement statement = stmts.nextStatement();
            addSameAs(statement.getSubject(), statement.getObject());
            removedStmts.add(statement);
        }
        StmtIterator statements = model.listStatements();
        while (statements.hasNext()) {
            Statement statement = statements.nextStatement();
            Resource subject = statement.getSubject();
            Property predicate = statement.getPredicate();
            RDFNode object = statement.getObject();
            if (isEquivalent(subject)) {
                if (!predicate.equals(RDF.type) 
                        && !predicate.equals(OWL.sameAs)) {
                    removedStmts.add(statement);
                    addedStmts.add(model.createStatement(
                            (Resource) getEquivalent(subject), predicate,
                            object));
                }
            } else if (isEquivalent(object)) {
                removedStmts.add(statement);
                addedStmts.add(model.createStatement(subject, predicate,
                        (RDFNode) getEquivalent(object)));
            }
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
