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
package iws.falcon.matcher.gmo;

import iws.falcon.model.Node;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class DataLiteralSim
{
    public double getSimilarity(Node left, Node right)
    {
        return DataLiteralSim.getDataLiteralSim(left, right);
    }

    public static double getDataLiteralSim(Node left, Node right)
    {
        if (left.equals(right)) {
            return 1;
        }
        return 0;
    }
}
