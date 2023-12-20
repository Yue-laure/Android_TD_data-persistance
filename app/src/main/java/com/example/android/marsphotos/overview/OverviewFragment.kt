/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.marsphotos.overview

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.android.marsphotos.R
import com.example.android.marsphotos.data.MarsPhoto
import com.example.android.marsphotos.databinding.FragmentOverviewBinding
import android.Manifest
import android.provider.MediaStore
import android.widget.Toast

/**
 * This fragment shows the the status of the Mars photos web services transaction.
 */
class OverviewFragment : Fragment() ,PhotoGridAdapter.OnItemClickListener{

    private val viewModel: OverviewViewModel by viewModels()

    private val binding: FragmentOverviewBinding by lazy {
        FragmentOverviewBinding.inflate(layoutInflater)
    }

    private val adapter: PhotoGridAdapter by lazy {
        PhotoGridAdapter(this)
    }

    private val REQUEST_CODE_CAMERA = 1001
    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root.apply {
            binding.photosGrid.adapter = adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            photos.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            // TODO Mettre à jour la vue en cas d'erreur
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()

        }
        binding.fabCapture.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
            } else {
                startCamera()
            }
        }
    }
// dialogue pour afficher des images de grande taille
    override fun onItemClick(imageUrl: String) {
        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.dialog_big_photo)
        val fullscreenImageView = dialog.findViewById<ImageView>(R.id.imageView)

        Glide.with(requireContext())
            .load(imageUrl)
            .override(1000,1000)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(fullscreenImageView)

        fullscreenImageView.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDeleteClick(photo: MarsPhoto) {
        val selectedPhotos = adapter.getSelectedPhotos()
        viewModel.deletePhotos(selectedPhotos)
    }

    override fun onShareClick(photo: MarsPhoto) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, photo.url)
        binding.root.context.startActivity(shareIntent)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Camera permission is required to use the camera",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun startCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.resolveActivity(requireActivity().packageManager)?.let {
            startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
        }

    }



}
