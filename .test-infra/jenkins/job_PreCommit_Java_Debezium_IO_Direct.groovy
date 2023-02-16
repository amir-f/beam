/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import PrecommitJobBuilder

PrecommitJobBuilder builder = new PrecommitJobBuilder(
    scope: this,
    nameBase: 'Java_Debezium_IO_Direct',
    gradleTasks: [
      ':sdks:java:io:debezium:build',
      ':sdks:java:io:debezium:expansion-service:build',
      ':sdks:java:io:debezium:integrationTest',
    ],
    gradleSwitches: [
      '-PdisableSpotlessCheck=true',
      '-PdisableCheckStyle=true'
    ], // spotless checked in separate pre-commit
    triggerPathPatterns: [
      '^runners/google-cloud-dataflow-java/.*$',
      '^sdks/java/core/src/main/.*$',
      '^sdks/java/io/common/.*$',
      '^sdks/java/io/debezium/.*$',
    ],
    timeoutMins: 60,
    )
builder.build {
  publishers {
    archiveJunit('**/build/test-results/**/*.xml')
  }
}