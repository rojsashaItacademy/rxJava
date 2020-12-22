package ru.trinitydigital.search.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import io.reactivex.Observable
import io.reactivex.Observable.fromArray
import io.reactivex.Single
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.trinitydigital.search.R
import ru.trinitydigital.search.data.RetrofitBuilder
import ru.trinitydigital.search.data.model.SearchModel
import ru.trinitydigital.search.databinding.ActivityMainBinding
import ru.trinitydigital.search.utils.doAfterTextChanged
import java.util.*

class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etSearch.doAfterTextChanged {
//            vm.search(it)
        }

        val s = Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9)



        s.subscribe({
            Log.d("asdsadasdasd", it.toString())
        }, {

        }, {

        })
        vm.data.observe(this, Observer {
            Toast.makeText(this, "searched", Toast.LENGTH_SHORT).show()
        })
        vm.progress.observe(this, Observer {
            if (it) binding.progress.visibility = View.VISIBLE
            else binding.progress.visibility = View.GONE
        })
    }
}