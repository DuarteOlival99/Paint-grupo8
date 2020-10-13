package com.example.paint.ui.fragments

import android.accessibilityservice.GestureDescription
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import com.example.paint.R
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel


class PincelChangeDialogFragment : DialogFragment() {
    private lateinit var viewModel : PaintViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.pincel_change_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(PaintViewModel::class.java)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        val inflater = LayoutInflater.from(context)
        val newFileView: View = inflater.inflate(R.layout.pincel_change_fragment, null)
        builder.setView(newFileView)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // on success
        })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })

        return builder.create()
    }

    override fun onDestroy() {
        Log.i("onDestroy", "evocadoFiltros")
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onStart() {
        Log.i("OnStart", "OnStartFiltros")
        super.onStart()
    }

    override fun onResume() {
        Log.i("onResume", "onResumeFiltros")
        super.onResume()
        val params: WindowManager.LayoutParams? = dialog?.window?.attributes
        params?.width = RelativeLayout.LayoutParams.MATCH_PARENT
        params?.height = RelativeLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        Log.i("onResume", "onResumeFiltros1")
    }

    override fun dismiss() {
        Log.i("dismiss", "dismissFiltros")
//        val parentFrag: IniciarJogoFragment = this.parentFragment as IniciarJogoFragment
//        parentFrag.atualizaPlacar()
        super.dismiss()
    }

}