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

/**
 * @author Wei Hu
 */
public class Neighbor
{
    private String uri = "";
    private HashMap subjects = new HashMap();
    private HashMap predicates = new HashMap();
    private HashMap objects = new HashMap();

    public Neighbor(String u)
    {
        uri = u;
    }

    public void setUri(String u)
    {
        uri = u;
    }

    public void addSubject(TokenNode node)
    {
        subjects.put(node.getURI(), node);
    }

    public void addPredicate(TokenNode node)
    {
        predicates.put(node.getURI(), node);
    }

    public void addObject(TokenNode node)
    {
        objects.put(node.getURI(), node);
    }

    public String getURI()
    {
        return uri;
    }

    public HashMap getSubjects()
    {
        return subjects;
    }

    public HashMap getPredicates()
    {
        return predicates;
    }

    public HashMap getObjects()
    {
        return objects;
    }
}
