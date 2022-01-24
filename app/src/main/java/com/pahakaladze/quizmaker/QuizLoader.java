package com.pahakaladze.quizmaker;

import android.content.Context;
import android.widget.Toast;

import java.io.*;

public class QuizLoader {
    private final static String FILE_NAME = "content.txt";
    private static QuizList quizList = QuizList.getInstance();

    private QuizLoader(){
    }

    public static void saveToFiles(QuestionPage questionPage, Context context) {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
//            fos = new FileOutputStream(new File(context.getExternalFilesDir(null),FILE_NAME));
            fos = context.openFileOutput("content.txt", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            quizList.add(questionPage);
            oos.writeObject(quizList);
            oos.close();
            Toast.makeText(context, "Quiz saved", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static QuestionPage loadFromFiles(Context context){
        FileInputStream fin = null;
        QuestionPage loadedPage = null;

        try {
            fin = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fin);
            quizList = (QuizList) ois.readObject();
            QuizList.getInstance().setList(quizList.getList());
            loadedPage = QuizList.getInstance().getFirstPage();
            Toast.makeText(context, "Quiz loaded", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return loadedPage;
    }
}
