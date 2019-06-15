package com.makotok.track.tracktestrestfulapi.repository

import javax.persistence.Entity
import java.util.Date
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.Table
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * レシピエンティティです。
 */
@Table(name = "recipes")
@Entity
data class Recipe(

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @JsonProperty
    var title: String = "",

    @JsonProperty("making_time")
    var makingTime: String = "",

    @JsonProperty
    var serves: String = "",

    @JsonProperty
    var ingredients: String = "",

    @JsonProperty
    var cost: Long = 0,

    @JsonIgnore
    var createdAt: Date = Date(),

    @JsonIgnore
    var updatedAt: Date = Date()
)