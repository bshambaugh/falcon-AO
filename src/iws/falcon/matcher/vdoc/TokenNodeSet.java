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
package iws.falcon.matcher.vdoc;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Wei Hu
 */
public class TokenNodeSet
{
    private HashMap nodes = new HashMap();

    public Iterator iterator()
    {
        return nodes.values().iterator();
    }

    public void add(TokenNode node)
    {
        nodes.put(node.getURI(), node);
    }

    public TokenNode get(String uri)
    {
        return (TokenNode) nodes.get(uri);
    }

    public boolean contains(String uri)
    {
        return nodes.containsKey(uri);
    }

    public int size()
    {
        return nodes.size();
    }

    public void show()
    {
        for (Iterator iter = nodes.values().iterator(); iter.hasNext();) {
            TokenNode node = (TokenNode) iter.next();
            System.out.println(node.getURI());
            HashMap t = node.getLocalName();
            if (t != null) {
                for (Iterator i = t.values().iterator(); i.hasNext();) {
                    WordFrequency wf = (WordFrequency) i.next();
                    System.out.println("->[localname]->" + wf.getWord() 
                            + ", " + wf.getFrequency());
                }
            }
            t = node.getLabel();
            if (t != null) {
                for (Iterator i = t.values().iterator(); i.hasNext();) {
                    WordFrequency wf = (WordFrequency) i.next();
                    System.out.println("->[label]->" + wf.getWord() 
                            + ", " + wf.getFrequency());
                }
            }
            t = node.getComment();
            if (t != null) {
                for (Iterator i = t.values().iterator(); i.hasNext();) {
                    WordFrequency wf = (WordFrequency) i.next();
                    System.out.println("->[comment]->" + wf.getWord() 
                            + ", " + wf.getFrequency());
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
