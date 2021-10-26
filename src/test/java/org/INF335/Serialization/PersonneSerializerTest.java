package org.INF335.Serialization;

import org.INF335.Personne;
import org.INF335.ReadWriteBuffer;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonneSerializerTest {

    @Test
    public void testSerialize(){
        LocalDate now = LocalDate.now();
        ReadWriteBuffer rw = new ReadWriteBuffer();

        Personne[] personnes = {
                new Personne("Alice", now),
                new Personne("Bernard", now, 123.34f, 0, 0),
                new Personne("Carl", now, 0, 12.33, 0),
                new Personne("Dorothee", now, 0, 0, 10),
                new Personne("Eleanor", now, 13.35, 24.4, 20),
                new Personne("Franz", now, -20.10, 0.165, 52)
        };

        for( Personne p : personnes) PersonneSerializer.serializePersonne(p, rw);

        for( Personne p : personnes){
            Personne r = PersonneSerializer.deserializePersonne(rw);
            assertEquals(p.getNom(), p, r);
        }
    }
}