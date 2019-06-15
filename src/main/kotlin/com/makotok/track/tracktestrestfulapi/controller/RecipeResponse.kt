package com.makotok.track.tracktestrestfulapi.controller

import com.makotok.track.tracktestrestfulapi.repository.Recipe
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RecipeResponse (
    @JsonProperty("message")
    var message : String? = null,

    @JsonProperty("requried")
    var requried : String? = null,

    @JsonProperty("recipe")
    var recipes : List<Recipe>? = null

)