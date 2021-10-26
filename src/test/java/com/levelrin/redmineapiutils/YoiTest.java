/*
 * Copyright (c) 2021 Rin (https://www.levelrin.com)
 *
 * This file has been created under the terms of the MIT License.
 * See the details at https://github.com/levelrin/redmine-api-utils/blob/main/LICENSE
 */

package com.levelrin.redmineapiutils;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
final class YoiTest {

    @Test
    public void checkYoi() {
        MatcherAssert.assertThat(
            new Yoi().temp(),
            CoreMatchers.equalTo("Yoi Yoi")
        );
    }

}
