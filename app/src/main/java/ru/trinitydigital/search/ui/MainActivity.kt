package ru.trinitydigital.search.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.trinitydigital.search.R
import ru.trinitydigital.search.data.RetrofitBuilder
import ru.trinitydigital.search.data.model.SearchModel
import ru.trinitydigital.search.databinding.ActivityMainBinding
import ru.trinitydigital.search.utils.doAfterTextChanged

class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etSearch.doAfterTextChanged {
            vm.search(it)
        }
        vm.data.observe(this, Observer {
            Toast.makeText(this, "searched", Toast.LENGTH_SHORT).show()
        })
        vm.progress.observe(this, Observer {
            if (it) binding.progress.visibility = View.VISIBLE
            else binding.progress.visibility = View.GONE
        })
    }
}