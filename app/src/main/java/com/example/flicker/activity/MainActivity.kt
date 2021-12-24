package com.example.flicker.activity

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flicker.databinding.ActivityMainBinding
import com.example.flicker.model.Photo
import com.example.flicker.model.SearchModel
import com.example.flicker.recyclerview.MyAdapter
import com.example.headsupapp.services.ApiClient
import com.example.headsupapp.services.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var imageList: ArrayList<Photo>
    private lateinit var searchText: String

    private val apiInterface by lazy { ApiClient().getClient().create(ApiInterface::class.java) }
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, "+10")
    val API_KEY = "5d58de8e8b2ec139a77a1369a6110c28"
    var searchBy = "text"
    var numberOfImage = 1

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setup UI
        supportActionBar!!.hide()
        setUpSpinner()
        setUpRecyclerview()

        // Spinner action
        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (list[position]!="+10") {
                    numberOfImage = list[position] as Int
                } else {
                    numberOfImage = 0
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        // Search bar Action
        binding.searchBar.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    imageList = arrayListOf()
                    searchText = binding.searchBar.text.toString()
                    getPhotos()
                    hideSoftKeyboard()
                    binding.searchBar.clearFocus()
                    binding.searchBar.isCursorVisible = false
                    return true
                }
                return false
            }
        })
        binding.searchIcon.setOnClickListener {
            imageList = arrayListOf()
            searchText = binding.searchBar.text.toString()
            getPhotos()
            hideSoftKeyboard()
            binding.searchBar.clearFocus()
            binding.searchBar.isCursorVisible = false
        }

        // Buttons Actions
        binding.tagButton.setOnClickListener {
            binding.tagButton.setBackgroundColor(Color.parseColor("#fbd141"))
            binding.tagButton.setTextColor(Color.WHITE)
            binding.textButton.setBackgroundColor(Color.parseColor("#e2e7f1"))
            binding.textButton.setTextColor(Color.parseColor("#515151"))
            searchBy = "tags"
        }
        binding.textButton.setOnClickListener {
            binding.textButton.setBackgroundColor(Color.parseColor("#fbd141"))
            binding.textButton.setTextColor(Color.WHITE)
            binding.tagButton.setBackgroundColor(Color.parseColor("#e2e7f1"))
            binding.tagButton.setTextColor(Color.parseColor("#515151"))
            searchBy = "text"
        }


    }

    private fun setUpSpinner() {
        val spinner = binding.spinner
        val spinnerAdapter = ArrayAdapter(
            this,
            R.layout.simple_spinner_item, list
        )
        spinner.adapter = spinnerAdapter
    }

    private fun setUpRecyclerview() {
        imageList = arrayListOf()
        recyclerView = binding.recyclerview
        myAdapter = MyAdapter(this, imageList)
        recyclerView.adapter = myAdapter
    }

    private fun Activity.hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    // GET API data
    private fun getPhotos() {
        apiInterface.getApiData("?method=flickr.photos.search&api_key=$API_KEY&format=json&nojsoncallback=1&extras=url_h,tags&$searchBy=$searchText")
            .enqueue(object : Callback<SearchModel> {
                override fun onResponse(call: Call<SearchModel>, response: Response<SearchModel>) {

                    val response = response.body()
                    val photos = response!!.photos.photo
                    for (photo in photos) {
                        println(photo.url_h)
                    }

                    if (numberOfImage != 0 ){
                        for (i in 1..numberOfImage){
                            imageList.add(photos[i])
                        }
                    } else {
                        imageList = photos as ArrayList<Photo>
                    }
                    myAdapter.update(imageList)
                    recyclerView.scheduleLayoutAnimation()

                }

                override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                    Log.d("MainActivity", "ERROR: ${t.message}")
                }
            })
    }
}