package com.woodny.weathernewsapplication.module

import com.woodny.weathernewsapplication.model.repository.ClientApiRepository
import com.woodny.weathernewsapplication.model.repository.ClientApiRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// 同じabstractクラス内でBindsとProvidesを一緒に定義すると、以下のエラーが発生するため、
// ClientApi側は別クラスに移動
// Module may not contain both non-static and abstract binding methods

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindClientApiRepository(repository: ClientApiRepositoryImpl): ClientApiRepository
}