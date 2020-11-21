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
package iws.falcon.model;

import com.hp.hpl.jena.ontology.OntModel;
import iws.falcon.model.coordination.RuleList;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public interface RBGModel
{
    public OntModel getOntModel();

    public void setOntModel(OntModel ontModel);

    public RBGModel read(String uri);

    public void clearModel();

    public void setCoordinationRuleList(RuleList rules);

    public Iterator listQuadruples();

    public ArrayList getQuadruples();

    public Iterator listStmtNodes();

    public NodeList getStmtNodes();

    public Iterator listNodes();

    public NodeList getNodes();

    public Node getNode(Object node);

    public String getNsPrefixURI(String prefix);

    public String getNsURIPrefix(String uri);

    public Iterator listNamedClassNodes();

    public NodeList getNamedClassNodes();

    public Iterator listNamedInstanceNodes();

    public NodeList getNamedInstanceNodes();

    public Iterator listClassNodes();

    public NodeList getClassNodes();

    public Iterator listPropertyNodes();

    public NodeList getPropertyNodes();

    public Iterator listInstanceNodes();

    public NodeList getInstanceNodes();

    public Iterator listLiteralNodes();

    public Iterator listLanguageLevel();

    public Iterator listOntologyLevel();

    public Iterator listInstanceLevel();

    public void setAddInstType(boolean ait);

    public void setClearClassType(boolean cct);
}
