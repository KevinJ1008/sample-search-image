package com.kevinj1008.samplesearchimage.epoxymodel

import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kevinj1008.base.base.KotlinEpoxyHolder
import com.kevinj1008.imageloader.loadImage
import com.kevinj1008.samplesearchimage.R

@EpoxyModelClass
abstract class ImageResultEpoxyModel : EpoxyModelWithHolder<ImageResultEpoxyModel.ViewHolder>() {

    @EpoxyAttribute
    var imageUrl: String = ""

    override fun getDefaultLayout(): Int {
        return R.layout.item_image_result
    }

    override fun bind(holder: ViewHolder) {
        super.bind(holder)
        holder.image.loadImage(imageUrl, 0)
    }

    class ViewHolder : KotlinEpoxyHolder() {
        val image by bind<ImageView>(R.id.image_result)
    }
}