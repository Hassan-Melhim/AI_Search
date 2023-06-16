module edu.birzeit.ai_search {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.birzeit.ai_search to javafx.fxml;
    exports edu.birzeit.ai_search;
}