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
package iws.falcon.matcher.pbm;

import iws.falcon.model.Node;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Wei Hu
 */
public class Cluster
{
    private int cid = -1;
    private double cohesion = 0;
    private HashMap couplings = null;
    private HashMap elements = null;

    public Cluster(int id)
    {
        cid = id;
        couplings = new HashMap();
        elements = new HashMap();
    }

    public int getClusterID()
    {
        return cid;
    }

    public double getCohesion()
    {
        return cohesion;
    }

    public HashMap getCouplings()
    {
        return couplings;
    }

    public Iterator listCouplings()
    {
        return couplings.values().iterator();
    }

    public HashMap getElements()
    {
        return elements;
    }

    public Iterator listElements()
    {
        return elements.values().iterator();
    }

    public void setClusterID(int id)
    {
        cid = id;
    }

    public void setCohesion(double c)
    {
        cohesion = c;
    }

    public void putCoupling(int id, double similarity)
    {
        couplings.put(id, similarity);
    }

    public void putElement(String key, Node node)
    {
        elements.put(key, node);
    }
}
