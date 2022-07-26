package com.example.kotlin_roomdb.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_roomdb.repository.UserRepository
import com.example.kotlin_roomdb.database.User
import com.example.kotlin_roomdb.databinding.ActivityMainBinding
import com.example.kotlin_roomdb.viewmodel.RoomViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

class MainActivity : AppCompatActivity() {

    lateinit var roomDataAdapter: RoomDataAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var roomviewmodel: RoomViewModel

    @OptIn(DelicateCoroutinesApi::class)
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initview()

        binding.addUserDia.addUserBtn.setOnClickListener {

            addUserOnclick()

        }
    }
    fun initview()
    {
        roomviewmodel = RoomViewModel(UserRepository(this))
        roomDataAdapter = RoomDataAdapter(applicationContext , supportFragmentManager)
        binding.recycler.apply {

            adapter  = roomDataAdapter
            layoutManager = LinearLayoutManager(applicationContext)

        }
        roomviewmodel.userList.observe(this) {
            roomDataAdapter.submitList(it)
        }
    }

     @OptIn(DelicateCoroutinesApi::class)
      fun addUserOnclick()
    {
        if(binding.addUserDia.editFname.text.toString().isEmpty())
        {
            binding.addUserDia.editFname.error = "Please fill it"
            return
        }
        else if(binding.addUserDia.editLname.text.toString().isEmpty())
        {
            binding.addUserDia.editLname.error = "Please fill it"
            return
        }
        else if(binding.addUserDia.editAge.text.toString().isEmpty())
        {
            binding.addUserDia.editAge.error = "Please fill it"
            return
        }
        else {
            val fName = binding.addUserDia.editFname.text.toString()
            val lName = binding.addUserDia.editLname.text.toString()
            val age =  binding.addUserDia.editAge.text.toString()

                roomviewmodel.insertUser(
                    User(0, fName , lName , age )
                )

            Toast.makeText(
                applicationContext,
                "User has been added to Room database",
                Toast.LENGTH_SHORT
            ).show()
            binding.addUserDia.editFname.setText("")
            binding.addUserDia.editLname.setText("")
            binding.addUserDia.editAge.setText("")

        }

    }
}