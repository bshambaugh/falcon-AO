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
public class NeighborSet
{
    private HashMap neighbors = new HashMap();

    public void addNeighbor(Neighbor neighbor)
    {
        neighbors.put(neighbor.getURI(), neighbor);
    }

    public Neighbor getNeighbor(String uri)
    {
        return (Neighbor) neighbors.get(uri);
    }

    public HashMap getNeighbors()
    {
        return neighbors;
    }

    public int size()
    {
        return neighbors.size();
    }

    public void show()
    {
        Iterator iter = neighbors.values().iterator();
        while (iter.hasNext()) {
            Neighbor neighbor = (Neighbor) iter.next();
            System.out.println(neighbor.getURI());
            Iterator i1 = neighbor.getSubjects().values().iterator();
            while (i1.hasNext()) {
                TokenNode node = (TokenNode) i1.next();
                System.out.println("->[s]->" + node.getURI());
            }
            Iterator i2 = neighbor.getPredicates().values().iterator();
            while (i2.hasNext()) {
                TokenNode node = (TokenNode) i2.next();
                System.out.println("->[p]->" + node.getURI());
            }
            Iterator i3 = neighbor.getObjects().values().iterator();
            while (i3.hasNext()) {
                TokenNode node = (TokenNode) i3.next();
                System.out.println("->[o]->" + node.getURI());
            }
            System.out.println();
        }
    }
}
