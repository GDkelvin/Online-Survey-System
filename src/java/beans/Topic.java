package beans;

import dao.TopicDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class Topic {
    private String title, id;
    private ArrayList<Question> questions = null;
    private int position = 0;

    public Topic() {
    }

    public int getPosition() {
        return position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Topic(String id, String title) {
        this.title = title;
        this.id = id;
    }

    public String process() {
        // Reset position to start of survey
        position = 0;

        // Fetch questions from database
        questions = TopicDAO.getQuestions(id);

        // Debug log
        System.out.println("Selected Topic ID: " + id);
        System.out.println("Questions Retrieved: " + (questions != null ? questions.size() : "null"));

        // Prevent NullPointerException
        if (questions == null || questions.isEmpty()) {
            System.out.println("Error: No questions found for topic ID " + id);
            return "index"; // Redirect back if no questions
        }

        return "survey";
    }

    public ArrayList<SelectItem> getTopics() {
        ArrayList<Topic> lst = TopicDAO.getTopics();
        
        // Debug log
        System.out.println("Topics Retrieved: " + (lst != null ? lst.size() : "null"));

        if (lst == null) {
            return new ArrayList<>(); // Return empty list to prevent null errors
        }

        ArrayList<SelectItem> items = new ArrayList<>();
        for (Topic t : lst) {
            items.add(new SelectItem(t.getId(), t.getTitle()));
        }

        return items;
    }

    public Question getQuestion() {
        if (questions == null || questions.isEmpty()) {
            System.out.println("Error: No questions available.");
            return null;
        }
        return questions.get(position);
    }

    public int getQuestionCount() {
        return (questions != null) ? questions.size() : 0;
    }

    public void next(ActionEvent evt) {
        if (position < getQuestionCount() - 1) {
            position++;
        }
    }

    public void previous(ActionEvent evt) {
        if (position > 0) {
            position--;
        }
    }

    public String cancel() {
        return "index";
    }

    public String finish() {
        if (questions == null) {
            System.out.println("Error: Cannot finish survey, questions list is null.");
            return "error";
        }

        boolean done = TopicDAO.storeSurveyResults(id, questions);
        return done ? "finish" : "error";
    }
}
