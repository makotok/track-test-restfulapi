package com.makotok.track.tracktestrestfulapi.controller

import com.makotok.track.tracktestrestfulapi.repository.Recipe
import com.fasterxml.jackson.annotation.JsonProperty

data class RecipeResponse (
    @JsonProperty("message")
    var message : String? = null,

    @JsonProperty("recipe")
    var recipes : List<Recipe>? = null,
    var requried : String? = null
)