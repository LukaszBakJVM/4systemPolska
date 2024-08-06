package org.freeddyns.systempolska.user;

import org.freeddyns.systempolska.Application;
import org.freeddyns.systempolska.exception.WrongFileFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;


@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class UploadFileControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void uploadUsersWrongFile() throws Exception {

        MockMultipartFile wrongFileType = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "plain text".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(wrongFileType))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> {

                    if (result.getResolvedException() instanceof WrongFileFormatException) {
                        String expectedMessage = "Wrong File Format";
                        String actualMessage = result.getResolvedException().getMessage();
                        Assertions.assertEquals(expectedMessage, actualMessage);
                    }
                });
    }
    @Test
    void uploadUsersFile() throws Exception {
        String xmlContent = """
                <users>
                    <user>
                        <name>Walton</name>
                        <surname>Alven</surname>
                        <login>walven0@rakuten.co.jp</login>
                    </user>
                </users>""";

        MockMultipartFile file = new MockMultipartFile("file", "test.xml", MediaType.TEXT_XML_VALUE, xmlContent.getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(file))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }


    }
