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
package volgyerdo.neural.structure;

import java.io.Serializable;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class TestAnalyses implements Serializable{
    
    public static final long versionUID = 1L;

    public int sampleSize;
    public float errorGeometricMean;
    public float errorArithmeticMean;
    public float errorMedian;
    public float errorStandardDeviation;
    public float minError;
    public float maxError;
    public long runTime;
    public long runPeriod;
    
}
