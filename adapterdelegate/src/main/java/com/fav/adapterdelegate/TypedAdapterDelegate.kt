package com.fav.adapterdelegate

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType

/**
 * Created by glenntuyu on 15/05/2024.
 */
abstract class TypedAdapterDelegate<T: ST, ST: Any, VH : RecyclerView.ViewHolder, VB : ViewDataBinding>(@LayoutRes layoutRes: Int) : BaseAdapterDelegate<T, ST, VH, VB>(layoutRes) {

    @Suppress("UNCHECKED_CAST")
    val itemClass: Class<T> = ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.first() as Class<T>)

    override fun isForViewType(itemList: List<ST>, position: Int, isFlexibleType: Boolean): Boolean {
        return if (isFlexibleType) itemClass.isAssignableFrom(itemList[position]::class.java)
        else itemList[position]::class.java == itemClass
    }
}