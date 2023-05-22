package com.pride.test.flow.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pride.test.flow.databinding.ActivityMainBinding
import com.pride.test.flow.room.MyEntity
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainVM: MainViewModel by viewModels()
    private val adapter = MainAdapter()
    private var imgBit:Bitmap? = null
    private var fileImg : File? = null
    private var fileUri = Uri.EMPTY
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val getUriFromCamera =
            if (it.resultCode == RESULT_OK && it.data != null) it.data!!.data else null
        if (getUriFromCamera != null) {
            try {
                val inputStream: InputStream? =
                    contentResolver.openInputStream(getUriFromCamera)
                val uotputStream = FileOutputStream(fileImg)
                copiStreaming(inputStream, uotputStream)
                uotputStream.close()
                inputStream?.close()
                saveImg(fileImg!!.path)
            } catch (errFile: Exception) {
                errFile.printStackTrace()
            }
        } else {
            if (fileUri != Uri.EMPTY && fileImg != null) {
                saveImg(fileImg!!.absolutePath)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mainVM.listOfNames.observe(this) {
            adapter.listItems = it
        }
        binding.button.setOnClickListener {
            mainVM.insert(MyEntity(null, binding.editName.text.toString(),imgBit))
        }
        binding.imgUser.setOnClickListener {
            insertNewImg()
        }
        binding.rcName.adapter = adapter
        binding.rcName.layoutManager = LinearLayoutManager(this)
    }

    private fun insertNewImg() {
        fileImg=null
        fileUri=Uri.EMPTY
        val mediaIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (mediaIntent.resolveActivity(packageManager)!=null) {
            try {
                fileImg = File.createTempFile(
                    "User_" + SimpleDateFormat(
                        "HHmmss_ddMMyyyy",
                        Locale.getDefault()
                    ).format(Date()),
                    ".jpg",
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                )
            } catch (errorFile: Exception) {
                errorFile.printStackTrace()
            }
            fileImg.let {
                if (it != null) {
                    fileUri = FileProvider.getUriForFile(
                        this@MainActivity,
                        "geting.image",
                        it
                    )
                }
            }
            mediaIntent.apply {
                putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            val prevLastIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            val lastIntent = Intent(Intent.ACTION_CHOOSER).apply {
                putExtra(Intent.EXTRA_INTENT, mediaIntent)
                putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(prevLastIntent))
            }
            resultLauncher.launch(lastIntent)
        }
    }

    @Throws(IOException::class)
    fun copiStreaming(inp: InputStream?, out: OutputStream) {
        val buffer = ByteArray(1024)
        var sdgdfg: Int
        while (inp?.read(buffer).also { sdgdfg = it!! } != -1) {
            out.write(buffer, 0, sdgdfg)
        }
    }

    private fun saveImg(strImg: String) {
        imgBit = BitmapFactory.decodeFile(strImg)
        Glide.with(this)
            .load(imgBit)
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imgUser)

    }
}