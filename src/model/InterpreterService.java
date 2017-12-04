package model;

import java.util.ArrayList;
import java.util.HashMap;

public class InterpreterService extends ServiceRequest {

    // Attributes
    private ArrayList<String> languages = new ArrayList<String>();
    private HashMap<String, long[]> reportInfo = new HashMap<String, long[]>();

    // ToDo: Possibly make each language for an interpreter its own class so that reports generate per language

    // Constructors
    public InterpreterService(){
        // TODO: get emails from database
        languages.add("French");
        languages.add("Dutch");
        languages.add("Icelandic");

        for (String s : getLanguages()){
            reportInfo.put(s, new long[2]);
        }
    }

    // Getters
    public ArrayList<String> getLanguages() {
        return this.languages;
    }

    // Setters
    public void setLanguages(ArrayList<String> languages){
        this.languages = languages;
    }

    // Methods
    public void updateEmails(){
        // TODO: get emails from database if edited in database
    }

    public void addLanguage(String add){
        this.languages.add(add);
    }

    public void removeLanguage(String remove){
        this.languages.remove(remove);
    }

    public String generateReport(){
        /*
            Information required:
            - How much time did each language take to interpret?
            - How many interpreters of each language have been requested?
         */
        if (!isActive()) {
            String lang = assigned.getLanguage();
            String report = "Language: " + lang;

            long diff = 0;

            long timeSent = sent.getTime();
            long timeReceived = received.getTime();

            long diffSeconds = (timeReceived - timeSent) / 1000;

            // This part increments the number of interpreters used for the language and time taken for this interpreter

            reportInfo.get(lang)[0] += 1;
            reportInfo.get(lang)[1] += diffSeconds;
            diff = reportInfo.get(lang)[1]/reportInfo.get(lang)[0];
            report.concat("Interpreters Used: " + reportInfo.get(lang)[0] + "\n" + "Average Time Taken: " + findTime(diff));

            return report;

        } else {
            return "This request has yet to be resolved";
        }
    }
}
