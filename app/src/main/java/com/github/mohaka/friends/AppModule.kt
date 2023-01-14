package com.github.mohaka.friends

import com.github.mohaka.friends.domain.user.InMemoryUserCatalog
import com.github.mohaka.friends.domain.user.UserRepository
import com.github.mohaka.friends.domain.validation.RegexCredentialsValidator
import com.github.mohaka.friends.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
	single { InMemoryUserCatalog() }
	factory { RegexCredentialsValidator() }
	factory { UserRepository(userCatalog = get()) }

	viewModel { SignUpViewModel(credentialsValidator = get(), userRepository = get()) }
}