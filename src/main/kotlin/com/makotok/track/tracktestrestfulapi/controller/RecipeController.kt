package com.makotok.track.tracktestrestfulapi

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import com.makotok.track.tracktestrestfulapi.repository.RecipeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import com.makotok.track.tracktestrestfulapi.repository.Recipe
import com.makotok.track.tracktestrestfulapi.controller.RecipeResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/api/recipes")
class RecipeController(
    @Autowired
    val recipeRepository: RecipeRepository
) {

    @GetMapping
    fun getRecipes(): List<Recipe> {
        return recipeRepository.findAll(Sort(Direction.ASC, "id"))
    }

    @GetMapping("{id}")
    fun getRecipe(@PathVariable id: Long): Recipe {
        return recipeRepository.findById(id).orElse(null)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postRecipe(@RequestBody recipe: Recipe): RecipeResponse {
        val savedEntity = recipeRepository.save(recipe);
        return RecipeResponse("Recipe successfully created!", listOf(savedEntity))
    }

}