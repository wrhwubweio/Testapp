package com.example.testapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.testapp.databinding.FragmentLoadImagesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class LoadImagesFragment : Fragment() {
    private lateinit var binding: FragmentLoadImagesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoadImagesBinding.inflate(inflater, container, false)

        binding.input.setOnClickListener{
            loadImageUrl(binding.editTextUrl.text.toString())
        }

        return binding.root
    }

    private fun loadImageUrl(imageUrl: String){
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
            try{
                val url = URL(imageUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()

                val inputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)

                saveImageToDevice(bitmap)

                withContext(Dispatchers.Main){
                    binding.imageOut.setImageBitmap(bitmap)
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun saveImageToDevice(bitmap: Bitmap){
        val directory = File(requireContext().filesDir, "images")
        if(!directory.exists()){
            directory.mkdirs()
        }

        val fileName = "image_" + System.currentTimeMillis() + ".jpg"
        val file = File(directory, fileName)

        try{
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}