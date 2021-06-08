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

class SignupViewModelTest {

    @Mock
    lateinit var userRepository: UserRepository
    private val testSchedulerProvider = TestSchedulerProvider()

    private lateinit var signupViewModel: SignupViewModel

    @Before
    fun setup() {
        // 이 메소드가 호출이 되어야 UserRepository의 Mock 객체가 생성
        MockitoAnnotations.openMocks(this)
//        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
//        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        signupViewModel = SignupViewModel(
            userRepository = userRepository,
            schedulerProvider = testSchedulerProvider
        )
    }

    @Test
    fun buttonEnable_true() {
        signupViewModel.check1State.onNext(true)
        signupViewModel.check2State.onNext(true)
        signupViewModel.check3State.onNext(true)
        Assert.assertTrue(signupViewModel.buttonState.value)
    }

    @Test
    fun buttonEnable_false() {
        signupViewModel.check1State.onNext(true)
        signupViewModel.check2State.onNext(false)
        signupViewModel.check3State.onNext(true)
        Assert.assertFalse(signupViewModel.buttonState.value)
    }

    @Test
    fun signupTest() {
        // UserRepository, SchecuProvider 의존하는 클래스 2개를 목킹
        // viewModel.signup() 에서 사용하는 Mock 클래스의 메소들들을 mocking -> Mockito.when()
        // Schedulers 에서 main을 사용하니 안드로이드 의존성 이라서 안되었음. trampolin 으로
        Mockito.`when`(userRepository.signup()).thenReturn(Completable.complete())

        // signup을 호출했어요
        signupViewModel.signup()

        // 실제 Scheduelrs는 시간이 흐른다, 또는 다음 메소드를 실행시킨다.
        // testScheduler는 내가 직접 호출하지 않는이상 다음 메소드를 실행시키지 않아요
        // triggerActions()을 호출하면 scheduelr가 한타임 진행된다
        testSchedulerProvider.testScheduler.triggerActions()

        Assert.assertFalse(signupViewModel.loadingState.value)
        Assert.assertEquals(signupViewModel.state.value, SignupViewModel.State.SIGNUP_SUCCESS)
        Mockito.verify(userRepository, Mockito.never()).saveUser()
    }

    @Test
    fun signupTest_error() {
        // UserRepository, SchecuProvider 의존하는 클래스 2개를 목킹
        // viewModel.signup() 에서 사용하는 Mock 클래스의 메소들들을 mocking -> Mockito.when()
        // Schedulers 에서 main을 사용하니 안드로이드 의존성 이라서 안되었음. trampolin 으로
        Mockito.`when`(userRepository.signup()).thenReturn(Completable.error(Exception()))

        // signup을 호출했어요
        signupViewModel.signup()

        // 실제 Scheduelrs는 시간이 흐른다, 또는 다음 메소드를 실행시킨다.
        // testScheduler는 내가 직접 호출하지 않는이상 다음 메소드를 실행시키지 않아요
        // triggerActions()을 호출하면 scheduelr가 한타임 진행된다
        testSchedulerProvider.testScheduler.triggerActions()

        Assert.assertFalse(signupViewModel.loadingState.value)
        Assert.assertNotEquals(signupViewModel.state.value, SignupViewModel.State.SIGNUP_SUCCESS)
        Mockito.verify(userRepository, Mockito.never()).saveUser()
    }
}
