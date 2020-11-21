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
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import iws.falcon.model.Constants;
import iws.falcon.model.coordination.Coordinator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class List implements Coordinator
{
    public Model coordinate(Model model)
    {
        ArrayList removedStmts = new ArrayList();
        ArrayList addedStmts = new ArrayList();

        HashMap lists = new HashMap();

        String querystr = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " 
                + " SELECT ?x ?y ?z ?type WHERE " 
                + " {?x rdf:type ?type. ?x rdf:first ?y. ?x rdf:rest ?z.} ";

        Query query = QueryFactory.create(querystr);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        for (Iterator iter = results; iter.hasNext();) {
            QuerySolution res = (QuerySolution) iter.next();
            Resource x = (Resource) res.get("x");
            RDFNode y = (RDFNode) res.get("y");
            Resource z = (Resource) res.get("z");
            Resource nodeType = (Resource) res.get("type");
            removedStmts.add(model.createStatement(x, RDF.type, nodeType));
            removedStmts.add(model.createStatement(x, RDF.first, y));
            removedStmts.add(model.createStatement(x, RDF.rest, z));

            BinaryTreeNode tempX = null;
            BinaryTreeNode tempZ = null;

            if (!lists.containsKey(x)) {
                tempX = new BinaryTreeNode(x);
                lists.put(x, tempX);
            } else {
                tempX = (BinaryTreeNode) lists.get(x);
            }
            tempX.setRight(y);
            tempX.setNodeType(nodeType);
            if (z.toString().equals(Constants.RDF_NS + "nil")) {
                tempX.setLeft(null);
            } else {
                if (!lists.containsKey(z)) {
                    tempZ = new BinaryTreeNode(z);
                    lists.put(z, tempZ);
                } else {
                    tempZ = (BinaryTreeNode) lists.get(z);
                }
                tempX.setLeft(tempZ);
                tempZ.setFather(tempX);
            }
        }
        Iterator listNodes = lists.values().iterator();
        while (listNodes.hasNext()) {
            BinaryTreeNode node = (BinaryTreeNode) listNodes.next();
            if (node.getLeft() == null) {
                ArrayList members = new ArrayList();
                members.add((RDFNode) node.getRight());
                while (node.getFather() != null) {
                    node = node.getFather();
                    members.add((RDFNode) node.getRight());
                }
                for (int i = 0; i < members.size(); i++) {
                    addedStmts.add(model.createStatement((Resource) node.getValue(), 
                            RDFS.member, (RDFNode) members.get(i)));
                }
                if (!node.getNodeType().toString().equals(
                        Constants.RDF_NS + "List")) {
                    addedStmts.add(model.createStatement((Resource) node.getValue(),
                            RDF.type, (Resource) node.getNodeType()));
                }
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

    public Model coordinate2(Model model)
    {
        ArrayList removedStmts = new ArrayList();
        ArrayList addedStmts = new ArrayList();

        HashMap lists = new HashMap();

        String querystr = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + " SELECT ?x ?y ?z ?type WHERE " 
                + " {?x rdf:type ?type. ?x rdf:first ?y. ?x rdf:rest ?z.} ";

        Query query = QueryFactory.create(querystr);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        for (Iterator iter = results; iter.hasNext();) {
            QuerySolution res = (QuerySolution) iter.next();
            Resource x = (Resource) res.get("x");
            RDFNode y = (RDFNode) res.get("y");
            Resource z = (Resource) res.get("z");
            removedStmts.add(model.createStatement(x, RDF.first, y));
            removedStmts.add(model.createStatement(x, RDF.rest, z));

            BinaryTreeNode tempX = null;
            BinaryTreeNode tempZ = null;

            if (!lists.containsKey(x)) {
                tempX = new BinaryTreeNode(x);
                lists.put(x, tempX);
            } else {
                tempX = (BinaryTreeNode) lists.get(x);
            }
            tempX.setRight(y);
            tempX.setNodeType(null);
            if (z.toString().equals(Constants.RDF_NS + "nil")) {
                tempX.setLeft(null);
            } else {
                if (!lists.containsKey(z)) {
                    tempZ = new BinaryTreeNode(z);
                    lists.put(z, tempZ);
                } else {
                    tempZ = (BinaryTreeNode) lists.get(z);
                }
                tempX.setLeft(tempZ);
                tempZ.setFather(tempX);
            }
        }
        Iterator listNodes = lists.values().iterator();
        while (listNodes.hasNext()) {
            BinaryTreeNode node = (BinaryTreeNode) listNodes.next();
            if (node.getLeft() == null) {
                ArrayList members = new ArrayList();
                members.add((RDFNode) node.getRight());
                while (node.getFather() != null) {
                    node = node.getFather();
                    members.add((RDFNode) node.getRight());
                }
                for (int i = 0; i < members.size(); i++) {
                    addedStmts.add(model.createStatement((Resource) node.getValue(), 
                            RDFS.member, (RDFNode) members.get(i)));
                }
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

class BinaryTreeNode
{

    private BinaryTreeNode father = null;
    private BinaryTreeNode left = null;
    private Object right = null;
    private boolean isLeaf = false;
    private Object value;
    private Object nodeType;

    public BinaryTreeNode(Object v)
    {
        value = v;
    }

    public BinaryTreeNode getFather()
    {
        return father;
    }

    public BinaryTreeNode getLeft()
    {
        return left;
    }

    public Object getRight()
    {
        return right;
    }

    public boolean isLeaf()
    {
        return isLeaf;
    }

    public Object getValue()
    {
        return value;
    }

    public Object getNodeType()
    {
        return nodeType;
    }

    public void setFather(BinaryTreeNode node)
    {
        father = node;
    }

    public void setLeft(BinaryTreeNode node)
    {
        left = node;
    }

    public void setRight(Object node)
    {
        right = node;
    }

    public void setIsLeaf(boolean leaf)
    {
        isLeaf = leaf;
    }

    public void setValue(Object v)
    {
        value = v;
    }

    public void setNodeType(Object nt)
    {
        nodeType = nt;
    }
}
