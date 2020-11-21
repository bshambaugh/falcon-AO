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
package iws.falcon.matcher.gmo;

import iws.falcon.model.Node;
import iws.falcon.model.Constants;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class BuiltInVocSim
{
    public static final double[][] classMatrix = {
        {1.0, 0.2, 0.2, 0.2, 0.3, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {1.0, 1.0, 0.2, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {1.0, 0.2, 1.0, 0.3, 0.1, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {1.0, 0.0, 0.1, 1.0, 0.1, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {0.6, 0.0, 0.1, 0.1, 1.0, 0.4, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {0.0, 0.0, 0.0, 0.0, 0.4, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {0.7, 0.2, 0.2, 0.2, 0.3, 0.3, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // rdf:Property
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0},
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0},
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0},
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0},
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0}
    };
    public static final double[][] propertyMatrix = {
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
    };
    public static String owlObjectProperty = Constants.OWL_NS + "ObjectProperty";
    public static String owlTransitiveProperty = Constants.OWL_NS + "TransitiveProperty";
    public static String owlSymmetricProperty = Constants.OWL_NS + "SymmetricProperty";
    public static String owlInverseFunctionalProperty = Constants.OWL_NS + "InverseFunctionalProperty";
    public static String owlFunctionalProperty = Constants.OWL_NS + "FunctionalProperty";
    public static String owlDatatypeProperty = Constants.OWL_NS + "DatatypeProperty";
    public static String rdfsProperty = Constants.RDFS_NS + "Property";
    public static String rdfsResource = Constants.RDFS_NS + "Resource";
    public static String rdfsClass = Constants.RDFS_NS + "Class";
    public static String owlClass = Constants.OWL_NS + "Class";
    public static String owlThing = Constants.OWL_NS + "Thing";
    public static String owlNothing = Constants.OWL_NS + "Nothing";
    public static String ClassNames[] = {owlObjectProperty,
        owlTransitiveProperty, owlSymmetricProperty,
        owlInverseFunctionalProperty, owlFunctionalProperty,
        owlDatatypeProperty, rdfsProperty, rdfsResource, rdfsClass,
        owlClass, owlThing, owlNothing
    };
    public static String owlSomeValuesFrom = Constants.OWL_NS + "someValuesFrom";
    public static String owlAllValuesFrom = Constants.OWL_NS + "allValuesFrom";
    public static String owlCardinality = Constants.OWL_NS + "cardinality";
    public static String owlMinCardinality = Constants.OWL_NS + "minCardinality";
    public static String owlMaxCardinality = Constants.OWL_NS + "maxCardinality";
    public static String owlHasValue = Constants.OWL_NS + "hasValue";
    public static String owlOnProperty = Constants.OWL_NS + "onProperty";
    public static String owlComplementOf = Constants.OWL_NS + "complementOf";
    public static String rdfsDomain = Constants.RDFS_NS + "domain";
    public static String rdfsRange = Constants.RDFS_NS + "range";
    public static String rdfsSubPropertyOf = Constants.RDFS_NS + "subPropertyOf";
    public static String rdfsSubClassOf = Constants.RDFS_NS + "subClassOf";
    public static String rdfType = Constants.RDF_NS + "type";
    public static String PropertyNames[] = {owlSomeValuesFrom,
        owlAllValuesFrom, owlCardinality, owlMinCardinality,
        owlMaxCardinality, owlHasValue, owlOnProperty, owlComplementOf,
        rdfsDomain, rdfsRange, rdfsSubPropertyOf, rdfsSubClassOf, rdfType
    };

    public static double getSimilarity(Node left, Node right)
    {
        return BuiltInVocSim.getSimilarity(left.toString(), right.toString());
    }

    public static double getSimilarity(String uri1, String uri2)
    {
        if (uri1.equals(uri2)) {
            if (uri1.equals(Constants.RDF_NS + "iType")) {
                return 0.1;
            }
            if (uri1.equals(Constants.RDFS_NS + "subClassOf")) {
                return 0.2;
            }
            return 1;
        }
        int index1 = -1, index2 = -1;
        for (int i = 0; i < PropertyNames.length; i++) {
            if (PropertyNames[i].equals(uri1)) {
                index1 = i;
            }
            if (PropertyNames[i].equals(uri2)) {
                index2 = i;
            }
        }
        if (index1 >= 0 && index2 >= 0) {
            return propertyMatrix[index1][index2];
        }
        index1 = -1;
        index2 = -1;
        for (int i = 0; i < ClassNames.length; i++) {
            if (ClassNames[i].equals(uri1)) {
                index1 = i;
            }
            if (ClassNames[i].equals(uri2)) {
                index2 = i;
            }
        }
        if (index1 >= 0 && index2 >= 0) {
            return classMatrix[index1][index2];
        }
        return 0;
    }
}
