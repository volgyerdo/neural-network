/*
 * Copyright 2021 Volgyerdo Nonprofit Kft.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package volgyerdo.neural.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import volgyerdo.neural.structure.TestAnalyses;
import volgyerdo.neural.structure.TestRowAnalyses;
import volgyerdo.neural.structure.TestData;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class TestAnalysesLogic {

    public static TestAnalyses analyze(TestData data) {
        TestAnalyses analyses = new TestAnalyses();
        if (data == null || data.errors.isEmpty()) {
            return analyses;
        }
        List<Float> errors = new ArrayList<>(data.errors);
        Collections.sort(errors);
        double sum = 0;
        double product = 1;
        for (float error : errors) {
            sum += error;
            product *= error;
        }
        analyses.errorArithmeticMean = (float) (sum / errors.size());
        analyses.errorGeometricMean = (float) (Math.pow(product, 1. / errors.size()));
        analyses.errorMedian = (float) (errors.get(errors.size() / 2));
        return analyses;
    }

    public static TestRowAnalyses rowAnalyze(TestData data) {
        TestRowAnalyses analyses = new TestRowAnalyses();
        if (data == null || data.errors.isEmpty()) {
            return analyses;
        }
        List<Float> errors = new ArrayList<>(data.errors);
        analyses.analyses = new ArrayList<>(errors.size());
        TestData testData = new TestData();
        for (int i = 1; i <= errors.size(); i++) {
            List<Float> subList = errors.subList(Math.max(0, i - NetworkConstants.ANALYSES_ROW_LENGTH), i);
            testData.errors = subList;
            analyses.analyses.add(analyze(testData));
        }
        return analyses;
    }

    private TestAnalysesLogic() {
    }

}
