package com.tiobe.plugins.intellij.utilities

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class TicsXML @JsonCreator constructor(
    @JsonProperty("version") val version: String,
    @JsonProperty("format") val format: String,
    @JsonProperty("Violations") val violations: List<Violation>,
    @JsonProperty("SolvedViolations") val solvedViolations: List<Violation>
)

data class Violation @JsonCreator constructor(
    @JsonProperty("File") val file: String,
    @JsonProperty("Line") val line: Int,
    @JsonProperty("Synopsis") val synopsis: String,
    @JsonProperty("Suppressed") val suppressed: TicsBoolean,
    @JsonProperty("Language") val language: String,
    @JsonProperty("RuleId") val ruleId: String,
    @JsonProperty("Category") val category: String,
    @JsonProperty("Level") val level: Int,
    @JsonProperty("Message") val message: String?,
    @JsonProperty("Reference") val reference: String?,
)

enum class TicsBoolean(val value: Boolean) {
    Yes(true),
    No(false)
}