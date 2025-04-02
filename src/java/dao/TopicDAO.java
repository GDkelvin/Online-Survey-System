package dao;

import beans.Question;
import beans.Topic;
import java.util.ArrayList;
import java.util.Arrays;

public class TopicDAO {
    private static ArrayList<Topic> topics = new ArrayList<>();
    private static ArrayList<Question> questions = new ArrayList<>();
    //add new topic
    static {
        // Hardcoded topics
        topics.add(new Topic("1001", "Programming Languages"));
        topics.add(new Topic("1002", "Web Frameworks"));

        // Hardcoded questions
        questions.add(new Question("2000", "What is your fav. dynamic Language?", "Javascript", "Ruby", "Python"));
        questions.add(new Question("2001", "What is your primary tool for writing code?", "IDE", "Text Editor", "Both"));
        questions.add(new Question("2002", "Which MVC framework do you use?", "JSF", "Struts", "Spring"));
        questions.add(new Question("2003", "Which Javascript library do you use?", "JQuery", "GWT", "Others"));
    }

    public static ArrayList<Topic> getTopics() {
        return new ArrayList<>(topics);
    }

    public static ArrayList<Question> getQuestions(String topicid) {
        ArrayList<Question> filteredQuestions = new ArrayList<>();
        if (topicid.equals("1001")) {
            filteredQuestions.addAll(Arrays.asList(questions.get(0), questions.get(1)));
        } else if (topicid.equals("1002")) {
            filteredQuestions.addAll(Arrays.asList(questions.get(2), questions.get(3)));
        }
        return filteredQuestions;
    }

    public static boolean storeSurveyResults(String topicid, ArrayList<Question> questions) {
        System.out.println("Survey results stored for topic ID: " + topicid);
        for (Question q : questions) {
            System.out.println("Question: " + q.getText() + " | Answer: " + q.getAnswer());
        }
        return true;
    }
}
