package my.edu.tarc.loan

import android.app.Notification
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import my.edu.tarc.loan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonCalculate.setOnClickListener {
            val sellingPrice = binding.editTextSellingPrice.text.toString().toInt()
            val downPayment = binding.editTextNumber2.text.toString().toInt()
            val firstTimeBuyer = binding.checkBoxFirstTime.isChecked

            var legalFee: Float = 0.0f
            var stampDuty:Float = 0.0f

            var different: Int = 0

            if(firstTimeBuyer){
                if(downPayment >= sellingPrice * 0.1){
                    //This is ok
                    different = sellingPrice - downPayment

                    if(different < 500000){
                        legalFee = different * 0.01f
                    } else if(different < 1000000){
                        legalFee -= 500000
                        legalFee = 5000.0f
                        legalFee += (different * 0.008).toFloat()
                    } else{
                        different -= 1000000
                        legalFee = 5000.0f
                        legalFee += (different * 0.008).toFloat()
                        legalFee += (different * 0.005).toFloat()

                    }

                    stampDuty = different * 0.5f

                } else{
                    //This is NOT ok
                }
            } else{
                //None first time buyer
                if(downPayment > sellingPrice * 0.1){
                    different = sellingPrice - downPayment

                    if(different < 500000){
                        legalFee = different * 0.01f
                    } else if(different < 1000000){
                        legalFee -= 500000
                        legalFee = 5000.0f
                        legalFee += (different * 0.008).toFloat()
                    } else{
                        different -= 1000000
                        legalFee = 5000.0f
                        legalFee += (different * 0.008).toFloat()
                        legalFee += (different * 0.005).toFloat()

                    }

                    stampDuty = different * 0.5f
                } else{
                    //This is NOT ok
                }
            }

            val myLoan = Loan(legalFee.toString(), stampDuty.toString())
            binding.myLoan = myLoan
        }

        binding.buttonReset.setOnClickListener {
            val myLoan = Loan("","")
            //binding.myLoan is defined in the data binding - layout file
            //myLoan is local variable
            binding.myLoan = myLoan
        }

        binding.imageButtonPhone.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:03456789")
            startActivity(intent)
        }

        binding.imageButtonWeb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://pbebank.com.my")
            startActivity(intent)
        }
    }
}