package com.example.imersao.dto;

import dev.langchain4j.model.input.structured.StructuredPrompt;

import java.util.List;

public class MyStructuredTemplate {

    @StructuredPrompt( {
            "Cria uma receita de {{prato}} que possa ser preparada usando somente {{ingredientes}}",
            "Estruture a sua resposta da seguinte forma",
            "Nome da receita: ...",
            "Descricao: ...",
            "Tempo de preparo: ...",
            "Ingredientes necessários:",
            "- ...",
            "- ...",
            "Modo de preparo:",
            "- ...",
            "- ...",
    }
    )
    public static class PromptDeReceita{
        public String prato;
        public List<String> ingredientes;
    }

}
