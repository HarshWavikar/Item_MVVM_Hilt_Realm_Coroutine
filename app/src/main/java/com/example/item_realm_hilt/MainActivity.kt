package com.example.item_realm_hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.item_realm_hilt.adapter.RecyclerItemAdapter
import com.example.item_realm_hilt.model.Item
import com.example.item_realm_hilt.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    lateinit var edtItemName: EditText
    lateinit var edtItemPrice: EditText
    lateinit var btnAddItem: Button
    lateinit var btnListItem: Button

    lateinit var itemRv: RecyclerView
    lateinit var adapterRv: RecyclerItemAdapter
 lateinit var coordinatorLayout: CoordinatorLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtItemName = findViewById(R.id.edtItemName)
        edtItemPrice = findViewById(R.id.edtItemPrice)
        btnAddItem = findViewById(R.id.btnAddItem)
        btnListItem = findViewById(R.id.btnListItem)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        itemRv = findViewById(R.id.itemRv)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        edtItemPrice.addTextChangedListener(textWatcher)
        edtItemName.addTextChangedListener(textWatcher)
        btnAddItem.setOnClickListener {
            save()
            edtItemName.text.clear()
            edtItemPrice.text.clear()
        }

        btnListItem.setOnClickListener {
            show()
        }
        setSwipeToDelete()
    }

    private fun show() {
        viewModel._items.observe(this, Observer {
            adapterRv = RecyclerItemAdapter(this, it)
            itemRv.layoutManager = LinearLayoutManager(this)
            itemRv.adapter = adapterRv
        })
    }

    private fun save() {
        val name = edtItemName.text.toString()
        val price = edtItemPrice.text.toString()
        val newItem = Item()

        if (name.isEmpty() && price.isEmpty()) {
            return
        } else {
            viewModel.insertItem(newItem)
            newItem.apply {
                this.item_name = name
                this.item_price = price
            }
            Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val name = edtItemName.text.toString().trim()
            val price = edtItemPrice.text.toString().trim()

            btnAddItem.isEnabled = name.isNotEmpty() && price.isNotEmpty()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun setSwipeToDelete() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.adapterPosition
                val currentItem = adapterRv.itemList[itemPosition]
                viewModel.deleteItem(currentItem.id)


                Snackbar.make(coordinatorLayout, "${currentItem.item_name} Deleted Successfully", Snackbar.LENGTH_SHORT).show()
//                    .setAction("Undo") {
//                        viewModel.insertItem(currentItem)
//                    }.show()
            }
        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(itemRv)
    }
}