package com.fav.adapterdelegate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by glenntuyu on 15/05/2024.
 */
abstract class BaseAdapterDelegate<T: ST, ST: Any, VH : RecyclerView.ViewHolder, VB : ViewDataBinding>(@LayoutRes private val layoutRes: Int) : AdapterDelegate<ST> {

    abstract fun onBindViewHolder(item: T, holder: VH)

    open fun onBindViewHolderWithPayloads(item: T, holder: VH, payloads: Bundle) {
        onBindViewHolder(item, holder)
    }

    abstract fun onCreateViewHolder(parent: ViewGroup, binding: VB): VH

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(itemList: List<ST>, position: Int, holder: RecyclerView.ViewHolder) {
        onBindViewHolder(itemList[position] as T, holder as VH)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(itemList: List<ST>, position: Int, payloads: Bundle, holder: RecyclerView.ViewHolder) {
        onBindViewHolderWithPayloads(itemList[position] as T, holder as VH, payloads)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<VB>(LayoutInflater.from(parent.context), layoutRes, parent, false)
        return onCreateViewHolder(parent, binding)
    }
}