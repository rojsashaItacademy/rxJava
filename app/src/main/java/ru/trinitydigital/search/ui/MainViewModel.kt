package ru.trinitydigital.search.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import ru.trinitydigital.search.data.RetrofitInterface
import ru.trinitydigital.search.data.model.SearchModel
import java.util.concurrent.TimeUnit

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
                },{

                })
        )
    }

    fun search(text: String) {
        filmBehaviourSubject.onNext(text)
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