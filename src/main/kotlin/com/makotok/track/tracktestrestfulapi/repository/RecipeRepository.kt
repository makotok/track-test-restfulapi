package com.makotok.track.tracktestrestfulapi.repository

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

/**
 * Recipeエンティティのリポジトリです。
 */
@Repository
@Transactional
interface RecipeRepository : JpaRepository<Recipe, Long> {
}