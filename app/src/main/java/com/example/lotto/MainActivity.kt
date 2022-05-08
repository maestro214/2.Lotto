package com.example.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val addButton: Button by lazy {
        findViewById<Button>(R.id.addButton)
    }

    private val runButton: Button by lazy {
        findViewById<Button>(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private val numberTextViewList : List<TextView> by lazy{
        listOf<TextView>(  //listOf는 리스트 생성할때 씀
            findViewById<TextView>(R.id.firstText),
            findViewById<TextView>(R.id.secondText),
            findViewById<TextView>(R.id.thirdText),
            findViewById<TextView>(R.id.fourthText),
            findViewById<TextView>(R.id.fifthText),
            findViewById<TextView>(R.id.sixthText)
        )
    }

    private var didRun = false


    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumber()

            didRun = true

            list.forEachIndexed{ index,number ->
                val textView = numberTextViewList[index]

                textView.text = number.toString()
                textView.isVisible = true


                setNumverBackGround(number, textView)

            }
        }
    }

    private fun initAddButton(){
        addButton.setOnClickListener{

            if(didRun){
                Toast.makeText(this,"초기화 후에 시도해주세요.",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(pickNumberSet.size >= 5){
                Toast.makeText(this,"번호는 5개까지만 선택할 수 있습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.contains(numberPicker.value)){ // contain함수 값이 들어있는지 확인
                Toast.makeText(this,"이미 선택한 번호입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            setNumverBackGround(numberPicker.value,textView)

            pickNumberSet.add(numberPicker.value)

        }

    }

    private fun setNumverBackGround(number:Int, textView: TextView){

        when(number){

            //drawble접근 contextCompat.getDrawable
            in 1..10 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_yellow)
            in 11..20 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_red)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_green)

        }

    }

    private fun initClearButton(){
        clearButton.setOnClickListener{
            pickNumberSet.clear()
            numberTextViewList.forEach{  //데이터를 순차적으로 하나씩 꺼내줌 foreach
                it.isVisible =false
            }
            didRun = false

        }

    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if(pickNumberSet.contains(i)){
                    continue
                }
                this.add(i)
            }
        }

        numberList.shuffle()
        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)

        return newList.sorted()
    }

}