package ru.trinitydigital.search.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import ru.trinitydigital.search.data.RetrofitInterface
import ru.trinitydigital.search.data.model.Search
import ru.trinitydigital.search.data.model.Search2
import ru.trinitydigital.search.data.model.SearchModel
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.BiFunction

class MainViewModel(private val network: RetrofitInterface) : ViewModel() {

    val progress = MutableLiveData<Boolean>()

    private var disposable: Disposable? = null
    private var compositeDisposable = CompositeDisposable()

    private val filmBehaviourSubject = BehaviorSubject.create<String>()

    val data = MutableLiveData<SearchModel>()

    init {
        compositeDisposable.add(
            filmBehaviourSubject
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe({
                    searchFilm(it)
                }, {

                })
        )
        searchRepeat()
    }

    fun search(text: String) {
        filmBehaviourSubject.onNext(text)
    }

    fun searchRepeat() {
        compositeDisposable.add(
            network.searchFilm2("4f391043", "dar")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen {
                    it.delay(5, TimeUnit.SECONDS)
                }
                .takeUntil {
                    return@takeUntil it.search != null
                }
                .subscribe({
                    data.postValue(it)
                }, {
                    Log.d("adasdasd", "asdasdasdasdsad")
                })
        )
    }

    private fun searchFilm2(text: String) {
        val res = network.searchFilm2("4f391043", text)
        val res2 = network.searchFilm2("4f391043", text)
        compositeDisposable.add(
            Observable.concat(res, res2)
                .first(SearchModel(response = "", search = listOf(), totalResults = ""))
                .subscribe({

                }, {

                })

        )

    }

    private fun searchFilm3(text: String) {
        val res = network.searchFilm2("4f391043", text)
        val res2 = network.searchFilm2("4f391043", text)
        compositeDisposable.add(
            Observable.zip(res, res2,
                { t1, t2 ->
                    val list = arrayListOf<Search>()
                    t1.search?.let { list.addAll(it) }
                    t2.search?.let { list.addAll(it) }
                    SearchModel(
                        totalResults = t1.totalResults,
                        search = list,
                        response = t1.response
                    )
                })
                .map {

                }
                .subscribe({
                    Log.d("adasdasd", "asdasdasdasdsad")
                }, {
                    Log.d("adasdasd", "asdasdasdasdsad")
                })

        )

    }

    private fun searchFilm(text: String) {
        compositeDisposable.add(network.searchFilm("4f391043", text)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                progress.postValue(true)
            }
            .doFinally {
                progress.postValue(false)
            }
//            .flatMap {
//                return@flatMap network.searchFilm("4f391043", text)
//            }
//            .map {
//                val result = it.search
//                return@map result
//            }
//            .map {
//                val data = it.map { Search2(
//                    poster = it.poster,
//                    title = "sasha",
//                    type = it.type,
//                    year = it.year,
//                    imdbID = it.imdbID
//                ) }
//                return@map data
//            }
//            .filter {
//                return@filter it.totalResults.toInt() > 142
//            }
            .retryWhen { throwable -> throwable.take(3).delay(1, TimeUnit.SECONDS) }
            .subscribe({
                data.postValue(it)
                Log.d("____ADssadsad", "success")
            }, {
                Log.d("____ADssadsad", it.localizedMessage)
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        compositeDisposable.dispose()
    }
}