/*
 * Copyright 2012-2017 the original author or authors.
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

package org.springframework.boot.actuate.autoconfigure.trace;

import org.junit.Test;

import org.springframework.boot.actuate.trace.InMemoryTraceRepository;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link TraceRepositoryAutoConfiguration}.
 *
 * @author Phillip Webb
 */
public class TraceRepositoryAutoConfigurationTests {

	@Test
	public void configuresInMemoryTraceRepository() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				TraceRepositoryAutoConfiguration.class);
		assertThat(context.getBean(InMemoryTraceRepository.class)).isNotNull();
		context.close();
	}

	@Test
	public void skipsIfRepositoryExists() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				Config.class, TraceRepositoryAutoConfiguration.class);
		assertThat(context.getBeansOfType(InMemoryTraceRepository.class)).isEmpty();
		assertThat(context.getBeansOfType(TraceRepository.class)).hasSize(1);
		context.close();
	}

	@Configuration
	public static class Config {

		@Bean
		public TraceRepository traceRepository() {
			return mock(TraceRepository.class);
		}

	}

}
