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
package iws.falcon.model.impl;

import iws.falcon.model.Node;
import iws.falcon.model.Quadruple;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class QuadrupleImpl implements Quadruple
{
    private Node statement = null;
    private Node subject = null;
    private Node predicate = null;
    private Node object = null;

    public QuadrupleImpl(Node stat, Node s, Node p, Node o)
    {
        statement = stat;
        subject = s;
        predicate = p;
        object = o;
    }

    public Node getStatement()
    {
        return statement;
    }

    public Node getSubject()
    {
        return subject;
    }

    public Node getPredicate()
    {
        return predicate;
    }

    public Node getObject()
    {
        return object;
    }

    public void setStatement(Node stat)
    {
        statement = stat;
    }

    public void setSubject(Node s)
    {
        subject = s;
    }

    public void setPredicate(Node p)
    {
        predicate = p;
    }

    public void setObject(Node o)
    {
        object = o;
    }

    public String toString()
    {
        return statement.toString();
    }
}
