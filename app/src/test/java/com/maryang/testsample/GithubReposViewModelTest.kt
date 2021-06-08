package com.maryang.testsample

import com.maryang.testsample.data.repository.GithubRepository
import com.maryang.testsample.entity.GithubRepo
import com.maryang.testsample.ui.repos.GithubReposViewModel
import com.maryang.testsample.util.provider.TestSchedulerProvider
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GithubReposViewModelTest {

    private lateinit var viewModel: GithubReposViewModel

    @Mock
    lateinit var githubRepository: GithubRepository

    private val testSchedulerProvider = TestSchedulerProvider()
    private val repos: List<GithubRepo> = listOf(GithubRepo())
    private val searchText = "searchText"

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        Mockito.`when`(githubRepository.searchGithubRepos(Mockito.anyString()))
            .thenReturn(Single.just(repos))
        Mockito.`when`(githubRepository.checkStar(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Completable.complete())

        viewModel = GithubReposViewModel(githubRepository, testSchedulerProvider)
        viewModel.onCreate()
    }
}
