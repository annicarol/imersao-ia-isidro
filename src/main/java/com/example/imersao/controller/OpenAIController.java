package com.example.imersao.controller;

import com.example.imersao.dto.MyQuestion;
import com.example.imersao.dto.MyStructuredTemplate;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.openai.OpenAiImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.Arrays;

@RestController
public class OpenAIController {


    @Value("{langchain4j.open-ai.chat-model.api-key}")
    private String apiKey;


    @Autowired
    private ChatLanguageModel chatLanguageModel;

    @PostMapping("/answer")
    public String chatWithOpenAI(@RequestBody MyQuestion question) {

//        ChatLanguageModel customModel = new OpenAiChatModel.OpenAiChatModelBuilder()
//                .apiKey(apiKey).modelName("gpt-4-turbo").temperature(0.7).build();

        return chatLanguageModel.generate(question.question());
    }

    @PostMapping("/receita")
    public String facaUmaReceita() {

        MyStructuredTemplate template = new MyStructuredTemplate();
        MyStructuredTemplate.PromptDeReceita rcPrompt = new MyStructuredTemplate.PromptDeReceita();
        rcPrompt.prato = "Pizza";
        rcPrompt.ingredientes = Arrays.asList("tomate","queijo");

        Prompt prompt = StructuredPromptProcessor.toPrompt(rcPrompt);
        return chatLanguageModel.generate(prompt.text());
    }

    @PostMapping("/imagem")
    public String generateImage(@RequestBody MyQuestion question) {

        ImageModel imageModel = new OpenAiImageModel.OpenAiImageModelBuilder().apiKey(apiKey).modelName("dall-e").build();

        try {
            return imageModel.generate(question.question()).content().url().toURL().toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


    }



}
