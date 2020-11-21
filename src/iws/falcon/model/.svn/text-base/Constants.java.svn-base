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
public class Constants
{
    public static final String OWL_NS = "http://www.w3.org/2002/07/owl#";
    public static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String RDFS_NS = "http://www.w3.org/2000/01/rdf-schema#";
    public static final String XSD_NS = "http://www.w3.org/2001/XMLSchema#";
    public static final String DC_NS = "http://purl.org/dc/elements/1.1/";
    public static final String FOAF_NS = "http://xmlns.com/foaf/0.1/";
    public static final String ICAL_NS = "http://www.w3.org/2002/12/cal/ical#";
    public static final int ONTOLOGY_CLASS = 1;
    public static final int ONTOLOGY_PROPERTY = 8;
    public static final int ONTOLOGY_INSTANCE = 16;
    public static final int EXTERNAL_CLASS = 32;
    public static final int EXTERNAL_PROPERTY = 64;
    public static final int EXTERNAL_INSTANCE = 128;
    public static final int STMT = 256;
    public static String BUILTIN_NS[] = {XSD_NS, RDF_NS, RDFS_NS, OWL_NS,
        DC_NS, FOAF_NS, ICAL_NS
    };

    public static boolean isBuiltInNs(String ns)
    {
        for (int i = 0, n = BUILTIN_NS.length; i < n; i++) {
            if (ns.equals(BUILTIN_NS[i])) {
                return true;
            }
        }
        return false;
    }
}
