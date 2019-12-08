package xyz.opendota.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import xyz.opendota.R
import xyz.opendota.data.LoginRepository
import xyz.opendota.di.applySchedulersSingle
import xyz.opendota.repositories.ILocalRepository
import xyz.opendota.utils.AppConstants.tokenize
import xyz.opendota.utils.base.BaseViewModel

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val localStorage: ILocalRepository
) : BaseViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        disposables.add(
            loginRepository.login(username, password)
                .compose(applySchedulersSingle())
                .subscribe(
                    { result ->
                        localStorage.token = tokenize("${username}:${password}")
                        _loginResult.value =
                            LoginResult(success = LoggedInUserView(displayName = result.data.username))
                        Log.d("LoginViewModel", result.toString())
                    },
                    { error ->
                        _loginResult.value = LoginResult(error = error.message)
                        Log.e("LoginViewModel", error.toString())
                    }
                )
        )
    }

    fun checkIfLoggedIn() {
        disposables.add(
            loginRepository.check(localStorage.token)
                .compose(applySchedulersSingle())
                .subscribe({ result ->
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = result.data.username))
                }, {
                    _loginResult.value = LoginResult(ignore = true)
                })
        )
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 1
    }
}
