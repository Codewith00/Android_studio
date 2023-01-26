package com.example.m15_room

fun main (){
    val a = "dfghjASD11-"
    val b = "ASDSD11"
    val c = "aasdasd"
    val d = "FGHJ-"
    val mail = "master0jide@gmail.com"

    println( check(a))
    println( check(b))
    println( check(c))
    println( check(d))
    println( check(mail))

}
fun check(word: String): String {
    return if (word.contains(Regex("""^[A-Za-z-]*$"""))) word
    else "null"
}