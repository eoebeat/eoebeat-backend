package com.eoemusic.eoebackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eoemusic.eoebackend.repository.MonthlySelectionRepository;
import com.eoemusic.eoebackend.repository.MusicRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 09/03/23 1:49 PM
 */
@SpringBootTest
@AutoConfigureMockMvc

public class OpsControllerTest {
  @Autowired
  private MusicRepository musicRepository;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private MonthlySelectionRepository monthlySelectionRepository;
  private static MockMultipartFile mockFile;

  @BeforeAll
  public static void setup() throws IOException {
    File file = new File("/Users/maxwellxiaoyuwu/Desktop/database.csv");
    String name = "file";
    String originalFileName = "database.csv";
    String contentType = "text/csv";
    byte[] content = Files.readAllBytes(file.toPath());
    mockFile = new MockMultipartFile(name, originalFileName, contentType, content);
  }

  @Test
  void testSyncMethodBasicCondition() throws Exception {
    musicRepository.deleteAll();
    monthlySelectionRepository.deleteAll();

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/eoebackend/syncDatabase")
        .file(mockFile)
        .param("alistAudioPath", "/audioPath")
        .param("alistCoverPath", "/coverPath"))
        .andExpect(status().isOk())
        .andReturn();
    String response = result.getResponse().getContentAsString();
    assertEquals("successfully sync", response);
  }

  @Test
  void testSyncWithAlreadySyncNoMonthlySelection() throws Exception {
    monthlySelectionRepository.deleteAll();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/eoebackend/syncDatabase")
        .file(mockFile)
        .param("alistAudioPath", "/audioPath")
        .param("alistCoverPath", "/coverPath"))
        .andExpect(status().isOk())
        .andReturn();
    String response = result.getResponse().getContentAsString();
    assertEquals("successfully sync", response);
  }

  @Test
  void testSyncWithAlreadySyncHasMonthlySelection() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/eoebackend/syncDatabase")
        .file(mockFile)
        .param("alistAudioPath", "/audioPath")
        .param("alistCoverPath", "/coverPath"))
        .andExpect(status().isOk())
        .andReturn();
    String response = result.getResponse().getContentAsString();
    assertEquals("successfully sync", response);
  }
  
}
