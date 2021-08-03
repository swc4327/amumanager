package com.awesome.amumanager.ui.main.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amumanager.R
import com.awesome.amumanager.data.model.Menu
import com.awesome.amumanager.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amumanager.ui.main.viewmodel.MenuViewModel
import com.awesome.amumanager.ui.main.viewmodel.MenuViewModelFactory
import kotlinx.android.synthetic.main.activity_add_menu.*

class AddMenuActivity : AppCompatActivity() {

    var storeId : String = ""

    private lateinit var firebaseViewModel : FirebaseViewModel
    private lateinit var menuViewModel : MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        storeId = intent.getStringExtra("storeId").toString()
        initListener()

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        var factory = MenuViewModelFactory(storeId.toString())
        menuViewModel = ViewModelProvider(this, factory).get(MenuViewModel::class.java)

        observe()

    }

    private fun initListener() {
        add_menu_image.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions,
                        AddMenuActivity.PERMISSION_CODE
                    )
                }
                else {
                    pickImageFromGallery()
                }
            }
            else {
                pickImageFromGallery()
            }
        }

        close_add_menu.setOnClickListener {
            finish()
        }

        add_menu_button.setOnClickListener {
            firebaseViewModel.uploadTask(add_menu_image.drawable as BitmapDrawable, storeId+menu_name.text.toString())
        }
    }

    private fun observe() {
        menuViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                //storeViewModel.status.value = 0
                //setResult(Activity.RESULT_OK)
                finish()
            }
        })

        firebaseViewModel.taskToString.observe(this, Observer<String> {
            val menu = Menu(
                    null,
                    menu_name.text.toString(),
                    it,
                    menu_price.text.toString(),
                    menu_comment.text.toString(),
                    storeId)
            menuViewModel.addMenu(menu)
        })


    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )
    }

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000;
        //Permission code
        private const val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            add_menu_image.setImageURI(data?.data)
        }
    }
}