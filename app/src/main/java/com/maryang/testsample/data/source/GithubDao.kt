package com.maryang.testsample.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maryang.testsample.entity.GithubRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


@Dao
interface GithubDao {
    @Query("SELECT * FROM githubrepo")
    fun selectAll(): Single<List<GithubRepo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: GithubRepo): Completable
}
