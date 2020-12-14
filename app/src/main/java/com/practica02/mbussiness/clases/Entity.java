package com.practica02.mbussiness.clases;

import com.google.firebase.firestore.Exclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Entity {
    private String documentId;

    @Exclude
    public String getDocumentId() {
        return this.documentId;
    }

}
