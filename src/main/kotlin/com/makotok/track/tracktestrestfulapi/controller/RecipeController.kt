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

    /**
     * RecipesリソースのGetエンドポイントです。
     */
    @GetMapping
    open fun getRecipes(): RecipeResponse {
        val recipes = recipeRepository.findAll(Sort(Direction.ASC, "id"))
        return RecipeResponse(null, null, recipes)
    }

    /**
     * RecipeリソースのGetエンドポイントです。
     */
    @GetMapping("{id}")
    open fun getRecipe(@PathVariable id: Long): RecipeResponse {
        val recipe = recipeRepository.findById(id).orElse(Recipe())
        return RecipeResponse("Recipe details by id", null, listOf(recipe))
    }

    /**
     * RecipeリソースのPOSTエンドポイントです。
     */
    @PostMapping
    @Transactional(rollbackFor = arrayOf(Throwable::class))
    open fun postRecipe(@RequestBody @Valid recipe: Recipe, bindingResult: BindingResult): ResponseEntity<RecipeResponse> {
        // バリデーションチェックを実施
        if (bindingResult.hasErrors()) {
            // TODO:なぜかテストだと200を返さないといけないので、とりあえず200を返しておく
            return ResponseEntity.ok()
                .body(RecipeResponse("Recipe creation failed!", "title, making_time, serves, ingredients, cost"))
        }
        // エンティティを保存
        val savedEntity = recipeRepository.save(recipe);

        // レスポンスを返す
        return ResponseEntity.ok(RecipeResponse("Recipe successfully created!", null, listOf(savedEntity)))
    }

    /**
     * RecipeリソースのPATCHエンドポイントです。
     */
    @PatchMapping("{id}")
    @Transactional(rollbackFor = arrayOf(Throwable::class))
    open fun patchRecipe(@PathVariable id: Long, @RequestBody recipe: Recipe): ResponseEntity<RecipeResponse> {
        val entity = recipeRepository.findById(id).orElse(Recipe())
        if (entity.id == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RecipeResponse("No Recipe found"));
        }

        // エンティティを更新・保存
        entity.title = recipe.title ?: entity.title
        entity.makingTime = recipe.makingTime ?: entity.makingTime
        entity.serves = recipe.serves ?: entity.serves
        entity.ingredients = recipe.ingredients ?: entity.ingredients
        entity.cost = recipe.cost ?: entity.cost
        entity.updatedAt = Date()
        recipeRepository.save(entity)

        // レスポンスを返す
        return ResponseEntity.ok(RecipeResponse("Recipe successfully updated!", null, listOf(entity)))
    }

    /**
     * RecipeリソースのDELETEエンドポイントです。
     */
    @DeleteMapping("{id}")
    open fun deleteRecipe(@PathVariable id: Long): ResponseEntity<RecipeResponse> {
        // エンティティの取得および存在チェック
        val entity = recipeRepository.findById(id).orElse(Recipe())
        if (entity.id == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RecipeResponse("No Recipe found"));
        }
        // エンティティの削除
        recipeRepository.deleteById(id)

        // レスポンスを返す
        return ResponseEntity.ok(RecipeResponse("Recipe successfully removed!"))
    }
}