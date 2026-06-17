package com.TuBes.HewanKu;

import java.util.ArrayList;
import java.util.List;

import com.TuBes.HewanKu.Hewan.Hewan;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Converter
public class JsonListHewanConverter implements AttributeConverter<List<Hewan>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Hewan> attribute) {
        try {
            // Mengubah List<Hewan> menjadi Teks JSON biasa saat disimpan ke Database
            return attribute != null ? objectMapper.writeValueAsString(attribute) : "[]";
        } catch (JacksonException e) {
            return "[]";
        }
    }

    @Override
    public List<Hewan> convertToEntityAttribute(String dbData) {
        try {
            // Mengubah kembali teks JSON dari database menjadi List<Hewan> di Java
            return dbData != null ? objectMapper.readValue(dbData, new TypeReference<List<Hewan>>() {
            }) : new ArrayList<>();
        } catch (JacksonException e) {
            return new ArrayList<>();
        }
    }
}