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
package volgyerdo.math.primitive;

import java.util.Objects;


@FunctionalInterface
public interface ShortUnaryOperator {


    short applyAsShort(short operand);


    default ShortUnaryOperator compose(ShortUnaryOperator before) {
        Objects.requireNonNull(before);
        return (short v) -> applyAsShort(before.applyAsShort(v));
    }

    default ShortUnaryOperator andThen(ShortUnaryOperator after) {
        Objects.requireNonNull(after);
        return (short t) -> after.applyAsShort(applyAsShort(t));
    }

    static ShortUnaryOperator identity() {
        return t -> t;
    }
}
