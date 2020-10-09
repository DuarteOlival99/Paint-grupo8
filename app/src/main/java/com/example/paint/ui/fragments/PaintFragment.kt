package com.example.paint.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import com.example.paint.R
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import kotlinx.android.synthetic.main.paint_fragment.*

class PaintFragment : Fragment() {

    private lateinit var viewModel : PaintViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.paint_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(PaintViewModel::class.java)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onStart() {
        super.onStart()
        //paint.setBackgroundColor(viewModel.getBackgroundColor()!!)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.pincel_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        when(item.itemId) {
            R.id.pincel_menu -> {
                Toast.makeText(activity as Context,"Tapped on icon",Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item)
    }

}