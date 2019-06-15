package com.makotok.track.tracktestrestfulapi.repository

import javax.persistence.Entity
import java.util.Date
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.Table
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.GenerationType
import javax.validation.constraints.NotNull

/**
 * レシピエンティティです。
 */
@Table(name = "recipes")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Recipe(

    @field:JsonProperty
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotNull
    @field:JsonProperty
    var title: String? = null,

    @field:NotNull
    @field:JsonProperty("making_time")
    var makingTime: String? = null,

    @field:NotNull
    @field:JsonProperty
    var serves: String? = null,

    @field:NotNull
    @field:JsonProperty
    var ingredients: String? = null,

    @field:NotNull
    @field:JsonProperty
    var cost: Long? = null,

    @field:JsonIgnore
    var createdAt: Date = Date(),

    @field:JsonIgnore
    var updatedAt: Date = Date()
)