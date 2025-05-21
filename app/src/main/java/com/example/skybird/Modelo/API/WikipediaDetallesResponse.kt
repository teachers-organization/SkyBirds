package com.example.skybird.Modelo.API


data class WikiMobileResponse(
    val lead: LeadSection,
    val remaining: RemainingSections
)

data class LeadSection(
    val sections: List<Section>
)

data class RemainingSections(
    val sections: List<Section>
)

data class Section(
    val id: Int,
    val heading: String?,
    val text: String //HTML content
)
