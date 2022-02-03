package com.pahakaladze.quizmaker;

import android.content.Context;
import android.widget.Toast;

import java.io.*;

public class QuizLoader {
    private final static String FILE_NAME = "content.txt";

    private QuizLoader(){
    }

    public static void saveToFiles(Context context) {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(QuizList.getInstance());
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
            QuizList quizList = (QuizList) ois.readObject();
            QuizList.getInstance().setList(quizList.getList());
            quizList = QuizList.getInstance();
            loadedPage = quizList.getFirstPage();
            quizList.setCurrentPageIndex(0);
            quizList.setLastPageIndex(quizList.size() - 1);
            Toast.makeText(context, "Quiz loaded", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            loadedPage = QuestionPage.getEmptyPage();
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
