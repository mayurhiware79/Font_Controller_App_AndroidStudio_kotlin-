package com.example.font_controller_a79

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.widget.*

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var previewText: TextView
    private lateinit var spinnerFont: Spinner
    private lateinit var spinnerColor: Spinner
    private lateinit var checkBold: CheckBox
    private lateinit var checkItalic: CheckBox
    private lateinit var checkUnderline: CheckBox
    private lateinit var seekBar: SeekBar
    private lateinit var btnApply: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Intialize Views
        editText = findViewById(R.id.editText)
        previewText = findViewById(R.id.previewText)
        spinnerFont = findViewById(R.id.spinnerFont)
        spinnerColor = findViewById(R.id.spinnerColor)
        checkBold = findViewById(R.id.checkBold)
        checkItalic = findViewById(R.id.checkItalic)
        checkUnderline = findViewById(R.id.checkUnderline)
        seekBar = findViewById(R.id.seekBar)
        btnApply = findViewById(R.id.btnApply)

        // Spinner Adapters
        val fontAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.font_styles,
            android.R.layout.simple_spinner_item
        )
        fontAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFont.adapter = fontAdapter

        val colorAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.font_colors,
            android.R.layout.simple_spinner_item
        )
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerColor.adapter = colorAdapter

        //Button click listener

        btnApply.setOnClickListener {
            // Set Text
            previewText.text = editText.text.toString()

            // Font Style
            val font = when (spinnerFont.selectedItem.toString()) {
                "Sans Serif" -> Typeface.SANS_SERIF
                "Serif" -> Typeface.SERIF
                "Monospace" -> Typeface.MONOSPACE
                else -> Typeface.DEFAULT
            }

            // Bold + Italic
            var style = Typeface.NORMAL
            if (checkBold.isChecked && checkItalic.isChecked) {
                style = Typeface.BOLD_ITALIC
            } else if (checkBold.isChecked) {
                style = Typeface.BOLD
            } else if (checkItalic.isChecked) {
                style = Typeface.ITALIC
            }

            previewText.typeface = Typeface.create(font, style)

            // Underline
            if (checkUnderline.isChecked) {
                previewText.paintFlags =
                    previewText.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            } else {
                previewText.paintFlags =
                    previewText.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
            }

            // Color
            val color = when (spinnerColor.selectedItem.toString()) {
                "Red" -> Color.RED
                "Blue" -> Color.BLUE
                "Green" -> Color.GREEN
                else -> Color.BLACK
            }
            previewText.setTextColor(color)

            // Text Size
            previewText.textSize = seekBar.progress.toFloat()
        }
    }
}