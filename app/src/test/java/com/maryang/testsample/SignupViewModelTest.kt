package com.maryang.testsample

import com.maryang.testsample.data.repository.UserRepository
import com.maryang.testsample.ui.signup.SignupViewModel
import com.maryang.testsample.util.provider.TestSchedulerProvider
import io.reactivex.rxjava3.core.Completable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SignupViewModelTest {

    private lateinit var viewModel: SignupViewModel

    @Mock
    lateinit var userRepository: UserRepository

    private val testSchedulerProvider = TestSchedulerProvider()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = SignupViewModel(userRepository, testSchedulerProvider)
    }

    @Test
    fun checkState_true() {
        viewModel.check1State.onNext(true)
        viewModel.check2State.onNext(true)
        viewModel.check3State.onNext(true)
        Assert.assertTrue(viewModel.buttonState.value)
    }

    @Test
    fun checkState_false() {
        viewModel.check1State.onNext(false)
        viewModel.check2State.onNext(true)
        viewModel.check3State.onNext(true)
        Assert.assertFalse(viewModel.buttonState.value)
    }

    @Test
    fun signup_success() {
        Mockito.`when`(userRepository.signup()).thenReturn(
            Completable.complete()
        )

        viewModel.signup()

        Assert.assertTrue(viewModel.loadingState.value)
        testSchedulerProvider.testScheduler.triggerActions()
        Assert.assertFalse(viewModel.loadingState.value)

        Assert.assertEquals(SignupViewModel.State.SIGNUP_SUCCESS, viewModel.state.value)
    }

    @Test
    fun signup_fail() {
        Mockito.`when`(userRepository.signup()).thenReturn(
            Completable.error(Exception())
        )

        viewModel.signup()

        Assert.assertTrue(viewModel.loadingState.value)
        testSchedulerProvider.testScheduler.triggerActions()
        viewModel.loadingState.test().assertValue(false)

        Assert.assertNotEquals(SignupViewModel.State.SIGNUP_SUCCESS, viewModel.state.value)
    }
}
