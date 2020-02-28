package net.azarquiel.innocvjorgec.views

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user.*
import net.azarquiel.innocvjorgec.model.User
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


class ActivityUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(net.azarquiel.innocvjorgec.R.layout.activity_user)

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val fecha = sdf.format(Date())
        tvfecha.text = fecha //ponemos la fecha de hoy

        btnpf.setOnClickListener { clickDatePicker() }
        btnAñadir.setOnClickListener { onClickbtnaceptar() }
    }

    private fun clickDatePicker() {

        val c = Date()
        var sdf = SimpleDateFormat("dd")
        val dia = sdf.format(c).toInt()
        sdf = SimpleDateFormat("MM")
        val mes = sdf.format(c).toInt()
        sdf = SimpleDateFormat("yyyy")
        val anio = sdf.format(c).toInt()
        val tpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, y, m, d ->
            tvfecha.text = "$d-${m+1}-$y"
        },anio,mes-1,dia)

        tpd.show()
    }

    private fun onClickbtnaceptar() {

        if(ednombre.text.toString().equals("")){
            toast("Por favor, ingrese un nombre")
        }else{

            val id = 0
            val name: String = ednombre.text.toString()


            val fecha: String = tvfecha.text.toString()
            var fecha2 = ""

            val numeros: List<String>  = fecha.split ("-")

            var dia = numeros[0]
            var mes = numeros[1]
            var anio = numeros[2]


            if(dia.length==1){
                dia = "0$dia"
            }
            if(mes.length==1){
                mes = "0$mes"
            }

            fecha2 = "$dia-$mes-$anio"

            val user = User( name , fecha2 ,id )

            //toast(fecha2)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", user)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }
}
