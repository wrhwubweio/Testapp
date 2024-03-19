package com.example.testapp.fragments

import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testapp.databinding.FragmentPhotoBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Suppress("DEPRECATION")
class PhotoFragment : Fragment(), SurfaceHolder.Callback {
    private lateinit var camera: Camera
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var surfaceView: SurfaceView
    private lateinit var binding: FragmentPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)

        if(checkCameraPermission()){
            camera = Camera.open()
            surfaceView = binding.surfaceView
            surfaceHolder = surfaceView.holder
            surfaceHolder.addCallback(this)
        }

        binding.takePhoto.setOnClickListener{
            val directory = File(requireContext().getExternalFilesDir(null), "photos")
            if(!directory.exists())
                directory.mkdirs()
            val fileName = "date.txt"
            val file = File(directory, fileName)

            val currentTime =
                SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())
            try{
                file.createNewFile()
                val fos = FileOutputStream(file, true)
                fos.write(currentTime.toByteArray())
                fos.write("\n".toByteArray())
                outInfo("File out: "  + directory +" "+ currentTime, true)
                fos.close()
            } catch (e: Exception){
                e.printStackTrace()
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setCameraDisplayOrientation()
    }

    private fun checkCameraPermission(): Boolean {
        val permission = android.Manifest.permission.CAMERA
        return if(ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED){
            true
        } else {
            outInfo("TryGet", true)
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), 1)
            false
        }
    }

    private fun setCameraDisplayOrientation(){
        val info = Camera.CameraInfo()
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info)
        val rotation = requireActivity().windowManager.defaultDisplay.rotation

        var degrees = 0
        when(rotation){
            Surface.ROTATION_0 -> degrees = 0
            Surface.ROTATION_90 -> degrees = 90
            Surface.ROTATION_180 -> degrees = 180
            Surface.ROTATION_270 -> degrees = 270
        }

        var result: Int
        if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
            result = (info.orientation + degrees) % 360
            result = (360 - result) % 360
        }
        else{
            result = (info.orientation - degrees) % 360
        }

        camera.setDisplayOrientation(result)
    }

    private fun outInfo(text: String, outToast: Boolean) {
        Log.d("MINE", text)
        if (!outToast) return
        val toast = Toast.makeText(
            requireActivity().applicationContext,
            text, Toast.LENGTH_SHORT
        )
        toast.show()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try{
            camera.setPreviewDisplay(holder)
            camera.startPreview()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder){
        camera.stopPreview()
        camera.release()
    }
}


