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
package iws.falcon.model.coordination;

import java.util.ArrayList;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class RuleList
{
    private ArrayList rules;

    public RuleList()
    {
        rules = new ArrayList();
    }

    public RuleList(int capacity)
    {
        rules = new ArrayList(capacity);
    }

    public RuleList(RuleList list)
    {
        rules = list.rules;
    }

    public Coordinator get(int i)
    {
        return (Coordinator) rules.get(i);
    }

    public void add(Coordinator rule)
    {
        rules.add(rule);
    }

    public int size()
    {
        return rules.size();
    }
}
