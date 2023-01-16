package com.udacity.project4.basic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

abstract class RemindRecyclerView<T>(private val callback: ((item: T) -> Unit)? = null) :
    RecyclerView.Adapter<DataViewHolder<T>>() {

    private var _items: MutableList<T> = mutableListOf()


    private val items: List<T>?
        get() = this._items

    override fun getItemCount() = _items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil
            .inflate<ViewDataBinding>(layoutInflater, getLayoutRes(viewType), parent, false)

        binding.lifecycleOwner = getLifecycleOwner()

        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder<T>, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            callback?.invoke(item)
        }
    }

    fun getItem(position: Int) = _items[position]


    fun addData(items: List<T>) {
        _items.addAll(items)
        notifyDataSetChanged()
    }


    fun clear() {
        _items.clear()
        notifyDataSetChanged()
    }

    @LayoutRes
    abstract fun getLayoutRes(viewType: Int): Int

    open fun getLifecycleOwner(): LifecycleOwner? {
        return null
    }
}

