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
package iws.falcon.ext.sentence.filter;

import java.util.ArrayList;
import java.util.Iterator;
import iws.falcon.ext.sentence.RDFSentence;
import iws.falcon.ext.sentence.RDFSentenceGraph;
import iws.falcon.ext.sentence.Vocabulary;

/**
 * @author Gong Cheng
 */
public class InstanceLevelFilter implements RDFSentenceFilter
{
    private RDFSentenceGraph graph = null;

    public InstanceLevelFilter(RDFSentenceGraph g)
    {
        graph = g;
    }

    public ArrayList filter(ArrayList sentences)
    {
        ArrayList result = new ArrayList(sentences.size());
        nextSentence:
        for (Iterator i = sentences.iterator(); i.hasNext();) {
            RDFSentence sentence = (RDFSentence) i.next();
            ArrayList uris = sentence.getAllURIs();
            for (Iterator j = uris.iterator(); j.hasNext();) {
                Vocabulary vocabulary = graph.getVocabulary((String) j.next());
                if (!vocabulary.isConcept()) {
                    continue nextSentence;
                }
            }
            result.add(sentence);
        }
        return result;
    }
}
