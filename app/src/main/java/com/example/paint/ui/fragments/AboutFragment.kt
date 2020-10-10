package com.example.paint.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.paint.R
import com.example.paint.ui.viewmodels.viewmodels.AboutViewModel
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : Fragment() {

    private lateinit var viewModel : AboutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        viewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onStart() {
        super.onStart()

        about.setBackgroundColor(viewModel.getBackgroundColor()!!)
    }

    @OnClick(R.id.iconLinkedin_bruna)
    fun onClickIconLinkedinBruna(view: View){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/brunavieites/"))
        startActivity(browserIntent)
    }

    @OnClick(R.id.iconFacebook_bruna)
    fun onClickIconFacebookBruna(view: View){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/bruna.vieites/"))
        startActivity(browserIntent)
    }

    @OnClick(R.id.iconInstagram_bruna)
    fun onClickIconInstagramBruna(view: View){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/bruna_vieites/"))
        startActivity(browserIntent)
    }

    @OnClick(R.id.iconLinkedin_duarte)
    fun onClickIconLinkedinDuarte(view: View){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/duarte-olival-125a98159/"))
        startActivity(browserIntent)
    }

    @OnClick(R.id.iconFacebook_duarte)
    fun onClickIconFacebookDuarte(view: View){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/duarte.olival.7/"))
        startActivity(browserIntent)
    }

    @OnClick(R.id.iconInstagram_duarte)
    fun onClickIconInstagramDuarte(view: View){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/duarte_olival/?hl=pt"))
        startActivity(browserIntent)
    }



}