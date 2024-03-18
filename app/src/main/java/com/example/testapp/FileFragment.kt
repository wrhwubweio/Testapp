package com.example.testapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.FragmentFileBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception

class FileFragment : Fragment() {
    data class PhotoItem(val dateTime: String)
    private val photoItems = ArrayList<PhotoItem>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentFileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileBinding.inflate(inflater, container, false)
        recyclerView = binding.recycler
        recyclerView.adapter = RecyclerFileAdapter(photoItems)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val directory = requireContext().getExternalFilesDir(null)?.resolve("photos")
        val fileName = "date.txt"
        val file = directory?.resolve(fileName)
        if(file != null && file.exists()){
            try{
                val inputStream = file.inputStream()
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String?

                while (reader.readLine().also { line = it } != null){
                    photoItems.add(PhotoItem(line ?: ""))
                }

                reader.close()
                inputStream.close()
            } catch (e: Exception){
                e.printStackTrace()
            }
        }

        return binding.root
    }
}

class RecyclerFileAdapter(photoItems: java.util.ArrayList<FileFragment.PhotoItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return TODO("Provide the return value")
    }

    override fun getItemCount(): Int {

        return TODO("Provide the return value")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

}