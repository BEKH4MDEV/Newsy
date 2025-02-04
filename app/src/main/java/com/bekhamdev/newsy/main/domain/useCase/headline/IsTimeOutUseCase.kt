package com.bekhamdev.newsy.main.domain.useCase.headline

import com.bekhamdev.newsy.main.domain.repository.HeadlineRepository
import javax.inject.Inject

class IsTimeOutUseCase @Inject constructor(
    private val repository: HeadlineRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.isTimeOut()
    }
}