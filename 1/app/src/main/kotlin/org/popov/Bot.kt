
package org.popov

class Question {
    val question: String;
    val variants: List<String>;
    val answer: Int;

    constructor(question: String, variants: List<String>, answer: Int){
        this.question = question;
        this.variants = variants;
        this.answer = answer;
    }

}

class Tests{
    val questions = listOf(
        Question("Что едят капибары?",
            listOf("Траву", "Мясо", "Рыбу", "Пайтон разработчиков"),
            1),
        Question("Why do we need kotlin at all?",
            listOf("Null safety", "Less boilerplate", "Java interoperability", "All of the above"),
            4
        ),
        Question("Какой тип используется для целых чисел в Kotlin?",
            listOf("String", "Int", "Boolean", "Float"),
            2
        ),
        Question("What keyword declares an immutable variable?",
            listOf("var", "val", "let", "const"),
            2
        )
    )

    fun checkAnswer(question: Question, answer: Int): Boolean{
        return question.answer == answer;
    }

    fun askQuestion(question: Question){
        println(question.question)
        for (i in question.variants.indices){
            println("${i + 1}. ${question.variants[i]}")
        }
        print("Your answer: ")
        while (true) {
            val input = readlnOrNull()?.trim()
            val answer = input?.toIntOrNull()
            if (answer == null) println("Invalid input. Try again.")
            else if (checkAnswer(question, answer)){
                println("Correct!")
                break
            }
            else println("Wrong answer.")
        }
        println()
    }

    fun runTests(){
        questions.forEach { askQuestion(it) }
        println("Completed , have a nice day !")
    }

}





class Bot {
    private fun readString(): String {
        print("> ")
        return (readlnOrNull() ?: "").trim()
    }

    private fun step1(){
        println("Hello ! My name is DICT_Bot.")
    }
    private fun step2(){
        println("Please, remind me your name .")
        val name = readString()
        println("What a great name you have , $name !")
    }
    private fun step3(){
        println("Let me guess your age.")
        println("Enter remainders of dividing your age by 3 , 5 and 7.")
        val rem3 = readString().toIntOrNull() ?: return
        val rem5 = readString().toIntOrNull() ?: return
        val rem7 = readString().toIntOrNull() ?: return
        val age = (rem3 * 70 + rem5 * 21 + rem7 * 15) % 105
        println("Your age is $age!")
    }

    private fun step4(){
        println("Now I will prove to you that I can count to any number you want .")
        val n = readString().toIntOrNull() ?: return
        for (i in 0..n) println("$i!")
    }

    private fun step5(){
        println("Lets test your programming knowledge.")
        Tests().runTests()
        println("Congratulations , have a nice day !")
    }

    public fun run(){
        step1()
        step2()
        step3()
        step4()
        step5()
    }
}

fun main() {
    val bot = Bot()
    bot.run()
}
