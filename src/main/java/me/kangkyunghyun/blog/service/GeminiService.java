package me.kangkyunghyun.blog.service;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeminiService {

    @Value("abstract-plane-478703-b9")
    private String projectId;

    private final String location = "us-central1";
    private final String modelName = "gemini-1.5-flash";

    public String getSummary(String text) {
        try (VertexAI vertexAI = new VertexAI(projectId, location)) {
            GenerativeModel model = new GenerativeModel(modelName, vertexAI);
            String prompt = "다음 내용을 3줄로 요약해줘. 말투는 친절하게 해요체로 해줘:\n\n" + text;
            GenerateContentResponse response = model.generateContent(prompt);
            return ResponseHandler.getText(response);

        } catch (IOException e) {
            e.printStackTrace();
            return "AI 댓글 생성에 실패했습니다.";
        }
    }

    public String generateReply(String text) {
        try (VertexAI vertexAI = new VertexAI(projectId, location)) {
            GenerativeModel model = new GenerativeModel(modelName, vertexAI);
            String prompt = "다음 내용을 바탕으로 댓글을 작성해줘. 말투는 친절하게 해요체로 해줘:\n\n" + text;
            GenerateContentResponse response = model.generateContent(prompt);
            return ResponseHandler.getText(response);

        } catch (IOException e) {
            e.printStackTrace();
            return "AI 댓글 생성에 실패했습니다.";
        }
    }
}
