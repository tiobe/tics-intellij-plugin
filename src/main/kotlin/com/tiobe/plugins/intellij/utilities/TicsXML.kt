package com.tiobe.plugins.intellij.utilities

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TicsXML @JsonCreator constructor(
    @param:JsonProperty("version") val version: String,
    @param:JsonProperty("format") val format: String,
    @param:JsonProperty("Violations") val violations: List<Violation>,
    @param:JsonProperty("SolvedViolations") val solvedViolations: List<Violation>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Violation @JsonCreator constructor(
    @param:JsonProperty("File") val file: String,
    @param:JsonProperty("Line") val line: Int,
    @param:JsonProperty("Synopsis") val synopsis: String,
    @param:JsonProperty("Suppressed") val suppressed: TicsBoolean,
    @param:JsonProperty("Language") val language: String,
    @param:JsonProperty("RuleId") val ruleId: String,
    @param:JsonProperty("Category") val category: String,
    @param:JsonProperty("Level") val level: Int,
    @param:JsonProperty("Message") val message: String?,
    @param:JsonProperty("Reference") val reference: String?,
    @param:JsonProperty("New") val new: TicsBoolean?,
)

enum class TicsBoolean(val value: Boolean) {
    Yes(true),
    No(false)
}