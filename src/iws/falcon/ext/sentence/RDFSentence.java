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
package iws.falcon.ext.sentence;

import iws.falcon.model.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

/**
 * @author Gong Cheng
 */
public class RDFSentence
{

    public static boolean displayLocalName = true;
    private int ID;
    private ArrayList statements = null;

    public RDFSentence(int id)
    {
        ID = id;
        statements = new ArrayList(1);
    }

    public void addStatement(Statement statement)
    {
        statements.add(statement);
    }

    public void addStatements(ArrayList stat)
    {
        for (int i = 0; i < stat.size(); i++) {
            statements.add((Statement) stat.get(i));
        }
    }

    @Override
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < statements.size(); i++) {
            Statement statement = (Statement) statements.get(i);
            RDFNode object = statement.getObject();
            String objectString = null;
            if (object.isLiteral()) {
                objectString = object.toString();
            }
            if (RDFSentence.displayLocalName) {
                String sln = statement.getSubject().getLocalName();
                String pln = statement.getPredicate().getLocalName();
                buffer.append(sln + "  " + pln + "  ");
                if (objectString == null) {
                    String oln = ((Resource) object.as(Resource.class)).getLocalName();
                    buffer.append(oln);
                } else {
                    buffer.append(objectString);
                }
                buffer.append("\n");
            } else {
                String sn = statement.getSubject().toString();
                String pn = statement.getPredicate().toString();
                buffer.append(sn + "  " + pn + "  ");
                if (objectString == null) {
                    buffer.append(statement.getObject().toString());
                } else {
                    buffer.append(objectString);
                }
                buffer.append("\n");
            }
        }
        return buffer.toString();
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int id)
    {
        ID = id;
    }

    public ArrayList getAllURIs()
    {
        ArrayList uris = new ArrayList(3 * statements.size());
        for (Iterator i = statements.iterator(); i.hasNext();) {
            Statement statement = (Statement) i.next();
            Resource subject = statement.getSubject();
            String uri = subject.toString();
            if (!uris.contains(uri)) {
                uris.add(uri);
            }
            uri = statement.getPredicate().toString();
            if (!uris.contains(uri)) {
                uris.add(uri);
            }
            RDFNode object = statement.getObject();
            uri = object.toString();
            if (!uris.contains(uri)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList getVocabularyURIs()
    {
        ArrayList uris = new ArrayList(3 * statements.size());
        for (Iterator i = statements.iterator(); i.hasNext();) {
            Statement statement = (Statement) i.next();
            Resource subject = statement.getSubject();
            String uri = subject.toString();
            if (subject.isURIResource() && !uris.contains(uri)) {
                uris.add(uri);
            }
            uri = statement.getPredicate().toString();
            if (!uris.contains(uri)) {
                uris.add(uri);
            }
            RDFNode object = statement.getObject();
            uri = object.toString();
            if (object.isURIResource() && !uris.contains(uri)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList getSubjectVocabularyURIs()
    {
        ArrayList uris = new ArrayList(statements.size());
        for (Iterator i = statements.iterator(); i.hasNext();) {
            Resource subject = ((Statement) i.next()).getSubject();
            String uri = subject.toString();
            if (subject.isURIResource() && !uris.contains(uri)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList getPredicateVocabularyURIs()
    {
        ArrayList uris = new ArrayList(statements.size());
        for (Iterator i = statements.iterator(); i.hasNext();) {
            String uri = ((Statement) i.next()).getPredicate().toString();
            if (!uris.contains(uri)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList getObjectVocabularyURIs()
    {
        ArrayList uris = new ArrayList(statements.size());
        for (Iterator i = statements.iterator(); i.hasNext();) {
            RDFNode object = ((Statement) i.next()).getObject();
            String uri = object.toString();
            if (object.isURIResource() && !uris.contains(uri)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList getDomainVocabularyURIs()
    {
        ArrayList uris = new ArrayList(2 * statements.size());
        for (Iterator i = statements.iterator(); i.hasNext();) {
            Statement statement = (Statement) i.next();
            Resource subject = statement.getSubject();
            String uri = subject.toString();
            String ns = subject.getNameSpace();
            if (subject.isURIResource() && !uris.contains(uri)
                    && !Constants.isBuiltInNs(ns)) {
                uris.add(uri);
            }
            uri = statement.getPredicate().toString();
            ns = statement.getPredicate().getNameSpace();
            if (!uris.contains(uri) && !Constants.isBuiltInNs(ns)) {
                uris.add(uri);
            }
            RDFNode object = statement.getObject();
            uri = object.toString();
            if (object.isURIResource() && !uris.contains(uri) 
                    && !Constants.isBuiltInNs(((Resource) object.as(
                            Resource.class)).getNameSpace())) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList getSubjectDomainVocabularyURIs()
    {
        ArrayList uris = new ArrayList(statements.size());
        for (Iterator i = statements.iterator(); i.hasNext();) {
            Resource subject = ((Statement) i.next()).getSubject();
            String uri = subject.toString();
            String ns = subject.getNameSpace();
            if (subject.isURIResource() && !uris.contains(uri) 
                    && !Constants.isBuiltInNs(ns)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList getPredicateDomainVocabularyURIs()
    {
        ArrayList uris = new ArrayList(statements.size());
        for (Iterator i = statements.iterator(); i.hasNext();) {
            Resource predicate = ((Statement) i.next()).getPredicate();
            String uri = predicate.toString();
            String ns = predicate.getNameSpace();
            if (!uris.contains(uri) && !Constants.isBuiltInNs(ns)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList getObjectDomainVocabularyURIs()
    {
        ArrayList uris = new ArrayList(statements.size());
        for (Iterator i = statements.iterator(); i.hasNext();) {
            RDFNode object = ((Statement) i.next()).getObject();
            String uri = object.toString();
            if (object.isURIResource() && !uris.contains(uri)
                    && !Constants.isBuiltInNs(((Resource) object.as(
                            Resource.class)).getNameSpace())) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList getPredicatePlusObjectVocabularyURIs()
    {
        ArrayList uris = new ArrayList(statements.size());
        for (Iterator i = statements.iterator(); i.hasNext();) {
            Statement statement = (Statement) i.next();
            if (statement.getObject().isURIResource()) {
                String ps = statement.getPredicate().toString();
                String os = statement.getObject().toString();
                uris.add(ps + " " + os);
            }
        }
        return uris;
    }

    public ArrayList getPaths(String sourceURI, String targetURI)
    {
        ArrayList paths = new ArrayList(0);
        for (Iterator i = statements.iterator(); i.hasNext();) {
            Statement statement = (Statement) i.next();
            String subjectURI = statement.getSubject().toString();
            if (subjectURI.equals(sourceURI)) {
                RDFNode object = statement.getObject();
                if (object.toString().equals(targetURI)) {
                    ArrayList path = new ArrayList(1);
                    path.add(statement);
                    paths.add(path);
                } else if (object.isAnon()) {
                    for (Iterator j = getPaths(
                            object.toString(), targetURI).iterator(); j.hasNext();) {
                        ArrayList nextPath = (ArrayList) j.next();
                        ArrayList path = new ArrayList(nextPath.size() + 1);
                        path.add(statement);
                        for (int k = 0; k < nextPath.size(); k++) {
                            path.add((Statement) nextPath.get(k));
                        }
                        paths.add(path);
                    }
                }
            }
        }
        return paths;
    }

    public ArrayList getStatements()
    {
        return new ArrayList(statements);
    }
}
