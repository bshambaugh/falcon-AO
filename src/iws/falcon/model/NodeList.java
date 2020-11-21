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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class NodeList
{
    private ArrayList list = null;

    public NodeList()
    {
        list = new ArrayList();
    }

    public NodeList(int capacity)
    {
        list = new ArrayList(capacity);
    }

    public int size()
    {
        return list.size();
    }

    public Node get(int i)
    {
        return (Node) list.get(i);
    }

    public void set(int index, Node node)
    {
        list.set(index, node);
    }

    public void add(Node node)
    {
        list.add(node);
    }

    public boolean contains(Node node)
    {
        return list.contains(node);
    }

    public int indexOf(Node node)
    {
        return list.indexOf(node);
    }

    public Node remove(int i)
    {
        return (Node) list.remove(i);
    }

    public boolean remove(Node node)
    {
        return list.remove(node);
    }

    public Iterator iterator()
    {
        return list.iterator();
    }

    public ArrayList getList()
    {
        return list;
    }
}
