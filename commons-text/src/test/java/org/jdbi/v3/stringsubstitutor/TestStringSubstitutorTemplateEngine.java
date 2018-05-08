/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.stringsubstitutor;

import java.util.HashMap;
import java.util.Map;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.statement.TemplateEngine;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TestStringSubstitutorTemplateEngine {
    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    private StatementContext ctx;

    private final Map<String, Object> attributes = new HashMap<>();

    @Before
    public void before() {
        when(ctx.getAttributes()).thenReturn(attributes);
    }

    @Test
    public void testDefaults() {
        attributes.put("name", "foo");

        assertThat(StringSubstitutorTemplateEngine.defaults().render("create table ${name};", ctx))
            .isEqualTo("create table foo;");
    }

    @Test
    public void testCustomPrefixSuffix() {
        attributes.put("name", "foo");

        TemplateEngine engine = StringSubstitutorTemplateEngine.between('<', '>');

        assertThat(engine.render("create table <name>;", ctx))
            .isEqualTo("create table foo;");
    }

    @Test
    public void testEscapeCharacter() {
        attributes.put("name", "foo");

        TemplateEngine engine = StringSubstitutorTemplateEngine.between('<', '>', '@');

        assertThat(engine.render("create table @<name>;", ctx))
            .isEqualTo("create table <name>;");
    }
}