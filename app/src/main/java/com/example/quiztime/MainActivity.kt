package com.example.quiztime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quiztime.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var quizModelList: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        quizModelList= mutableListOf()
        getDataFromFirebase()
    }
    private fun setupRecyclerView() {
        binding.progressBar.visibility = View.GONE
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter
    }

    private fun getDataFromFirebase() {
        binding.progressBar.visibility = View.VISIBLE
        val AndroidQuestionModel = mutableListOf<QuestionModel>()

// Android Development Questions
        AndroidQuestionModel.add(QuestionModel("What is Android?", mutableListOf("Language", "OS", "Product", "None"), "OS"))
        AndroidQuestionModel.add(QuestionModel("What does XML stand for in Android?", mutableListOf("Extensible Markup Language", "Extra Markup Language", "Executable Markup Language", "None of the above"), "Extensible Markup Language"))
        AndroidQuestionModel.add(QuestionModel("Which component handles user interaction in Android?", mutableListOf("Service", "Broadcast Receiver", "Activity", "Content Provider"), "Activity"))
        AndroidQuestionModel.add(QuestionModel("What is the main thread in Android called?", mutableListOf("Worker Thread", "UI Thread", "Background Thread", "None of the above"), "UI Thread"))
        AndroidQuestionModel.add(QuestionModel("Which method is used to start an activity in Android?", mutableListOf("startActivity()", "beginActivity()", "launchActivity()", "openActivity()"), "startActivity()"))

        val webDevelopmentQuestionModel = mutableListOf<QuestionModel>()

// Web Development Questions
        webDevelopmentQuestionModel.add(QuestionModel("What does HTML stand for?", mutableListOf("HyperText Markup Language", "HighText Markup Language", "HyperText Machine Language", "None of the above"), "HyperText Markup Language"))
        webDevelopmentQuestionModel.add(QuestionModel("Which HTML tag is used to define an internal style sheet?", mutableListOf("<style>", "<script>", "<css>", "<link>"), "<style>"))
        webDevelopmentQuestionModel.add(QuestionModel("Which of the following is a valid CSS selector?", mutableListOf(".class", "#id", "element", "All of the above"), "All of the above"))
        webDevelopmentQuestionModel.add(QuestionModel("What is the purpose of the 'alt' attribute in an <img> tag?", mutableListOf("To specify alternative text for an image", "To define the image source", "To define the image height", "To specify image width"), "To specify alternative text for an image"))
        webDevelopmentQuestionModel.add(QuestionModel("What does the 'box-sizing' CSS property do?", mutableListOf("Defines how the total width and height of an element is calculated", "Sets the border of an element", "Adjusts the padding of an element", "None of the above"), "Defines how the total width and height of an element is calculated"))


// Java Questions
        val javaQuestions = mutableListOf<QuestionModel>()
        javaQuestions.add(QuestionModel("What is the default value of an int variable in Java?", mutableListOf("0", "null", "1", "-1"), "0"))
        javaQuestions.add(QuestionModel("Which keyword is used to define a class in Java?", mutableListOf("class", "define", "struct", "object"), "class"))
        javaQuestions.add(QuestionModel("Which method is the entry point of a Java application?", mutableListOf("start()", "run()", "main()", "init()"), "main()"))
        javaQuestions.add(QuestionModel("What does JVM stand for?", mutableListOf("Java Virtual Machine", "Java Variable Machine", "Java Version Machine", "Java View Machine"), "Java Virtual Machine"))
        javaQuestions.add(QuestionModel("Which data type is used to create a variable that should store text in Java?", mutableListOf("String", "Text", "Char", "Data"), "String"))

// C++ Questions
        val cppQuestions = mutableListOf<QuestionModel>()
        cppQuestions.add(QuestionModel("Which of the following is a valid variable name in C++?", mutableListOf("int 1variable", "float variable1", "char @variable", "double variable"), "double variable"))
        cppQuestions.add(QuestionModel("What is the default access specifier for members of a class in C++?", mutableListOf("private", "protected", "public", "none"), "private"))
        cppQuestions.add(QuestionModel("What does the 'new' operator do in C++?", mutableListOf("Allocates memory", "Deletes memory", "Prints output", "None of the above"), "Allocates memory"))
        cppQuestions.add(QuestionModel("What is the correct syntax for including a header file in C++?", mutableListOf("#include <filename>", "#include 'filename'", "include <filename>", "import <filename>"), "#include <filename>"))
        cppQuestions.add(QuestionModel("Which of the following is used for comments in C++?", mutableListOf("// comment", "/* comment */", "# comment", "<!-- comment -->"), "// comment"))

// C Questions
        val cQuestions = mutableListOf<QuestionModel>()
        cQuestions.add(QuestionModel("Which function is used to print output to the console in C?", mutableListOf("print()", "write()", "printf()", "output()"), "printf()"))
        cQuestions.add(QuestionModel("What is the size of an integer in C?", mutableListOf("2 bytes", "4 bytes", "8 bytes", "16 bytes"), "4 bytes"))
        cQuestions.add(QuestionModel("Which header file is required for input/output functions in C?", mutableListOf("iostream", "stdio.h", "stdlib.h", "conio.h"), "stdio.h"))
        cQuestions.add(QuestionModel("What is the correct syntax to declare a pointer in C?", mutableListOf("type *ptr", "type ptr*", "ptr type", "*ptr type"), "type *ptr"))
        cQuestions.add(QuestionModel("Which of the following is not a valid C data type?", mutableListOf("int", "float", "char", "boolean"), "boolean"))

// Python Questions
        val pythonQuestions = mutableListOf<QuestionModel>()
        pythonQuestions.add(QuestionModel("Which of the following is a correct variable assignment in Python?", mutableListOf("x = 10", "10 = x", "x := 10", "x == 10"), "x = 10"))
        pythonQuestions.add(QuestionModel("What is the correct syntax for defining a function in Python?", mutableListOf("def function_name():", "function_name() def:", "function_name := def", "def function_name"), "def function_name():"))
        pythonQuestions.add(QuestionModel("Which data type is immutable in Python?", mutableListOf("List", "Set", "Tuple", "Dictionary"), "Tuple"))
        pythonQuestions.add(QuestionModel("How do you start a comment in Python?", mutableListOf("#", "//", "/*", "<!--"), "#"))
        pythonQuestions.add(QuestionModel("Which of the following is used to handle exceptions in Python?", mutableListOf("try-except", "catch", "handle", "try-finally"), "try-except"))

// Machine Learning Questions
        val mlQuestions = mutableListOf<QuestionModel>()
        mlQuestions.add(QuestionModel("What is the main goal of supervised learning?", mutableListOf("To make predictions", "To find patterns", "To perform clustering", "To optimize a function"), "To make predictions"))
        mlQuestions.add(QuestionModel("Which algorithm is commonly used for classification tasks?", mutableListOf("K-Means", "Linear Regression", "Decision Trees", "PCA"), "Decision Trees"))
        mlQuestions.add(QuestionModel("What is overfitting in machine learning?", mutableListOf("Model performs well on training data but poorly on test data", "Model performs well on test data but poorly on training data", "Model performs equally well on both training and test data", "Model is not trained enough"), "Model performs well on training data but poorly on test data"))
        mlQuestions.add(QuestionModel("Which metric is used to measure the performance of a classification model?", mutableListOf("Accuracy", "Mean Squared Error", "R-squared", "Precision-Recall"), "Accuracy"))
        mlQuestions.add(QuestionModel("What does 'bagging' stand for in machine learning?", mutableListOf("Bootstrap Aggregating", "Bag Aggregating", "Binary Aggregating", "Base Aggregating"), "Bootstrap Aggregating"))

// Adding all quiz models to the list
        quizModelList.add(QuizModel("1", "Android Development", "Fundamentals of Android Development", "5", AndroidQuestionModel))
        quizModelList.add(QuizModel("2", "Web Development", "Fundamentals of Web Development", "2", webDevelopmentQuestionModel))
        quizModelList.add(QuizModel("3", "Java", "Basics of Java Programming", "5", javaQuestions))
        quizModelList.add(QuizModel("4", "C++", "Basics of C++ Programming", "5", cppQuestions))
        quizModelList.add(QuizModel("5", "C", "Basics of C Programming", "5", cQuestions))
        quizModelList.add(QuizModel("6", "Python", "Basics of Python Programming", "2", pythonQuestions))
        quizModelList.add(QuizModel("7", "Machine Learning", "Introduction to Machine Learning", "5", mlQuestions))

        setupRecyclerView()

    }

}