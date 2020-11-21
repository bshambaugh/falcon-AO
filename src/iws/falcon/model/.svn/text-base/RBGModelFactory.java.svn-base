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

import iws.falcon.model.coordination.RuleFactory;
import iws.falcon.model.coordination.RuleList;
import iws.falcon.model.impl.RBGModelImpl;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class RBGModelFactory
{
    public static RBGModel createModel(String modelType)
    {
        RBGModelImpl model = new RBGModelImpl();
        if (modelType.equalsIgnoreCase("GMO_MODEL")) {
            RuleList rules = new RuleList();
            rules.add(RuleFactory.getAnnotationRule());
            rules.add(RuleFactory.getAllDifferentRule());
            rules.add(RuleFactory.getIntersectionOfRule());
            rules.add(RuleFactory.getListRule());
            rules.add(RuleFactory.getOntologyHeaderRule());
            rules.add(RuleFactory.getRedefinitionRule());
            rules.add(RuleFactory.getUnionOfRule());
            rules.add(RuleFactory.getEquivalentClassRule());
            rules.add(RuleFactory.getEquivalentPropertyRule());
            rules.add(RuleFactory.getSameAsRule());
            model.setCoordinationRuleList(rules);
            model.setClearClassType(true);
            model.setAddInstType(true);
        } else if (modelType.equalsIgnoreCase("VDOC_MODEL")) {
            RuleList rules = new RuleList();
            rules.add(RuleFactory.getAnnotationRule());
            rules.add(RuleFactory.getAllDifferentRule());
            rules.add(RuleFactory.getIntersectionOfRule());
            rules.add(RuleFactory.getListRule());
            rules.add(RuleFactory.getOntologyHeaderRule());
            rules.add(RuleFactory.getRedefinitionRule());
            rules.add(RuleFactory.getUnionOfRule());
            model.setCoordinationRuleList(rules);
            model.setClearClassType(true);
            model.setAddInstType(true);
        } else if (modelType.equalsIgnoreCase("STRING_MODEL")) {
            RuleList rules = new RuleList();
            rules.add(RuleFactory.getAnnotationRule());
            rules.add(RuleFactory.getDeprecatedRule());
            rules.add(RuleFactory.getOntologyHeaderRule());
            rules.add(RuleFactory.getRedefinitionRule());
            model.setCoordinationRuleList(rules);
            model.setClearClassType(true);
            model.setAddInstType(true);
        } else if (modelType.equalsIgnoreCase("PBM_MODEL")) {
            RuleList rules = new RuleList();
            rules.add(RuleFactory.getAnnotationRule());
            rules.add(RuleFactory.getDeprecatedRule());
            rules.add(RuleFactory.getOntologyHeaderRule());
            rules.add(RuleFactory.getRedefinitionRule());
            model.setCoordinationRuleList(rules);
            model.setInitHierarchy(true);
        }
        return model;
    }
}
