/*
 * Copyright 2015 the original author or authors.
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
package com.toh.us.CivilizaSim.GameObjects.People.NameGen;

import java.util.ArrayList;
import java.util.List;

/**
 * NameMachine generates random names.
 *
 * @author A.J. Brown <a href="mailto:aj@ajbrown.org">aj@ajbrown.org</a>
 */
public class NameMachine {

    private List<Name> names = new ArrayList<>();

    /**
     * Generate random names and send List.
     *
     * @param numNames first argument is the number of names to generate
     * @param gen the second argument is the gender
     */
    public NameMachine(int numNames, String gen) {

        if (numNames == 0) {
            System.err.print("You must specify the number of names to generate");
        }

        if (numNames < 0) {
            throw new IllegalArgumentException("Name count must be a positive number.");
        }

        // gender can be specified in either plural or singular form.
        Gender gender = Gender.valueOf(gen.replace("s", "").toUpperCase());


        NameGenerator generator = new NameGenerator();
        for (int i = 0; i < numNames; i++) {
            names.add(generator.generateName(gender));
        }

    }

    public List<Name> getNames() {
        return this.names;
    }
}
