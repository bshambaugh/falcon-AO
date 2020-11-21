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
package iws.falcon.model.coordination.rule;

import iws.falcon.model.coordination.Coordinator;
import java.util.HashMap;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public abstract class Equivalent implements Coordinator
{
    HashMap allValues = new HashMap();

    public boolean isEmpty()
    {
        if (allValues != null) {
            return allValues.isEmpty();
        }
        return true;
    }

    public abstract boolean isEquivalent(Object o);

    public abstract Object getEquivalent(Object left);

    public class SameNode
    {
        public static final int MaxInteger = 5000000;
        public Object value = null;
        public int setNum = MaxInteger;

        public SameNode(Object v)
        {
            value = v;
        }
    }
}
