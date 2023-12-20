package com.example.android.marsphotos.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsphotos.R
import com.example.android.marsphotos.data.MarsPhoto
import com.example.android.marsphotos.databinding.GridViewItemBinding
import com.example.android.marsphotos.network.model.MarsPhotoDto

class PhotoGridAdapter(private val listener: OnItemClickListener) :
    ListAdapter<MarsPhoto, PhotoGridAdapter.MarsPhotosViewHolder>(DiffCallback) {

    inner class MarsPhotosViewHolder(
        private var binding: GridViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener ,View.OnLongClickListener{
        init {
            binding.marsImage.setOnClickListener(this)
            binding.marsImage.setOnLongClickListener(this)
        }
        fun bind(photo: MarsPhoto) {
            bindImage(photo.url)
        }

        private fun bindImage(imgUrl: String) {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            binding.marsImage.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val photo = getItem(position)
                listener.onItemClick(photo.url)
            }
        }
        override fun onLongClick(view: View?): Boolean {
            showPopupMenu(view)
            return true
        }

        fun showPopupMenu(view: View?) {
            view?.let {
                val popup = PopupMenu(it.context, it)
                popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_delete -> {
                            val position = adapterPosition
                            if (position != RecyclerView.NO_POSITION) {
                                val photo = getItem(position)
                                listener.onDeleteClick(photo)
                            }
                            true
                        }
                        R.id.menu_share -> {
                            val position = adapterPosition
                            if (position != RecyclerView.NO_POSITION) {
                                val photo = getItem(position)
                                listener.onShareClick(photo) // 执行分享操作
                            }
                            true
                        }
                        else -> false
                    }
                }

                popup.show()
            }
        }


    }
    interface OnItemClickListener {
        fun onItemClick(imageUrl: String)
        fun onDeleteClick(photo: MarsPhoto)
        fun onShareClick(photo: MarsPhoto)

    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [MarsPhotoDto] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<MarsPhoto>() {
        override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.url == newItem.url
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsPhotosViewHolder {

        return MarsPhotosViewHolder(
            GridViewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MarsPhotosViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)

        if (marsPhoto.isSelected) {
            holder.itemView.setBackgroundResource(R.drawable.red_border)
        } else {
            holder.itemView.background = null // non selected effaacer background
        }

        // On fait la sélection ici
        holder.itemView.setOnClickListener {
            marsPhoto.isSelected = !marsPhoto.isSelected
            notifyItemChanged(position)
        }

        holder.itemView.setOnLongClickListener {
            holder.showPopupMenu(holder.itemView)
            true
        }
    }

//     obtenir la liste des photos sélectionnées
    fun getSelectedPhotos(): List<MarsPhoto> {
        return currentList.filter { it.isSelected }
    }
}
