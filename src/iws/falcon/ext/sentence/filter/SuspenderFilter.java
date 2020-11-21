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

/**
 * @author Gong Cheng
 */
public class SuspenderFilter implements RDFSentenceFilter
{
    public ArrayList filter(ArrayList sentences)
    {
        ArrayList result = new ArrayList(sentences.size());
        for (Iterator i = sentences.iterator(); i.hasNext();) {
            RDFSentence sentence = (RDFSentence) i.next();
            if (sentence.getDomainVocabularyURIs().size() > 1) {
                result.add(sentence);
            }
        }
        return result;
    }
}
