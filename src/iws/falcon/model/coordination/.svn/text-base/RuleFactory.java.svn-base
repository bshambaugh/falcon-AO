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

import iws.falcon.model.coordination.rule.AllDifferent;
import iws.falcon.model.coordination.rule.Annotation;
import iws.falcon.model.coordination.rule.EquivalentClass;
import iws.falcon.model.coordination.rule.EquivalentProperty;
import iws.falcon.model.coordination.rule.IntersectionOf;
import iws.falcon.model.coordination.rule.List;
import iws.falcon.model.coordination.rule.OntologyHeader;
import iws.falcon.model.coordination.rule.Redefinition;
import iws.falcon.model.coordination.rule.SameAs;
import iws.falcon.model.coordination.rule.UnionOf;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class RuleFactory
{
    public static Coordinator getAnnotationRule()
    {
        return new Annotation();
    }

    public static Coordinator getDeprecatedRule()
    {
        return new iws.falcon.model.coordination.rule.Deprecated();
    }

    public static Coordinator getListRule()
    {
        return new List();
    }

    public static Coordinator getOntologyHeaderRule()
    {
        return new OntologyHeader();
    }

    public static Coordinator getIntersectionOfRule()
    {
        return new IntersectionOf();
    }

    public static Coordinator getAllDifferentRule()
    {
        return new AllDifferent();
    }

    public static Coordinator getUnionOfRule()
    {
        return new UnionOf();
    }

    public static Coordinator getRedefinitionRule()
    {
        return new Redefinition();
    }

    public static Coordinator getEquivalentClassRule()
    {
        return new EquivalentClass();
    }

    public static Coordinator getEquivalentPropertyRule()
    {
        return new EquivalentProperty();
    }

    public static Coordinator getSameAsRule()
    {
        return new SameAs();
    }
}
