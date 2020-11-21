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

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class NodeCategory
{
    public static int getCategory(Node node)
    {
        if (node.getNodeType() == Node.STATEMENT) {
            return Constants.STMT;
        } else if (node.getNodeLevel() == Node.LANGUAGE_LEVEL 
                || node.getNodeLevel() == Node.EXTERNAL 
                || node.getCategory() == Node.LITERAL) {
            if (node.getCategory() == Node.CLASS) {
                return Constants.EXTERNAL_CLASS;
            } else if (node.getCategory() == Node.INSTANCE 
                    || node.getCategory() == Node.LITERAL) {
                return Constants.EXTERNAL_INSTANCE;
            } else {
                return Constants.EXTERNAL_PROPERTY;
            }
        } else {
            if (node.getCategory() == Node.CLASS) {
                return Constants.ONTOLOGY_CLASS;
            } else if (node.getCategory() == Node.INSTANCE 
                    || node.getCategory() == Node.LIST) {
                return Constants.ONTOLOGY_INSTANCE;
            } else {
                return Constants.ONTOLOGY_PROPERTY;
            }
        }
    }

    public static int getCategoryWithoutExternal(Node node)
    {
        int category = getCategory(node);
        if (node.getNodeLevel() == Node.EXTERNAL) {
            if (category == Constants.EXTERNAL_CLASS) {
                return Constants.ONTOLOGY_CLASS;
            } else if (category == Constants.EXTERNAL_INSTANCE) {
                return Constants.ONTOLOGY_INSTANCE;
            } else if (category == Constants.EXTERNAL_PROPERTY) {
                return Constants.ONTOLOGY_PROPERTY;
            }
        }
        return category;
    }

    public static int getLanguageCategory(String ns, String localName)
    {
        if (ns.equals(Constants.XSD_NS)) {
            return Node.CLASS;
        } else if (ns.equals(Constants.RDF_NS)) {
            if (localName.equals("type") 
                    || localName.equals("value") 
                    || localName.equals("first") 
                    || localName.equals("rest")) {
                return Node.PROPERTY;
            } else if (localName.equals("List") 
                    || localName.equals("XMLLiteral")
                    || localName.equals("Property")) {
                return Node.CLASS;
            } else if (localName.equals("nil")) {
                return Node.INSTANCE;
            }
        } else if (ns.equals(Constants.RDFS_NS)) {
            if (localName.equals("domain") 
                    || localName.equals("range") 
                    || localName.equals("subClassOf") 
                    || localName.equals("subPropertyOf")) {
                return Node.PROPERTY;
            } else if (localName.equals("Class") 
                    || localName.equals("Resource")) {
                return Node.CLASS;
            }
        } else if (ns.equals(Constants.OWL_NS)) { // langProfile = "OWL"
            if (localName.equals("Class") 
                    || localName.equals("Restriction")
                    || localName.equals("ObjectProperty") 
                    || localName.equals("DatatypeProperty")
                    || localName.equals("AnnotationProperty") 
                    || localName.equals("TransitiveProperty") 
                    || localName.equals("SymmetricProperty") 
                    || localName.equals("InverseFunctionalProperty")
                    || localName.equals("FunctionalProperty") 
                    || localName.equals("Ontology") 
                    || localName.equals("DeprecatedClass") 
                    || localName.equals("DeprecatedProperty")
                    || localName.equals("AllDifferent")) {
                return Node.CLASS;
            } else if (localName.equals("onProperty") 
                    || localName.equals("oneOf") 
                    || localName.equals("allValuesFrom") 
                    || localName.equals("someValuesFrom")
                    || localName.equals("hasValue")
                    || localName.equals("cardinality") 
                    || localName.equals("maxCardinality")
                    || localName.equals("minCardinality") 
                    || localName.equals("equivalentClass")
                    || localName.equals("equivalentProperty") 
                    || localName.equals("sameAs") 
                    || localName.equals("inverseOf")
                    || localName.equals("unionOf") 
                    || localName.equals("intersectionOf") 
                    || localName.equals("differentFrom") 
                    || localName.equals("distinctMembers") 
                    || localName.equals("disjointWith")) {
                return Node.PROPERTY;
            } else if (localName.equals("Thing")
                    || localName.equals("Nothing")
                    || localName.equals("priorVersion")
                    || localName.equals("backwardCompatibleWith") 
                    || localName.equals("incompatibleWith") 
                    || localName.equals("imports")) {
                return Node.INSTANCE;
            }
        }
        return Node.UNDEFINED;
    }
}
