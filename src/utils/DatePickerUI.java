package utils;

import com.jfoenix.controls.JFXDatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DatePickerUI {
    private Date date = new Date();
    private JFXDatePicker datePicker = new JFXDatePicker();
    public DatePickerUI(){
        setPicker();
    }

    public JFXDatePicker getPicker(){
        return datePicker;
    }

    public Date getDate(){
        if (datePicker.getValue()!= null) {
            date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        else
            date = new Date();
        return date;
    }

    private void setPicker(){
        datePicker.setPrefWidth(110);
        datePicker.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
        datePicker.setValue(LocalDate.now());
    }
}
