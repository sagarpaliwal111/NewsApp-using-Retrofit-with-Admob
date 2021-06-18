package com.example.retrofitapicheezycode2part

object ColorPicker {
    val colors= arrayOf("#00FFFF","#008080","#000080","#FF00FF","#FFA07A","#B22222","#8B0000","#F08080","#FFDAB9")
    var colorIndex =1
    fun getColor():String{
        return colors[colorIndex++ % colors.size]
    }
}

