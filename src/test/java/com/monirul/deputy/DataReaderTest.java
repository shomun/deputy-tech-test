package com.monirul.deputy;

import com.monirul.deputy.model.Role;
import com.monirul.deputy.model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataReaderTest {
    private DataReader classUnderTest = new DataReader();

    @Test
    void readData_should_read_user_data_successfully() throws Exception {

       List<User> users = classUnderTest.readData("userdata.json", User[].class);

       assertNotNull(users);
       assertEquals(5, users.size());
    }

    @Test
    void readData_should_not_read_user_data_from_unknown_file() throws IOException, URISyntaxException {
        Exception exception = assertThrows(Exception.class, () -> {
            classUnderTest.readData("userdata_unknown.json", User[].class);
        });
    }

    @Test
    void readData_should_not_read_invalid_json_data() throws IOException, URISyntaxException {
        Exception exception = assertThrows(Exception.class, () -> {
            classUnderTest.readData("roledata_invalid.json", Role[].class);
        });
    }
}