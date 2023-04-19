package mx.edu.potros.practica10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private val userRef = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnSave: Button = findViewById(R.id.save_button)

        btnSave.setOnClickListener {
            saveMarkFromForm()
        }

        userRef.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val usuario = snapshot.getValue(User::class.java)
                if (usuario != null) writeMark(usuario)
            }
        })
    }

    private fun saveMarkFromForm() {
        var name: EditText = findViewById(R.id.et_name)
        var lastName: EditText = findViewById(R.id.et_lastName)
        var age: EditText = findViewById(R.id.et_age)

        val usuario = User(name.text.toString(), lastName.text.toString(), age.text.toString())

        userRef.push().setValue(usuario)
    }

    private fun writeMark(mark: User) {
        var listV: TextView = findViewById(R.id.list_textView)
        val text = listV.text.toString() + mark.toString() + "\n"
        listV.text = text
    }
}