package com.kevinj1008.samplesearchimage.controller

import android.os.Bundle
import com.airbnb.epoxy.EpoxyController
import com.kevinj1008.samplesearchimage.entity.ImageEntity
import com.kevinj1008.samplesearchimage.epoxymodel.imageResult

class ImageResultEpoxyController : EpoxyController() {
    var isLoading: Boolean = false
    private var imageList: ArrayList<ImageEntity> = arrayListOf()

    override fun buildModels() {
        imageList.forEachIndexed { index, imageEntity ->
            imageResult {
                id(imageEntity.hashCode() + index)
                imageUrl(imageEntity.image)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_IMAGE, imageList)
    }

    override fun onRestoreInstanceState(inState: Bundle?) {
        super.onRestoreInstanceState(inState)
        inState?.getParcelableArrayList<ImageEntity>(KEY_IMAGE)?.apply {
            imageList = this
        }
    }

    fun setImages(list: List<ImageEntity>?) {
        isLoading = false
        list?.apply {
            imageList.addAll(this)
            requestModelBuild()
        }
    }

    companion object {
        private const val KEY_IMAGE = "IMAGE_LIST"
    }
}