package com.developer.blooddonation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.developer.blooddonation.R
import com.developer.blooddonation.adapter.BloodBankAdapter
import com.developer.blooddonation.model.BloodBankModel

class BloodBanksActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: BloodBankAdapter
    private lateinit var list: ArrayList<BloodBankModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_banks)
        recyclerview = findViewById(R.id.blood_bank_recycler)
        list = ArrayList()
        adapter = BloodBankAdapter(list, this)
        recyclerview.adapter = adapter
        initData()


    }

    private fun initData() {
        list.clear()
        list.add(
            BloodBankModel(
                "https://content3.jdmagicbox.com/comp/visakhapatnam/f2/0891px891.x891.180209150531.d2f2/catalogue/sanjeevani-volantary-blood-bank-gajuwaka-visakhapatnam-blood-banks-lml3k888lc.jpg?clr=382e2e",
                "Sanjeevini Blood Bank",
                " 1-1-79/A, Bhagyanagar Complex, RTC Cross Rd, near Sri Mayuri Theatre, Hyderabad, Telangana 500020",
                "17.4097962",
                "78.493671,15",
                "Hyderabad",
                " 040 2766 7500"
            )
        )
        list.add(
            BloodBankModel(
                "https://content3.jdmagicbox.com/comp/visakhapatnam/f2/0891px891.x891.180209150531.d2f2/catalogue/sanjeevani-volantary-blood-bank-gajuwaka-visakhapatnam-blood-banks-lml3k888lc.jpg?clr=382e2e",
                "Sanjeevini Blood Bank",
                " 1-1-79/A, Bhagyanagar Complex, RTC Cross Rd, near Sri Mayuri Theatre, Hyderabad, Telangana 500020",
                "17.4097962",
                "78.493671,15",
                "Hyderabad",
                " 040 2766 7500"
            )
        )
        list.add(
            BloodBankModel(
                "https://content3.jdmagicbox.com/comp/visakhapatnam/f2/0891px891.x891.180209150531.d2f2/catalogue/sanjeevani-volantary-blood-bank-gajuwaka-visakhapatnam-blood-banks-lml3k888lc.jpg?clr=382e2e",
                "Sanjeevini Blood Bank",
                " 1-1-79/A, Bhagyanagar Complex, RTC Cross Rd, near Sri Mayuri Theatre, Hyderabad, Telangana 500020",
                "17.4097962",
                "78.493671,15",
                "Hyderabad",
                " 040 2766 7500"
            )
        )
        list.add(
            BloodBankModel(
                "https://content3.jdmagicbox.com/comp/visakhapatnam/f2/0891px891.x891.180209150531.d2f2/catalogue/sanjeevani-volantary-blood-bank-gajuwaka-visakhapatnam-blood-banks-lml3k888lc.jpg?clr=382e2e",
                "Sanjeevini Blood Bank",
                " 1-1-79/A, Bhagyanagar Complex, RTC Cross Rd, near Sri Mayuri Theatre, Hyderabad, Telangana 500020",
                "17.4097962",
                "78.493671,15",
                "Hyderabad",
                " 040 2766 7500"
            )
        )
        list.add(
            BloodBankModel(
                "https://content3.jdmagicbox.com/comp/visakhapatnam/f2/0891px891.x891.180209150531.d2f2/catalogue/sanjeevani-volantary-blood-bank-gajuwaka-visakhapatnam-blood-banks-lml3k888lc.jpg?clr=382e2e",
                "Sanjeevini Blood Bank",
                " 1-1-79/A, Bhagyanagar Complex, RTC Cross Rd, near Sri Mayuri Theatre, Hyderabad, Telangana 500020",
                "17.4097962",
                "78.493671,15",
                "Hyderabad",
                " 040 2766 7500"
            )
        )
    }
}