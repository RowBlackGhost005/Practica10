package mx.edu.itson.practica10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val userRef = FirebaseDatabase.getInstance().getReference("Usuarios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnGuardar: Button = findViewById(R.id.btnEnviar) as Button

        btnGuardar.setOnClickListener{ saveMarkFromForm()}

        userRef.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(databaseError: DatabaseError){}
            override fun onChildMoved(dataSnapshot: DataSnapshot , previousName: String?){}
            override fun onChildChanged(dataSnapshot: DataSnapshot , previousName: String?){}
            override fun onChildRemoved(dataSnapshot: DataSnapshot){}

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val usuario = dataSnapshot.getValue(User::class.java)
                if(usuario != null) writeMark(usuario)
            }
        })
    }

    private fun saveMarkFromForm(){
        var name: EditText = findViewById(R.id.etName) as EditText
        var lastName: EditText = findViewById(R.id.etLastName) as EditText
        var age: EditText = findViewById(R.id.etAge) as EditText

        val usuario = User(name.text.toString() , lastName.text.toString() , age.text.toString())

        userRef.push().setValue(usuario)
    }

    private fun writeMark(mark: User){
        var listV: TextView = findViewById(R.id.list_textView) as TextView
        val text = listV.text.toString() + mark.toString() + "\n"
        listV.text = text;
    }
}