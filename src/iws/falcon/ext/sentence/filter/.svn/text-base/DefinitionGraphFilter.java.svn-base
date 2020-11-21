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
import java.util.HashMap;
import java.util.Iterator;
import iws.falcon.ext.sentence.RDFSentence;

/**
 * @author Gong Cheng
 */
public class DefinitionGraphFilter implements RDFSentenceFilter
{
    public ArrayList filter(ArrayList sentences)
    {
        HashMap sURIToSentences = new HashMap(sentences.size());
        int bNodeCount = 0;
        nextSentence:
        for (Iterator i = sentences.iterator(); i.hasNext();) {
            RDFSentence sentence = (RDFSentence) i.next();
            Iterator j = sentence.getSubjectDomainVocabularyURIs().iterator();
            if (!j.hasNext()) {
                sURIToSentences.put("DefB%" + bNodeCount, sentence);
                bNodeCount++;
                continue nextSentence;
            }
            String firstURI = (String) j.next();
            RDFSentence first = (RDFSentence) sURIToSentences.get(firstURI);
            if (first == null) {
                sURIToSentences.put(firstURI, sentence);
                first = sentence;
            } else {
                first.addStatements(sentence.getStatements());
            }
            while (j.hasNext()) {
                String uri = (String) j.next();
                RDFSentence current = (RDFSentence) sURIToSentences.get(uri);
                if (current == null) {
                    sURIToSentences.put(uri, first);
                } else if (current != first) {
                    first.addStatements(current.getStatements());
                    sURIToSentences.put(uri, first);
                }
            }
        }
        ArrayList result = new ArrayList(sURIToSentences.values());
        sURIToSentences = null;
        return result;
    }
}
