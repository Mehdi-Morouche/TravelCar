package com.mehdi.travelcar

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mehdi.travelcar.databinding.ActivityAccountBinding
import com.mehdi.travelcar.entities.AccountEntity
import com.mehdi.travelcar.viewmodel.AccountActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mehdi on 2020-02-16.
 */
class AccountActivity : AppCompatActivity() {

    private lateinit var mModel: AccountActivityViewModel

    lateinit var dataBinding: ActivityAccountBinding

    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001

    private lateinit var mCurrentPhotoPath : String

    private lateinit var account: AccountEntity
    private var insertObj = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mModel = ViewModelProvider(this).get(AccountActivityViewModel::class.java)

        dataBinding = DataBindingUtil.setContentView<ActivityAccountBinding>(this, R.layout.activity_account).apply {
            setLifecycleOwner(this@AccountActivity)
        }

        mModel.account.observe(this, Observer { acc ->
            if (acc == null) {
                insertObj = true
                account = AccountEntity()
            } else {
                account = acc

                dataBinding.editLastname.text = Editable.Factory.getInstance().newEditable(account.lastname)
                dataBinding.editName.text = Editable.Factory.getInstance().newEditable(account.name)
                dataBinding.editAddress.text = Editable.Factory.getInstance().newEditable(account.address)
                dataBinding.editBirthdate.text = Editable.Factory.getInstance().newEditable(account.birthdate)

                Glide.with(applicationContext)
                    .load(account.photo).apply(RequestOptions().circleCrop())
                    .into(dataBinding.photo)
            }
        })

        dataBinding.relativePhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else {
                    //permission already granted
                    pickImageFromGallery()
                }
            }
            else {
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }

        dataBinding.relativePhoto.isClickable = false

        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year )
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
            dataBinding.editBirthdate.text = Editable.Factory.getInstance().newEditable(sdf.format(cal.time))

        }

        dataBinding.editBirthdate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                var date = account.birthdate?.split("/")
                Log.e("cal", date.toString())

                DatePickerDialog(this@AccountActivity, dateSetListener,
                    if (date.isNullOrEmpty()) cal.get(Calendar.YEAR) else Integer.valueOf(date[2]),
                    if (date.isNullOrEmpty()) cal.get(Calendar.MONTH) else Integer.valueOf(date[1]),
                    if (date.isNullOrEmpty()) cal.get(Calendar.DAY_OF_MONTH) else Integer.valueOf(date[0])).show()
            }
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @Throws(IOException::class)
    private fun createFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    private fun pickImageFromGallery() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = createFile()

        val uri: Uri = FileProvider.getUriForFile(
            this,
            "com.mehdi.travelcar.fileprovider",
            file
        )
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        val chooser = Intent.createChooser(intent, "")
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        startActivityForResult(chooser, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){

            if (data?.data != null) {
                Glide.with(applicationContext)
                    .load(data?.data).apply(RequestOptions().circleCrop())
                    .into(dataBinding.photo)

                account.photo = data?.data.toString()
            }
            else {
                Glide.with(applicationContext)
                    .load(mCurrentPhotoPath).apply(RequestOptions().circleCrop())
                    .into(dataBinding.photo)

                account.photo = mCurrentPhotoPath
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                navigateUpTo(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    fun edit(v : View) {
        dataBinding.edit.isEnabled = false
        dataBinding.save.isEnabled = true
        dataBinding.editLastname.isEnabled = true
        dataBinding.editName.isEnabled = true
        dataBinding.editAddress.isEnabled = true
        dataBinding.editBirthdate.isEnabled = true
        dataBinding.relativePhoto.isClickable = true
        dataBinding.editPhoto.visibility = View.VISIBLE
    }

    fun save(v : View) {
        dataBinding.edit.isEnabled = true
        dataBinding.save.isEnabled = false
        dataBinding.editLastname.isEnabled = false
        dataBinding.editName.isEnabled = false
        dataBinding.editAddress.isEnabled = false
        dataBinding.editBirthdate.isEnabled = false
        dataBinding.relativePhoto.isClickable = false
        dataBinding.editPhoto.visibility = View.GONE


        account.lastname = dataBinding.lastname.editText?.text.toString()
        account.name = dataBinding.name.editText?.text.toString()
        account.address = dataBinding.address.editText?.text.toString()
        account.birthdate = dataBinding.birthdate.editText?.text.toString()

        if (insertObj) {
            mModel.insert(account)
        } else {
            mModel.update(account)
        }
    }
}