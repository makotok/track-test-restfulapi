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
import javax.validation.Valid
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.http.ResponseEntity
import java.util.Date
import org.springframework.validation.annotation.Validated
import org.springframework.validation.BindingResult

/**
 * RecipeリソースのためのRestコントローラです。
 */
@RestController
@RequestMapping("/api/recipes")
open class RecipeController(
    val recipeRepository: RecipeRepository
) {

    @GetMapping
    open fun getRecipes(): List<Recipe> {
        return recipeRepository.findAll(Sort(Direction.ASC, "id"))
    }

    @GetMapping("{id}")
    open fun getRecipe(@PathVariable id: Long): Recipe {
        return recipeRepository.findById(id).orElse(Recipe())
    }

    @PostMapping
    @Transactional(rollbackFor = arrayOf(Throwable::class))
    open fun postRecipe(@RequestBody @Valid recipe: Recipe, bindingResult: BindingResult): ResponseEntity<RecipeResponse> {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                .body(RecipeResponse("Recipe creation failed!", "title, making_time, serves, ingredients, cost"))
        }
        val savedEntity = recipeRepository.save(recipe);
        return ResponseEntity.ok(RecipeResponse("Recipe successfully created!", null, listOf(savedEntity)))
    }

    @PatchMapping("{id}")
    @Transactional(rollbackFor = arrayOf(Throwable::class))
    open fun patchRecipe(@PathVariable id: Long, @RequestBody recipe: Recipe): ResponseEntity<RecipeResponse> {
        val entity = recipeRepository.findById(id).orElse(Recipe())
        if (entity.id == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RecipeResponse("No Recipe found"));
        }
        entity.title = recipe.title ?: entity.title
        entity.makingTime = recipe.makingTime ?: entity.makingTime
        entity.serves = recipe.serves ?: entity.serves
        entity.ingredients = recipe.ingredients ?: entity.ingredients
        entity.cost = recipe.cost ?: entity.cost
        entity.updatedAt = Date()
        recipeRepository.save(entity)

        return ResponseEntity.ok(RecipeResponse("Recipe successfully updated!", null, listOf(entity)))
    }

    @DeleteMapping("{id}")
    open fun deleteRecipe(@PathVariable id: Long): ResponseEntity<RecipeResponse> {
        val entity = recipeRepository.findById(id).orElse(Recipe())
        if (entity.id == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RecipeResponse("No Recipe found"));
        }
        recipeRepository.deleteById(id)
        return ResponseEntity.ok(RecipeResponse("Recipe successfully removed!"))
    }
}