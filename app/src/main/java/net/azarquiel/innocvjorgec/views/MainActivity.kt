package net.azarquiel.innocvjorgec.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import net.azarquiel.innocvjorgec.R
import net.azarquiel.innocvjorgec.adapter.CustomAdapter
import net.azarquiel.innocvjorgec.model.User
import net.azarquiel.innocvjorgec.viewmodel.MainViewModel
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), CustomAdapter.OnLongClickListenerUser , SearchView.OnQueryTextListener  {


    private lateinit var searchView: SearchView
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CustomAdapter
    private lateinit var users: List<User>

    companion object {
        const val REQUESTADD = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initRV()
        getUsers()

        fab.setOnClickListener {

            onClickAdd()
        }
    }

    private fun initRV() {
        adapter = CustomAdapter(this, R.layout.row, this)
        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(this)
    }

    private fun getUsers() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getDataUsers().observe(this, Observer { it ->
            it?.let{
                users=it
                adapter.setUsers(users)
            }
        })

    }

    private fun saveUser(user: User,path: Int) {//0 = actualizar, 1 = guardar nuevo
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.saveUser(user)

        if(path==1) {
            //DARLE LA VUELTA A LA FECHA
            var fecha = user.birthdate
            val numeros: List<String>  = fecha.split ("-")

            var dia = numeros[2]
            var mes = numeros[1]
            var anio = numeros[0]

            val fecha2 = "$dia-$mes-$anio"
            user.birthdate = fecha2

            //adivinamos su proximo id
            val lastUser = users.last()
            user.id = lastUser.id+1



            //modificamos el list
            val usersaux = ArrayList<User>(users)
            usersaux.add(0, user)
            users = ArrayList<User>(usersaux)
            adapter.setUsers(users)
        }
    }

    private fun onClickAdd(){
        val intent = Intent(this, ActivityUser::class.java)
        startActivityForResult(intent,REQUESTADD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUESTADD) {
                val user = data!!.getSerializableExtra("user") as User





                saveUser(user,1)
                longToast("User: <"+user.name+"> fue registrado correctamente.")
            }
        }

    }

    fun onClickUser(v: View){
        val user = v.tag as User

        alert("¿Estás seguro que quieres editar a ${user.name}, con código: ${user.id}?", "Confirm") {
            yesButton {
                modifyUser(user)
            }
            noButton {}
        }.show()
    }
    private fun modifyUser(userModify: User) {//otra activity?

        alert {
            val anio = userModify.birthdate.substring(0,4)
            val mes = userModify.birthdate.substring(5,7)
            val dia = userModify.birthdate.substring(8,10)
            title = "Modificar usuario: ${userModify.name} "
            customView {
                verticalLayout {
                    lparams(width = wrapContent, height = wrapContent)
                    textView{//lblNombre
                        text = "Nombre:"
                        //padding = dip(8)
                        setPadding(45,34,25,0)
                    }
                    val etNombre = editText {
                        hint = "Nombre"
                        //setPadding(50,25,25,0)

                        padding = dip(15)
                        userModify?.let{ this.setText(userModify.name)}
                    }
                    textView{//lblDia
                        text = "Día:"
                        //padding = dip(8)
                        setPadding(45,34,25,0)
                    }
                    val etDia = editText {
                        hint = "Día"
                        gravity = 1
                        padding = dip(8)
                        userModify?.let{ this.setText(dia)}
                    }
                    textView{//lblMes
                        text = "Mes:"
                        //padding = dip(8)
                        setPadding(45,34,25,0)
                    }
                    val etMes = editText {
                        hint = "Mes"
                        gravity = 1
                        padding = dip(8)
                        userModify?.let{ this.setText(mes)}
                    }
                    textView{//lblAnio
                        text = "Año:"
                        //padding = dip(8)
                        setPadding(45,34,25,0)
                    }
                    val etAño = editText {
                        hint = "Año"
                        gravity = 1
                        padding = dip(16)
                        userModify?.let{ this.setText(anio)}
                    }
                    positiveButton("Aceptar") {
                        if (etNombre.text.toString().isEmpty() || etDia.text.toString().isEmpty() || etMes.text.toString().isEmpty()|| etAño.text.toString().isEmpty()){
                            longToast("ERROR: Todos los campos son obligatorios")
                            return@positiveButton
                        }

                        else {
                         // modify
                            //hacer string fecha nueva
                            var dia = etDia.text.toString()
                            if(dia.length==1){
                                dia = "0$dia"
                            }
                            var mes = etMes.text.toString()
                            if(mes.length==1){
                                mes = "0$mes"
                            }
                            var año = etAño.text.toString()
                            if(año.length!=4){
                                longToast("ERROR: Introduzca un año válido")
                                return@positiveButton
                            }
                            var fecha = "$año-$mes-$dia"


                            val userNew = User(
                                etNombre.text.toString(),
                                fecha,
                                userModify.id
                            )


                            val usersaux = ArrayList<User>(users)
                            usersaux.add(0,userNew)
                            usersaux.remove(userModify)
                            users = ArrayList<User>(usersaux)
                            adapter.setUsers(users)


                            saveUser(userNew,0)
                            longToast("User: <"+userNew.name+"> fue actualizado correctamente.")
                        }

                    }
                    negativeButton("Cancelar"){}
                }

            }
        }.show()

    }


    private fun deleteUser(user: User){
        viewModel.deleteUser(user.id)


        val usersaux = ArrayList<User>(users)
        usersaux.remove(user)
        users = ArrayList<User>(usersaux)
        adapter.setUsers(users)

        alert {
            title = "Ususario elimiado: ${user.name} "
            negativeButton("Aceptar"){}
        }.show()
    }

    override fun OnLongClickUser(user: User):Boolean{//eliminar
        alert("¿Estás seguro que quieres eliminar a ${user.name}, con código: ${user.id}?", "Confirm") {
            yesButton {
                deleteUser(user)
                longToast("El usuario ha sido eliminado correctamente")//o hacer un  modal
            }
            noButton {}
        }.show()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        // ************* <Filtro> ************
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
        // ************* </Filtro> ************

        return true
    }

    // ************* <Buscador> ************
    override fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<User>(users)
        adapter.setUsers(original.filter { user -> user.name!=null && user.name.toUpperCase().contains(query.toUpperCase()) })
        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }
    // ************* <Buscador> ************

}
