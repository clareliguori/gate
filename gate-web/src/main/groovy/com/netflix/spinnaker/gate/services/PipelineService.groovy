/*
 * Copyright 2014 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.netflix.spinnaker.gate.services

import com.netflix.spinnaker.gate.services.internal.EchoService
import com.netflix.spinnaker.gate.services.internal.MayoService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@CompileStatic
@Component
@Slf4j
class PipelineService {
  @Autowired(required = false)
  MayoService mayoService

  @Autowired(required = false)
  EchoService echoService

  void deleteForApplication(String applicationName, String pipelineName) {
    mayoService.deletePipelineConfig(applicationName, pipelineName)
  }

  void save(Map pipeline) {
    mayoService.savePipelineConfig(pipeline)
  }

  void move(Map moveCommand) {
    mayoService.move(moveCommand)
  }

  void trigger(String application, String pipelineName, String user) {
    echoService.postEvent(EchoService.EventBuilder.builder().withSource('gate').withType('manualPipelineTrigger')
        .withContent([application: application, name: pipelineName, user: user]).build())
  }
}
