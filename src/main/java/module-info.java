module com.mycompany.hirayadeskbeta {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.hirayadeskbeta to javafx.fxml;
    exports com.mycompany.hirayadeskbeta;
}
