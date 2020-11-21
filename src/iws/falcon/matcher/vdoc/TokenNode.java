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
import iws.falcon.model.Node;

/**
 * @author Wei Hu
 */
public class TokenNode
{
    private Node node = null;
    private HashMap localname = null;
    private HashMap label = null;
    private HashMap comment = null;

    public TokenNode(Node n)
    {
        node = n;
    }

    public TokenNode(Node n, HashMap name, HashMap lbl, HashMap cmt)
    {
        node = n;
        if (name != null) {
            localname = name;
        }
        if (lbl != null) {
            label = lbl;
        }
        if (cmt != null) {
            comment = cmt;
        }
    }

    public Node getNode()
    {
        return node;
    }

    public String getURI()
    {
        return node.toString();
    }

    public int getNodeCategory()
    {
        return node.getCategory();
    }

    public HashMap getLocalName()
    {
        return localname;
    }

    public HashMap getLabel()
    {
        return label;
    }

    public HashMap getComment()
    {
        return comment;
    }

    public void setLocalName(HashMap name)
    {
        if (name != null) {
            localname = name;
        }
    }

    public void setLabel(HashMap lbl)
    {
        if (lbl != null) {
            label = lbl;
        }
    }

    public void setComment(HashMap cmt)
    {
        if (cmt != null) {
            comment = cmt;
        }
    }
}
