package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo2.ui.fragments

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment

abstract class PermissionedFragment (private val requestCode: Int) : Fragment() {

    fun onRequestPermissions (context: Context, permissions: Array<String>) {
            var permissionsGiven = 0 //ex.8 da ficha 10 fz
            permissions.forEach {
                if(checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(permissions, requestCode)
                } else {
                    permissionsGiven++
                }
            }
            if (permissionsGiven == permissions.size)
        onRequestPermissionSucces()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if(this.requestCode == requestCode){
            if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                onRequestPermissionSucces()
            }else {
                onRequestPermissionFailure()
            }
        }
    }

    abstract fun onRequestPermissionSucces()
    abstract fun onRequestPermissionFailure()

}