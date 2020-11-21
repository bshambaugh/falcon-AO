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
package iws.falcon.model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import iws.falcon.model.Node;
import iws.falcon.model.NodeList;
import iws.falcon.model.Quadruple;
import iws.falcon.model.RBGModel;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class NodeImpl implements Node
{
    private Resource resource = null;
    private RDFNode rdfnode = null;
    private Statement statement = null;
    private int nodeType = Node.UNDEFINED;
    private int nodeLevel = Node.UNDEFINED;
    private int category = Node.UNDEFINED;
    private ArrayList label = null;
    private ArrayList comment = null;
    private String uri = null;
    private int hierarchy = 0;
    private NodeList parents = null;
    private NodeList children = null;
    private NodeList domains = null;
    private RBGModel model = null;
    public int _built_in = 0;
    public int _as_prop = 0;
    public int _p_cls = 0;
    public int _cls_lvl = 0;

    public NodeImpl(Resource resource)
    {
        setNodeValue(resource);
        setNodeType(SPO_RESOURCE);
    }

    public NodeImpl(RDFNode rdfNode)
    {
        setNodeValue(rdfNode);
        setNodeType(SPO_RDFNODE);
    }

    public NodeImpl(Statement statement)
    {
        setNodeValue(statement);
        setNodeType(STATEMENT);
    }

    public String getLocalName()
    {
        if (nodeType == STATEMENT) {
            return "[STATEMENT]";
        } else if (nodeType == SPO_RESOURCE) {
            return resource.getLocalName();
        } else if (nodeType == SPO_RDFNODE) {
            if (rdfnode.asNode().isLiteral()) {
                return rdfnode.asNode().getLiteral().getLexicalForm();
            } else {
                return rdfnode.asNode().toString();
            }
        }
        return null;
    }

    public String getNameSpace()
    {
        if (nodeType == STATEMENT) {
            return "STATEMENT";
        } else if (nodeType == SPO_RESOURCE) {
            return resource.getNameSpace();
        } else if (nodeType == SPO_RDFNODE) {
            if (rdfnode.asNode().isLiteral()) {
                return rdfnode.asNode().getLiteral().getDatatypeURI();
            }
        }
        return null;
    }

    public String getNSQName()
    {
        if (nodeType == STATEMENT) {
            return "STATEMENT";
        } else if (nodeType == SPO_RESOURCE && getNameSpace() != null) {
            int index = toString().indexOf(getLocalName());
            return model.getNsURIPrefix(toString().substring(0, index));
        }
        return null;
    }

    public ArrayList getLabel()
    {
        return label;
    }

    public void addLabel(String l)
    {
        if (label == null) {
            label = new ArrayList();
        }
        label.add(l);
    }

    public ArrayList getComment()
    {
        return comment;
    }

    public void addComment(String c)
    {
        if (comment == null) {
            comment = new ArrayList();
        }
        comment.add(c);
    }

    public int getHierarchy()
    {
        return hierarchy;
    }

    public void setHierarchy(int hier)
    {
        hierarchy = hier;
    }

    public int getNodeType()
    {
        return nodeType;
    }

    public void setNodeType(int nt)
    {
        nodeType = nt;
    }

    public int getNodeLevel()
    {
        return nodeLevel;
    }

    public void setNodeLevel(int nl)
    {
        nodeLevel = nl;
    }

    public int getCategory()
    {
        return category;
    }

    public void setCategory(int c)
    {
        category = c;
    }

    public RBGModel getRBGModel()
    {
        return model;
    }

    public void setRBGModel(RBGModel m)
    {
        model = m;
    }

    public Object getValue()
    {
        if (getNodeType() == Node.STATEMENT) {
            return statement;
        } else if (getNodeType() == Node.SPO_RDFNODE) {
            return rdfnode;
        } else if (getNodeType() == Node.SPO_RESOURCE) {
            return resource;
        }
        return null;
    }

    public void setNodeValue(Resource r)
    {
        resource = r;
    }

    public void setNodeValue(RDFNode n)
    {
        rdfnode = n;
    }

    public void setNodeValue(Statement s)
    {
        statement = s;
    }

    public Node getSubject()
    {
        if (nodeType == STATEMENT) {
            return model.getNode(statement.getSubject());
        }
        return null;
    }

    public Node getPredicate()
    {
        if (nodeType == STATEMENT) {
            return model.getNode(statement.getPredicate());
        }
        return null;
    }

    public Node getObject()
    {
        if (nodeType == STATEMENT) {
            return model.getNode(statement.getObject());
        }
        return null;
    }

    public boolean isAnon()
    {
        if (nodeType == STATEMENT) {
            return true;
        } else if (nodeType == SPO_RESOURCE) {
            return resource.isAnon();
        } else if (nodeType == SPO_RDFNODE) {
            return rdfnode.asNode().isBlank();
        }
        return true;
    }

    public boolean isLiteral()
    {
        if (getCategory() == Node.LITERAL) {
            return true;
        }
        return false;
    }

    public boolean isBuiltIn()
    {
        if (getNodeLevel() == Node.LANGUAGE_LEVEL && !isLiteral()) {
            return true;
        } else {
            return false;
        }
    }

    public NodeList getAdjacentNodes()
    {
        NodeList nodeList = new NodeList();
        if (nodeType == STATEMENT) {
            nodeList.add(getSubject());
            nodeList.add(getPredicate());
            nodeList.add(getObject());
        } else {
            Iterator quadruples = model.listQuadruples();
            while (quadruples.hasNext()) {
                Quadruple quadruple = (Quadruple) quadruples.next();
                if (quadruple.getSubject().equals(this) 
                        || quadruple.getPredicate().equals(this) 
                        || quadruple.getObject().equals(this)) {
                    nodeList.add(quadruple.getStatement());
                }
            }
        }
        return nodeList;
    }

    public Iterator listAdjacentNodes()
    {
        NodeList nodeList = getAdjacentNodes();
        return nodeList.iterator();
    }

    public NodeList getPointedNodes()
    {
        NodeList nodeList = new NodeList();
        if (nodeType == STATEMENT) {
            nodeList.add(getPredicate());
            nodeList.add(getObject());
        } else {
            Iterator quadruples = model.listQuadruples();
            while (quadruples.hasNext()) {
                Quadruple quadruple = (Quadruple) quadruples.next();
                if (quadruple.getSubject().equals(this)) {
                    nodeList.add(quadruple.getStatement());
                }
            }
        }
        return nodeList;
    }

    public Iterator listPointedNodes()
    {
        NodeList nodeList = getPointedNodes();
        return nodeList.iterator();
    }

    public Iterator listNamedSupers()
    {
        return getNamedSupers().iterator();
    }

    public NodeList getNamedSupers()
    {
        return parents;
    }

    public void addNamedSuper(Node node)
    {
        if (parents == null) {
            parents = new NodeList();
        }
        if (!parents.contains(node)) {
            parents.add(node);
        }
    }
    
    public Iterator listNamedSubs()
    {
        return getNamedSubs().iterator();
    }

    public NodeList getNamedSubs()
    {
        return children;
    }

    public void addNamedSub(Node node)
    {
        if (children == null) {
            children = new NodeList();
        }
        if (!children.contains(node)) {
            children.add(node);
        }
    }

    public Iterator listNamedDomains()
    {
        return getNamedDomains().iterator();
    }

    public NodeList getNamedDomains()
    {
        return domains;
    }

    public void addNamedDomain(Node node)
    {
        if (domains == null) {
            domains = new NodeList();
        }
        if (!domains.contains(node)) {
            domains.add(node);
        }
    }

    @Override
    public String toString()
    {
        if (uri != null) {
            return uri;
        } else if (nodeType == STATEMENT) {
            uri = statement.toString();
        } else if (nodeType == SPO_RESOURCE) {
            uri = resource.toString();
        } else if (nodeType == SPO_RDFNODE) {
            if (rdfnode.asNode().isLiteral()) {
                uri = rdfnode.asNode().getLiteral().toString();
            } else {
                uri = rdfnode.toString();
            }
        }
        return uri;
    }

    public boolean equals(Node node)
    {
        if (toString().equals(node.toString())) {
            return true;
        } else {
            return false;
        }
    }
}
