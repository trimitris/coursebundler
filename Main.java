package coursebundle;

import coursebundle.Data;

public class Main {
    public static void main(String[] args){
        Data database = new Data(3);
        for (int i=0; i<database.getLength(); i++){
            database.getCourse(i).printCourse();
        }
    }
}
